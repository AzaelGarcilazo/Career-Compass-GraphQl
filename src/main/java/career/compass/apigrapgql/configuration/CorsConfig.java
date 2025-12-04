package career.compass.apigrapgql.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permitir todos los orígenes
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // Permitir credenciales
        config.setAllowCredentials(true);
        
        // Permitir todos los headers
        config.addAllowedHeader("*");
        
        // Permitir todos los métodos
        config.addAllowedMethod("*");
        
        // Exponer headers
        config.setExposedHeaders(Arrays.asList(
            "Content-Type",
            "X-Total-Count"
        ));
        
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}