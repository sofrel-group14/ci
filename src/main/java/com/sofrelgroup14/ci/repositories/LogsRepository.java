package com.sofrelgroup14.ci.repositories;

import com.sofrelgroup14.ci.models.Logs;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogsRepository extends MongoRepository<Logs, String> {
  Logs findBy_id(ObjectId _id);
}