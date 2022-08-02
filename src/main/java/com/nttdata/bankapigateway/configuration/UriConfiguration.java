package com.nttdata.bankapigateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * UriConfiguration Class.
 *
 * @author jmacoele
 *
 */

//tag::uri-configuration[]
@ConfigurationProperties
public class UriConfiguration {

  private String httpbin = "http://banking.com:80";

  public String getHttpbin() {
    return httpbin;
  }

  public void setHttpbin(String httpbin) {
    this.httpbin = httpbin;
  }
}
