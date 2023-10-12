package cm.nzock.eLearning.security;

import cm.nzock.eLearning.converters.UserDetailsConverter;
import cm.nzock.eLearning.dao.ParticipantDao;
import cm.platform.basecommerce.core.security.UserModel;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.UserService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.ParticipantModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

public class JwtTokenRepository implements CsrfTokenRepository {
    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenRepository.class);

    private static final String headerName = "X-CSRF-TOKEN";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ParticipantDao userDao;
    @Autowired
    private ModelService modelService;
    @Autowired
    private UserDetailsConverter userDetailsConverter;

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String username = request.getParameter("username");
        final ParticipantModel user = userDao.findByUsername(username).orElse(null);

        if (StringUtils.isNoneBlank(username) && Objects.nonNull(user)) {
            return new DefaultCsrfToken(headerName, "_csrf", jwtTokenUtil.generateAccessToken(user));
        }
        return null;
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        String username = request.getParameter("username");
        final ParticipantModel user = userDao.findByUsername(username).orElse(null);
        if (Objects.nonNull(user)) {
            user.setToken(csrfToken.getToken());
            try {
                modelService.save(user);
            } catch (ModelServiceException e) {
                LOG.error(String.format("Fail to save user token du to : %s", e.getMessage()));
            }
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String username = request.getParameter("username");

        if (StringUtils.isBlank(username)) {

            if (Objects.nonNull(getContext().getAuthentication())) {
               Object principal = getContext().getAuthentication().getPrincipal();

               if (principal instanceof UserDetails) {
                   username = ((UserDetails)principal).getUsername();
               } else {
                   username = principal.toString();
               }
            }
        }
        //Check if there is username
        if (StringUtils.isNoneBlank(username)) {
            final ParticipantModel user = userDao.findByUsername(username).orElse(null);

            if (Objects.nonNull(user) && StringUtils.isNoneBlank(user.getToken())) {
                     return new DefaultCsrfToken(headerName, "_csrf", user.getToken());
            }
        }
        return null;
    }
}
