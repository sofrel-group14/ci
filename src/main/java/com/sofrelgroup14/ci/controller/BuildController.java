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
import java.time.Instant;

@RestController
@RequestMapping("/build")
public class BuildController {
    
    @Autowired
    private LogsRepository repository;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public Logs buildAndCreateLog() {
        Logs log = new Logs(ObjectId.get(), true, "great job", Instant.now());
        repository.save(log);
        return log;

    }

}