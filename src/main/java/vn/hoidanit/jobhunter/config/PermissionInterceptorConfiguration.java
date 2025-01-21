package vn.hoidanit.jobhunter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up the PermissionInterceptor.
 * Implements the WebMvcConfigurer interface to customize the Spring MVC
 * configuration.
 * 
 * <p>
 * This configuration class defines a bean for the PermissionInterceptor and
 * adds it to the
 * InterceptorRegistry with specific path patterns to exclude from interception.
 * </p>
 * 
 * <p>
 * The following paths are excluded from interception:
 * </p>
 * <ul>
 * <li>"/"</li>
 * <li>"/api/v1/auth/**"</li>
 * <li>"/storage/**"</li>
 * <li>"/api/v1/companies"</li>
 * <li>"/api/v1/jobs"</li>
 * <li>"/api/v1/skills"</li>
 * <li>"/api/v1/files"</li>
 * </ul>
 * 
 * <p>
 * Usage:
 * </p>
 * 
 * <pre>
 * &#64;Configuration
 * public class PermissionInterceptorConfiguration implements WebMvcConfigurer {
 *     // Bean and method definitions
 * }
 * </pre>
 * 
 * @see PermissionInterceptor
 * @see WebMvcConfigurer
 */
@Configuration
public class PermissionInterceptorConfiguration implements WebMvcConfigurer {

    @Bean
    PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] whiteList = {
                "/", "/api/v1/auth/**", "/storage/**",
                "/api/v1/companies/**", "/api/v1/jobs/**", "/api/v1/skills/**", "/api/v1/files"
        };
        registry.addInterceptor(getPermissionInterceptor())
                .excludePathPatterns(whiteList);
    }
}
