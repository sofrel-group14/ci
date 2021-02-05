package com.sofrelgroup14.ci.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Logs {
  @Id
  private ObjectId _id;
  private boolean buildSuccess;
  private String buildResult;
  
  // Constructors
  public Logs() {}
  
  public Logs(ObjectId _id, boolean buildSucess, String buildResult) {
    this._id = _id;
    this.buildSuccess = buildSucess;
    this.buildResult = buildResult;
  }
  
  // ObjectId needs to be converted to string
  public String get_id() { return _id.toHexString(); }
  public void set_id(ObjectId _id) { this._id = _id; }
  
  public boolean getBuildSucess() { return buildSuccess; }
  public void setBuildSucess(boolean buildSuccess) { this.buildSuccess = buildSuccess; }

  public String getBuildResults() { return buildResult; }
  public void setBuildResults(String buildResult) { this.buildResult = buildResult; }

}