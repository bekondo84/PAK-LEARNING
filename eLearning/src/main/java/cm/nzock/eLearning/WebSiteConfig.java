package cm.nzock.eLearning;

import cm.platform.BaseCommerceServiceConfig;
import cm.platform.basecommerce.BaseCommerceFacadesConfig;
import cm.platform.basecommerce.core.BaseCommerceProcessConfig;
import cm.platform.cms.CMSConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"cm.nzock.eLearning"})
@Import({BaseCommerceServiceConfig.class, BaseCommerceFacadesConfig.class, CMSConfig.class, BaseCommerceProcessConfig.class})
public class WebSiteConfig implements WebMvcConfigurer
{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        //registry.addViewController("/login").setViewName("login");
    }
}