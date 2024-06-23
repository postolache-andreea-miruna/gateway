package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@SpringBootApplication
public class GatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}
@Configuration
class RouteConfiguration {
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/bookshop/salesOff/**")
						.filters(f -> f.rewritePath("/bookshop/salesOff/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://SALESOFF")) //ln load balancer + application_name
				.route(p -> p
						.path("/bookshop/BookShop-Spring-Cloud/**")
						.filters(f -> f.rewritePath("/bookshop/BookShop-Spring-Cloud/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time",new Date().toString()))
						.uri("lb://BOOKSHOP-SPRING-CLOUD")).build();
	}
}