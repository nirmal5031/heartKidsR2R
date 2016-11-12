package com.tcs.survey.platform.util;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExternalConfigComponent {
  private static Logger logger = LoggerFactory.getLogger(ExternalConfigComponent.class);

  //No properties configured yet.

  @PostConstruct
  public void postConstruct(){
    //This is where we are going to print the values of the properties
  }
}