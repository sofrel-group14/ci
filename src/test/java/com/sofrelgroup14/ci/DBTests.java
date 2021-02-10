package com.sofrelgroup14.ci;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sofrelgroup14.ci.controller.BuildController;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.sofrelgroup14.ci.models.Logs;
import com.sofrelgroup14.ci.repositories.LogsRepository;
import org.bson.types.ObjectId;
import java.util.List;
import java.time.Instant;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.AfterEach;
@SpringBootTest(properties = {
    "spring.data.mongodb.database=test_logs"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DBTest {
    
    @Autowired
    private LogsRepository repository;

    /*
    This test if we are able to create a log and get that log from the database
    
    */

    @Test
    @Order(1)
    public void createLog(){

        List<Logs> logs = repository.findAll();
        assertEquals(0, logs.size());
        ObjectId id = ObjectId.get();
        Logs log = new Logs(id, "423u4h23u423y32uih234u32", false, "Nope", Instant.now());
        repository.save(log);
        logs = repository.findAll();

        assertEquals(1, logs.size());

        log = repository.findBy_id(id);
        
        assertEquals(id.toHexString(), log.get_id());
        assertEquals(false, log.getBuildSuccess());

    }

    /*
    This test if we are able to create multiple logs and get them from the database
    
    */

    @Test
    @Order(2)
    public void checkAllLogs(){
        List<Logs> logs = repository.findAll();
        assertEquals(0, logs.size());

        ObjectId id1 = ObjectId.get();
        ObjectId id2 = ObjectId.get();
        ObjectId id3 = ObjectId.get();
        Logs log1 = new Logs(id1, "423u4h23u423y32uih234u32", false, "Nope", Instant.now());
        Logs log2 = new Logs(id2, "423u4h23u423y32uih234u32", false, "Nope", Instant.now());
        Logs log3 = new Logs(id3, "423u4h23u423y32uih234u32", false, "Nope", Instant.now());

        repository.save(log1);
        repository.save(log2);
        repository.save(log3);
        log1 = repository.findBy_id(id1);
        log2 = repository.findBy_id(id2);
        log3 = repository.findBy_id(id3);


        logs = repository.findAll();

        
        assertEquals(3, logs.size());

        assertEquals(id1.toHexString(), log1.get_id());
        assertEquals(id2.toHexString(), log2.get_id());
        assertEquals(id3.toHexString(), log3.get_id());

    }



    @AfterEach
    public void CleanUpDB() {
        repository.deleteAll();
    } 
}
