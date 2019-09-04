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
		
		log.info("*************** Consull properties ***************");
		log.info("config/api-gateway/test : " + consulProperties.getTest());
		
		log.info("*************** Discovery clients services ***************");
		discoveryClient.getServices().forEach(service -> {
			log.info("Service: " + service);
		});
		
		log.info("*************** Discovery clients instances ***************");
		discoveryClient.getInstances("client-gateway-1").forEach(instance -> {
			log.info("Uri: {}, ServiceId:{}",instance.getUri(),instance.getServiceId());
			
		});
	}
	
	@Bean 
	  public DiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient,DiscoveryLocatorProperties properties) { 
		  return new DiscoveryClientRouteDefinitionLocator(discoveryClient, properties); }
	 
	  
	
	
	
}
