package cm.nzock.mylearning;

import cm.platform.BaseCommerceServiceConfig;
import cm.platform.basecommerce.BaseCommerceFacadesConfig;
import cm.platform.basecommerce.core.BaseCommerceProcessConfig;
import cm.platform.basecommerce.core.security.SecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"cm.nzock"})
@Import({BaseCommerceServiceConfig.class, SecurityConfig.class, BaseCommerceFacadesConfig.class,
        BaseCommerceProcessConfig.class})
public class ApplicationConfig implements WebMvcConfigurer
{

}