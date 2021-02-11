package com.sofrelgroup14.ci.controller;
import java.io.BufferedReader;
import java.io.File;

// For parsing JSON-strings (source: https://stackoverflow.com/a/5245881)
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.tomcat.jni.Proc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.sofrelgroup14.ci.models.Logs;
import com.sofrelgroup14.ci.repositories.LogsRepository;
import org.bson.types.ObjectId;

import java.io.InputStreamReader;
import java.util.List;
import java.time.Instant;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;

/**
 * Rest controller for the /build-endpoint, invoked by the
 * GitHub webhook when a push is made to a branch.
 */
@RestController
@RequestMapping("/build")
public class BuildController {
    
    @Autowired
    private LogsRepository repository;

    /*
    Sources used to learn how to extract the HTTP-body from the POST-request
    made by the GitHub webhook. (I.e. how to use @RequestBody)
    1) https://stackabuse.com/get-http-post-body-in-spring/
    2) https://www.baeldung.com/spring-request-response-body
    3) https://stackoverflow.com/a/6019761
    */
    /**
     * Builds the branch specified in "ref" attribute of the webhook payload
     * (this is the branch that was pushed to), runs tests on code in this branch,
     * and finally logs the output to the database.
     *
     * Does this by cloning the repo, checking out the branch, building
     * and running tests using 'mvn' command, and then deletes the repo.
     *
     * Example payload (irrelevant parts omitted):
     * {
     *  ...
     *  "ref": "refs/heads/webhooks-playground",
     *  "before": "c130af7052edd8cc063dd28df6441262cb782822",
     *  "after": "bee5e5bd2e8da4bcde79f8a0483bda650bdb1b95",
     *  ...
     * }
     * would run:
     * 'git clone -b webhooks-playground <repo-url> repo'
     * 'mvn verify --file repo/pom.xml'
     * ...save the output to the database...
     * 'rm -Rf repo'
     *
     * What HTTP-payload looks like can be seen in Settings -> Webhooks
     * on GitHub (click an active webhook and scroll down).
     *
     * @param body The HTTP POST request body.
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void buildAndCreateLog(@RequestBody String body) {

        String ref;
        String commitHash = ""; // Remove error: "commitHash may not have been initialized"
        String branchName = ""; // -||-

        // Parse the JSON in the POST body to extract commit-hash and branch name.
        // (Source for JSON parsing strings automatically: https://stackoverflow.com/a/5245881)
        try {
            JSONObject jsonObject = new JSONObject(body);

            ref = jsonObject.getString("ref"); // Will be "refs/heads/branch-name"
            String[] tmp = ref.split("/");
            branchName = tmp[tmp.length-1];

            commitHash = jsonObject.getString("after");

        } catch (JSONException e){
            e.printStackTrace();
        }

        sendStatus("pending", commitHash, "");  

        // Clone repo, run mvn verify, post output to db, remove repo
        // ProcessBuilder:s have to be wrapped in try-catch block.
        try {
            ProcessBuilder pbClone = new ProcessBuilder(
              "git", "clone", "-b", branchName, "https://github.com/sofrel-group14/ci.git", "repo"); // Could be useful to save time and space: '--single-branch' ("Clone only the history leading to the tip of a single branch, either specified by the --branch option or the primary branch remote's HEAD points at.")
            pbClone.start().waitFor(); // Start process and block this program thread until process has finished.

            ProcessBuilder pbMvnVerify = new ProcessBuilder(
              "mvn", "verify", "-B", "--file", "repo/pom.xml");
            Process p = pbMvnVerify.start();

            // Gather Maven-output (i.e. build output) to string (source: https://stackoverflow.com/a/16714180)
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String buildOutput = sb.toString();

            // See if build was successful
            p.waitFor(); // Wait until process is finished
            int val = p.exitValue(); // 0 if success, error code otherwise
            boolean buildSuccess = (val == 0) ? true : false;

            // Save output to database
            ObjectId _id = ObjectId.get();
            System.out.println(_id.toString());
            Logs log = new Logs(_id, commitHash, buildSuccess, buildOutput, Instant.now());
            repository.save(log);

            // Remove repo (-R for directory, -f to skip prompt "are you sure?")
            ProcessBuilder pbRmRepo = new ProcessBuilder("rm", "-R", "-f", "repo");
            pbRmRepo.start().waitFor(); // Start process and block this program thread until process has finished.
            
            sendStatus(buildSuccess == true ? "success" : "failure", commitHash, _id.toString());

        } catch (Exception e) {
            e.printStackTrace();
            // Server crashed or something :(
            sendStatus("failure", commitHash, ""); 
        }
    }


    /**
     * This function sends a notification to Github with information about the build progress and status.
     * @param status the build status. "pending", "success" or "failure".
     * @param commitHash the commit the build job belongs to
     * @param jobID The Mongo id of the log object.
     */
    private void sendStatus(String status, String commitHash, String jobID) {
        try {
            // Abort if no access token.
            if (System.getenv("GH_ACCESS_TOKEN") == null) {
                System.out.println("NO GH_ACCESS_TOKEN DEFINED. WON'T NOTIFY GITHUB ABOUT BUILD STATUS.");
                return;
            };

            // https://www.baeldung.com/java-http-request
            // https://www.baeldung.com/httpurlconnection-post
            URL url = new URL("https://api.github.com/repos/sofrel-group14/ci/statuses/" + commitHash);
    
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/vnd.github.v3+json");
            // Generate a token at https://github.com/settings/tokens/new and give it the correct access rights
            // I think it is workflow/repo
            con.setRequestProperty("Authorization", "token " + System.getenv("GH_ACCESS_TOKEN"));
    
            // Put arguments to JSON body
            JSONObject body = new JSONObject();
            // Link to resulting job. Only append if status is not pending or nonzero id.
            // TODO: Make linking to pretty frontend possible
            if (!jobID.equals("pending") && jobID.length() != 0) {
                body.put("target_url", "http://axelelmarsson.se#" + commitHash);
            }
            body.put("description", "Our CI Server");
            // Job status
            body.put("state", status);
    
            // Send body
            DataOutputStream os = new DataOutputStream(con.getOutputStream());
            byte[] input = body.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.close();
            
            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                        con.getInputStream()));
            String decodedString;
            System.out.println("Response from Github:");
            while ((decodedString = in.readLine()) != null) {
                System.out.println(decodedString);
            }
            in.close();
            con.disconnect();
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
