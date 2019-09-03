package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import io.netty.bootstrap.Bootstrap;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@EnableConfigurationProperties
@EnableDiscoveryClient
@SpringBootApplication
public class DemoApplication {

	@Autowired
	ConsulProperties consulProperties;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
	}
	
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("Consul props:" + consulProperties.getTest());
	}

	@Bean
	public RouteLocator helloRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
		.route("standard_server", r -> 
			r.path("/hello")
			.filters(f -> 
				f.rewritePath("/hello", "/api/hello"))
		.uri("http://localhost:9090")).build();
	}
	
	@Bean
	public RouteLocator httpBinRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("standard_server_http_bin", r -> r.path("/get").uri("http://httpbin.org:80")).build();
	}
	
	
	//@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		
		return builder.routes()
			//.route("path_route", r -> r.path("/get")
			//	.uri("http://google.ch"))
			.route("standard_server", r -> r.path("/hello").uri("http://localhost:9090"))
			.route("standard_server_http_bin", r -> r.path("/get").uri("http://httpbin.org:80"))
		/*	.route("host_route", r -> r.host("*.myhost.org")
				.uri("http://httpbin.org"))
			.route("rewrite_route", r -> r.host("*.rewrite.org")
				.filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
				.uri("http://httpbin.org"))
			.route("hystrix_route", r -> r.host("*.hystrix.org")
				.filters(f -> f.hystrix(c -> c.setName("slowcmd")))
				.uri("http://httpbin.org"))
			.route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
				.filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
				.uri("http://httpbin.org"))
			*/
			.build();
	}
}
