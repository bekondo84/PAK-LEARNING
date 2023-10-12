package cm.nzock.eLearning;

import cm.platform.backoffice.AbstractIsisApplication;
import org.springframework.boot.SpringApplication;

//@Import(WebSiteConfig.class)
public class WebSiteApplication extends AbstractIsisApplication
{
    public static void main(String[] args){
        SpringApplication.run(WebSiteApplication.class ,args);
    }
}
