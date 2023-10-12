package cm.nzock.eLearning.security;

import cm.nzock.eLearning.converters.UserDetailsConverter;
import cm.nzock.eLearning.dao.ParticipantDao;
import cm.platform.basecommerce.core.dao.UserDao;
import cm.platform.basecommerce.core.security.UserModel;
import cm.platform.myleaninig.core.ParticipantModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final ParticipantDao userDao;

    private final UserDetailsConverter userDetailsConverter;

    @Autowired
    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, ParticipantDao userDao, UserDetailsConverter userDetailsConverter) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDao = userDao;
        this.userDetailsConverter = userDetailsConverter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            //Get authorization header and validate
            if (StringUtils.isBlank(header) || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            //Get jwt token and validate
           final String token = header.split(" ")[1].trim();
            if (!jwtTokenUtil.validate(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            //Get user identity and set it on the spring security context
        ParticipantModel user = (ParticipantModel) userDao.findByUsername(jwtTokenUtil.getusername(token))
                .orElse(null);
        UserDetails userDetails = userDetailsConverter.convert(user);
        UsernamePasswordAuthenticationToken
                authenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails,null,
          userDetails == null ? CollectionUtils.emptyCollection() : userDetails.getAuthorities()
        );
        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
