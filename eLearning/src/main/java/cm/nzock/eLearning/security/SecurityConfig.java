package cm.nzock.eLearning.security;

import cm.nzock.eLearning.converters.UserDetailsConverter;
import cm.nzock.eLearning.dao.ParticipantDao;
import cm.platform.BaseCommerceConfig;
import cm.platform.basecommerce.core.enums.ParticipantCategory;
import cm.platform.basecommerce.core.settings.SettingModel;
import cm.platform.basecommerce.services.SettingService;
import cm.platform.myleaninig.core.ParticipantModel;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static java.lang.String.format;

@EnableWebSecurity
@Configuration
@Import(BaseCommerceConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

    private ParticipantDao userDao ;
    private JwtTokenFilter jwtTokenFilter;
    private UserDetailsConverter userDetailsConverter;
    private LdapAuthenticationProvider ldapAuthenticationProvider;
    private LocalAuthenticationProvider localAuthenticationProvider;
    //private SettingService settingService;

    @Autowired
    public SecurityConfig(ParticipantDao userDao, JwtTokenFilter jwtTokenFilter,
                          UserDetailsConverter userDetailsConverter, LdapAuthenticationProvider ldapAuthenticationProvider,
                          LocalAuthenticationProvider localAuthenticationProvider/**, SettingService settingService*/) {
        this.userDao = userDao;
        this.jwtTokenFilter = jwtTokenFilter;
        this.userDetailsConverter = userDetailsConverter;
        this.ldapAuthenticationProvider = ldapAuthenticationProvider;
        this.localAuthenticationProvider = localAuthenticationProvider;
        //this.settingService = settingService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();
        //Set session management to stateless
        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        //Set unauthorized requests exception handler
        http = http.exceptionHandling()
                .authenticationEntryPoint(
                        ((request, response, authException) -> {
                            LOG.error("Unauthorized request - {}", authException.getMessage());
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    authException.getMessage()
                            );
                        })
                ).and();

        //Set permission endpoints
        http.authorizeRequests()
                //Swagger endpoint must be public accible
                //.antMatchers("/").permitAll()
                //.antMatchers(format("%s/**",re))
                //Our public endpoint
                .antMatchers("/", "/favicon.ico","/api/v1/public/**","/swagger-ui/**","/assets/**","/images/**", "/img/**", "/js/**", "/fonts/**", "/css/**", "/login").permitAll()
                //.antMatchers(null)
                //Our private endpoints
                .anyRequest().authenticated();

        //Add JWT token filter
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //http.authorizeRequests().antMatchers("/","/assets/**","/images/**", "/img/**", "/js/**", "/css/**").permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ldapAuthenticationProvider);
        //auth.authenticationProvider(authenticationProvider());
        auth.authenticationProvider(localAuthenticationProvider);
    }

   /** @Bean
    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        final SettingModel setting = settingService.getSettings();
        ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider =
                new ActiveDirectoryLdapAuthenticationProvider( setting.getLdapDomain(), setting.getLdapUrl());
        // to parse AD failed credentails error message due to account - expiry,lock, credentialis - expiry,lock
        activeDirectoryLdapAuthenticationProvider.setConvertSubErrorCodesToExceptions(true);

        return activeDirectoryLdapAuthenticationProvider;
    }
**/
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        //Inherit Security context in async function calls
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        return new CorsFilter(source);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

/**
    @Bean
    public DaoAuthenticationProvider authenticationProvider() throws UsernameNotFoundException{
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       authProvider.setUserDetailsService(username -> userDao
                .findByUsername(username).map(u -> userDetailsConverter.convert((ParticipantModel) u))
                .orElseThrow(()-> new UsernameNotFoundException(format("User %s, not found", username))
                ));
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
 **/

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
