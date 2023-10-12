package cm.nzock.eLearning.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="isis.ldap")
public class LdapProperties {
    private String url;
    private String baseDn;
    private String password;
    private String dnPattern;
    private String domain;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBaseDn() {
        return baseDn;
    }

    public void setBaseDn(String baseDn) {
        this.baseDn = baseDn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDnPattern() {
        return dnPattern;
    }

    public void setDnPattern(String dnPattern) {
        this.dnPattern = dnPattern;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
