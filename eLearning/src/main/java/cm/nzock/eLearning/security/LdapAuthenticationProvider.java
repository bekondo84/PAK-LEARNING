package cm.nzock.eLearning.security;

import cm.nzock.eLearning.converters.UserDetailsConverter;
import cm.nzock.eLearning.dao.ParticipantDao;
import cm.platform.basecommerce.core.enums.ParticipantCategory;
import cm.platform.basecommerce.core.settings.SettingModel;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.SettingService;
import cm.platform.myleaninig.core.ParticipantModel;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class LdapAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(LdapAuthenticationProvider.class);

    @Autowired
    private LdapContextSource contextSource;

   @Autowired
    private ParticipantDao userDao ;
    @Autowired
    private UserDetailsConverter converter;

    @Autowired
    private LdapProperties ldapProperties;

    @Autowired
    private SettingService settingService;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostConstruct
   private void initContext() {
        contextSource.setUrl("ldap://172.16.0.2:389");
        contextSource.setBase("DC=pak,DC=local");
        contextSource.setAnonymousReadOnly(true);
        contextSource.setUserDn("CN={0},OU=Utilisateurs,DC=pak,DC=local");
        contextSource.afterPropertiesSet();

   }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SettingModel setting = settingService.getSettings();

        if (Objects.nonNull(setting) && BooleanUtils.isTrue(setting.getLdapAuthentication()) && activate(authentication)) {
            ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(null, setting.getLdapUrl());
            Authentication auth = provider.authenticate(authentication);
            return auth;
        } else {
            return null;
        }
    }

    private boolean  activate(final Authentication authentication) {
        final String username = authentication.getName();
        assert StringUtils.isNotBlank(username): "No username provided ....";
        ParticipantModel user = userDao.findByUsername(username).orElse(null);
        return Objects.nonNull(user) && Objects.nonNull(user.getCategory()) && user.getCategory().equals(ParticipantCategory.EMPLOYE);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
