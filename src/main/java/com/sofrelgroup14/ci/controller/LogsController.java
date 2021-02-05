package com.sofrelgroup14.ci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAllLogs() {
        return "bruh1";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getLogById(@PathVariable("id") String id) {
        return id;
    }

    @RequestMapping(value = "/build", method = RequestMethod.POST)
    public String buildAndCreateLog() {
        return "omegaLUL";
    }

}