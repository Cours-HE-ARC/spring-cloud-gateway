package ch.hearc.jee.apigateway;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!ymlroutes")
@Component
public class GatewayRoutesBuilder {

	
	@Bean
	public RouteLocator helloRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
			.route("hello_api_route", r -> 
				r.path("/hello")
				.filters(f -> 
					f.rewritePath("/hello", "/api/hello"))
			.uri("lb://client-gateway-1")).build();
	}

 	@Bean
	public RouteLocator httpBinRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("http_bin_route", r -> 
					r.path("/get").uri("http://httpbin.org:80")).build();
	}


}
