package ch.hearc.jee.apigateway;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class ApiGatewayApplication {

	@Autowired
	ConsulProperties consulProperties;
	
	@Autowired
	DiscoveryClient discoveryClient;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@PostConstruct
	public void postContextConstruct() {
		log.info("application context started");
		log.info("Consul properties -test- : " + consulProperties.getTest());
		log.info("Discovery clients:");
		
		discoveryClient.getInstances("client.gateway-1").forEach(instance -> {
			log.info("Uri: " + instance.getUri());
		});
		
	
	}
	
	@Bean 
	  public DiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient,DiscoveryLocatorProperties properties) { 
		  return new DiscoveryClientRouteDefinitionLocator(discoveryClient, properties); }
	 
	  
	
	
	
}
