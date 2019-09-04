package ch.hearc.jee.apigateway;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GatewayRoutesBuilder {

	
	@Bean
	public RouteLocator helloRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
		.route("standard_server", r -> 
			r.path("/hello")
			.filters(f -> 
				f.rewritePath("/hello", "/api/hello"))
		.uri("http://client-gateway-1")).build();
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
