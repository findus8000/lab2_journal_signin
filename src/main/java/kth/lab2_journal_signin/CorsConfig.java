package kth.lab2_journal_signin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsConfig implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://react-frontend:3000",  // Frontend service in Kubernetes
                        "http://localhost:3000"         // For local testing
                )
                .allowedMethods("GET", "POST")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}

