package cm.nzock.eLearning.security;

import cm.nzock.eLearning.converters.UserDetailsConverter;
import cm.nzock.eLearning.dao.ParticipantDao;
import cm.platform.basecommerce.core.enums.ParticipantCategory;
import cm.platform.basecommerce.core.settings.SettingModel;
import cm.platform.basecommerce.services.SettingService;
import cm.platform.myleaninig.core.ParticipantModel;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.lang.String.format;

@Component
public class LocalAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ParticipantDao userDao ;
    @Autowired
    private UserDetailsConverter userDetailsConverter;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SettingService settingService;


    public LocalAuthenticationProvider() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       if (activate(authentication)) {
           DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
           authProvider.setUserDetailsService(username -> userDao
                   .findByUsername(username).map(u -> userDetailsConverter.convert((ParticipantModel) u))
                   .orElseThrow(() -> new UsernameNotFoundException(format("User %s, not found", username))
                   ));
           authProvider.setPasswordEncoder(passwordEncoder);

           return authProvider.authenticate(authentication);
       } else  {
           return null;
       }
    }

    private boolean  activate(final Authentication authentication) {
        final SettingModel setting =  settingService.getSettings();
        if (Objects.isNull(setting) || BooleanUtils.isFalse(setting.getLdapAuthentication())) {
            return true;
        }
        final String username = authentication.getName();
        assert StringUtils.isNotBlank(username): "No username provided ....";
        ParticipantModel user = userDao.findByUsername(username).orElse(null);
        return Objects.nonNull(user) && Objects.nonNull(user.getCategory()) && user.getCategory().equals(ParticipantCategory.PARTENER);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
