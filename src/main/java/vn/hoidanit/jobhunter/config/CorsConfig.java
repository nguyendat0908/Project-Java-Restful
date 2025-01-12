package vn.hoidanit.jobhunter.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Cho phép các URL nào có thể truy cập tới Backend
        configuration.setAllowedOrigins(
                Arrays.asList("http://localhost:3000", "http://localhost:4173", "http://localhost:5173"));

        // Các method nào được kết nối
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Các phần Header được phép gửi lên
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "x-no-retry"));

        // Gửi kèm cookie hay không?
        configuration.setAllowCredentials(true);

        // Thời gian pre-flight request có thể cache (tính theo seconds)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Cấu hình cors cho tất cả api
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
