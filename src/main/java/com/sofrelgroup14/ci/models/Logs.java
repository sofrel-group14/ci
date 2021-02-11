package com.sofrelgroup14.ci.models;

import java.sql.Timestamp;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.schema.IdentifiableJsonSchemaProperty.TimestampJsonSchemaProperty;
import java.time.Instant;

public class Logs {
  @Id
  private ObjectId _id;
  private String commitHash;
  private boolean buildSuccess;
  private String buildResult;
  private Instant timestamp;
  private String branchName;

  // Constructors
  public Logs() {}
  
  public Logs(ObjectId _id, String commitHash, boolean buildSuccess, String buildResult, Instant timestamp, String branchName) {
    this._id = _id;
    this.commitHash = commitHash;
    this.buildSuccess = buildSuccess;
    this.buildResult = buildResult;
    this.timestamp = timestamp;
    this.branchName = branchName;
  }
  
  // ObjectId needs to be converted to string
  public String get_id() { return _id.toHexString(); }
  public void set_id(ObjectId _id) { this._id = _id; }

  public String getCommitHash() { return commitHash; }
  public void setCommitHash(String commitHash) { this.commitHash = commitHash; }
  
  public boolean getBuildSuccess() { return buildSuccess; }
  public void setBuildSuccess(boolean buildSuccess) { this.buildSuccess = buildSuccess; }

  public String getBuildResults() { return buildResult; }
  public void setBuildResults(String buildResult) { this.buildResult = buildResult; }

  public Instant getTimestamp() { return timestamp; }
  public void setTimestamp(Instant timestamp){ this.timestamp = timestamp; }

  public String getBranchName() { return branchName; }
  public void setBranchName(String branchName) { this.branchName = branchName; }
}