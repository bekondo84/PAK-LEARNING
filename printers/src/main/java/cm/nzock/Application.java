package cm.nzock;

import cm.platform.BaseCommerceServiceConfig;
import cm.platform.backoffice.AbstractIsisApplication;
import cm.platform.basecommerce.BaseCommerceFacadesConfig;
import cm.platform.basecommerce.core.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.boot.SpringApplication;

//@Import(WebSiteConfig.class)
public class Application extends AbstractIsisApplication
{
    public static void main(String[] args){
        SpringApplication.run(Application.class ,args);
    }
}
