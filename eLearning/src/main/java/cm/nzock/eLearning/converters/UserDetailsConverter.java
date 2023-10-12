package cm.nzock.eLearning.converters;

import cm.nzock.eLearning.security.JwtTokenUtil;
import cm.platform.basecommerce.populator.Converter;
import cm.platform.myleaninig.core.ParticipantModel;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component
public class UserDetailsConverter implements Converter<ParticipantModel, UserDetails> {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsConverter.class);

    private JwtTokenUtil jwtTokenUtil ;

    @Autowired
    public UserDetailsConverter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public UserDetails convert(ParticipantModel source) {
        if(Objects.isNull(source)){
            return null;
        }
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return CollectionUtils.emptyCollection();
            }

            @Override
            public String getPassword() {
                return source.getPassword();
            }

            @Override
            public String getUsername() {
                return source.getCode();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true ;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;//!source.getLocked();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;//!source.getExpired() ;
                /**StringUtils.isBlank(source.getToken()) ? true
                        : jwtTokenUtil.getExpirationDate(source.getToken()).before(new Date())**/
            }

            @Override
            public boolean isEnabled() {
                return true;//source.getActive();
            }
        };
    }
}
