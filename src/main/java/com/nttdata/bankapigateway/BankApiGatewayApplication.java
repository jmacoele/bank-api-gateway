package com.nttdata.bankapigateway;

import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bankapigateway.configuration.UriConfiguration;

// tag::code[]
@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class BankApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankApiGatewayApplication.class, args);
  }

  // tag::route-locator[]
  @Bean
  RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
    String httpUri = uriConfiguration.getHttpbin();
    return builder.routes()
        .route(p -> p
            .path("/get")
            .filters(f -> f.addRequestHeader("Hello", "World"))
            .uri(httpUri))
        .route(p -> p
            .host("*.banking.com")
            .filters(f -> f
                .circuitBreaker(config -> config
                    .setName("mycmd")
                    .setFallbackUri("forward:/fallback")))
            .uri(httpUri))
        .build();
  }
  // end::route-locator[]

  // tag::fallback[]
  @RequestMapping("/fallback")
  public Mono<String> fallback() {
    return Mono.just("fallback");
  }
  // end::fallback[]
}