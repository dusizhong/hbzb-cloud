package com.hbzb.gw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@SpringBootApplication
@RestController
public class GwApplication {

	public static void main(String[] args) {
		SpringApplication.run(GwApplication.class, args);
	}

	@GetMapping("/")
	public String greetings() {
		return "Greetings from hbzb gw server!";
	}

//	@Bean
//	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(p -> p
//						.path("/uaa")
//						.filters(f -> f.addRequestHeader("Hello", "World"))
//						.uri("http://127.0.0.1:9999"))
////				.route(p -> p
////						.host("*.hystrix.com")
////						.filters(f -> f.hystrix(config -> config
////								.setName("myHystrix")
////								.setFallbackUri("forward:/fallback")))
////						.uri("http://httpbin.org"))
//				.build();
//	}

	@Configuration
	public class CorsConfig {
		@Bean
		public CorsWebFilter corsFilter() {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowCredentials(true);
			config.addAllowedOrigin("*");
			config.addAllowedHeader("*");
			config.addAllowedMethod("*");
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
			source.registerCorsConfiguration("/**", config);
			return new CorsWebFilter(source);
		}
	}
}
