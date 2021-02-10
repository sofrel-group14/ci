package com.sofrelgroup14.ci.models;

import java.sql.Timestamp;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.schema.IdentifiableJsonSchemaProperty.TimestampJsonSchemaProperty;
import java.time.Instant;

public class Logs {
  @Id
  private ObjectId _id;
  private boolean buildSuccess;
  private String buildResult;
  private Instant timestamp;
  
  // Constructors
  public Logs() {}
  
  public Logs(ObjectId _id, boolean buildSuccess, String buildResult, Instant timestamp) {
    this._id = _id;
    this.buildSuccess = buildSuccess;
    this.buildResult = buildResult;
    this.timestamp = timestamp;
  }
  
  // ObjectId needs to be converted to string
  public String get_id() { return _id.toHexString(); }
  public void set_id(ObjectId _id) { this._id = _id; }
  
  public boolean getBuildSuccess() { return buildSuccess; }
  public void setBuildSuccess(boolean buildSuccess) { this.buildSuccess = buildSuccess; }

  public String getBuildResults() { return buildResult; }
  public void setBuildResults(String buildResult) { this.buildResult = buildResult; }

  public Instant getTimestamp() { return timestamp; }
  public void setTimestamp(Instant timestamp){ this.timestamp = timestamp; }

}