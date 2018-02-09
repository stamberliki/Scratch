package com.mygdx.game.entity;

/**
 * Created by Anony on 2/5/2018.
 */

public class gameException extends Exception{
  private String msg;
  
  public gameException(String ex){
    msg = ex;
  }
  
  public gameException(Exception e) {
    super(e);
  }
  
  @Override
  public String getMessage() {
    if (msg != null)
      return msg;
    else
      return super.getMessage();
  }
  
}
