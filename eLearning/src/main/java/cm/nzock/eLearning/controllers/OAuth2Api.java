package cm.nzock.eLearning.controllers;

import cm.nzock.eLearning.beans.AuthRequest;
import cm.nzock.eLearning.beans.AuthResponse;
import cm.nzock.eLearning.beans.RegisterRequest;
import cm.nzock.eLearning.converters.UserDataConverter;
import cm.nzock.eLearning.converters.UserDetailsConverter;
import cm.nzock.eLearning.dao.ParticipantDao;
import cm.nzock.eLearning.security.JwtTokenUtil;
import cm.platform.basecommerce.core.dao.UserDao;
import cm.platform.basecommerce.core.exception.NzockException;
import cm.platform.basecommerce.core.security.UserModel;
import cm.platform.basecommerce.core.utils.IsisConstants;
import cm.platform.basecommerce.services.I18NService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.ParticipantModel;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.*;


@Api(tags = "Authorization")
@RestController
@RequestMapping(path = OAuth2Api.ROOT)
public class OAuth2Api {

    private static final Logger LOG = LoggerFactory.getLogger(OAuth2Api.class);
    public static final String AUTHENTICATE_PATH = "/authenticate";
    public static final String RESET_PASSWORD = "/resetPassword";
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ParticipantDao userDao;
    private final UserDetailsConverter userDetailsConcerter;
    private final ModelService modelService ;
    private final PasswordEncoder passwordEncoder;
    private final UserDataConverter userDataConverter ;
    public static final String ROOT = IsisConstants.ApiScope.PUBLIC;

    @Autowired
    private I18NService i18NService;

    /**
     *
     * @param authenticationManager
     * @param jwtTokenUtil
     * @param userDao
     * @param userDetailsConcerter
     * @param modelService
     * @param passwordEncoder
     */
    @Autowired
    public OAuth2Api(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, ParticipantDao userDao, UserDetailsConverter userDetailsConcerter
            , ModelService modelService, PasswordEncoder passwordEncoder, UserDataConverter converter) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDao = userDao;
        this.userDetailsConcerter = userDetailsConcerter;
        this.modelService = modelService;
        this.passwordEncoder = passwordEncoder;
        this.userDataConverter = converter;
    }

    @CrossOrigin("*")
    @PostMapping(AUTHENTICATE_PATH)
    public ResponseEntity authenticate(@RequestBody @Valid AuthRequest request) throws ModelServiceException, EntityNotFoundException {
            ParticipantModel user = userDao.findByUsername(request.getUsername()).orElseThrow(()->new EntityNotFoundException(format("User %s not found", request.getUsername())));
            //Reinitialize the user token
            user.setToken(null);
            modelService.save(user);

            //Authenticate the user
            Authentication authentication = authenticationManager
                    .authenticate(
                      new UsernamePasswordAuthenticationToken(
                              request.getUsername(), request.getPassword()
                      )
                    );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            final String token = jwtTokenUtil.generateAccessToken(user) ;

            //Set user session token
            user.setToken(token);
            modelService.save(user);
            AuthResponse response = new AuthResponse()
                       .accessToken(token)
                       .typeToken("bearer")
                       .expiresIn(jwtTokenUtil.getExpirationDate(token).getTime())
                       .user(userDataConverter.convert(user));

            if (Objects.nonNull(user.getDefaultLanguage())) {
                Locale.setDefault(Locale.forLanguageTag(user.getDefaultLanguage().getCode()));
            }
            return ResponseEntity.ok(response);

    }

    @CrossOrigin("*")
    @PostMapping(RESET_PASSWORD)
    public void register(@RequestBody @Valid RegisterRequest request) throws Throwable {
        final Optional result = userDao.findByUsername(request.getUsername());
        final ParticipantModel user = (ParticipantModel) result
                .orElseThrow(()->new EntityNotFoundException(format("User %s, not found", request.getUsername())));

        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setToken(null);
            modelService.save(user);
        } else {
            throw  new NzockException(i18NService.getLabel("password.not.match", "L'ancien mot de passe ne correspond pas à celui enregistré"));
        }
    }
}
