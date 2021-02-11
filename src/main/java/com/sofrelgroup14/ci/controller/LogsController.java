package com.sofrelgroup14.ci.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.sofrelgroup14.ci.models.Logs;
import com.sofrelgroup14.ci.repositories.LogsRepository;
import org.bson.types.ObjectId;
import java.util.List;



/**
 * Rest controller for the /logs-endpoint, invoked by 
 * the frontend to check the build logs
 * The endpoints either return all logs or a log with a specific ID.
 */

@RestController
@RequestMapping("/logs")
public class LogsController {
    @Autowired
    private LogsRepository repository;

    /**
     * The logs-endpoints return a all build logs with in the database
     * @return List<Logs>
     */

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Logs> getAllLogs() {
        return repository.findAll();
    }
    /**
     * The logs/{id}-endpoint return a specific log with the id which is sent as a URL paramenter
     * @param id unique id for identifying each log 
     * @return Logs
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Logs getLogById(@PathVariable("id") ObjectId id) {
        return repository.findBy_id(id);
    }

}