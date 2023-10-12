package cm.nzock.eLearning.security;

import cm.platform.basecommerce.core.security.UserModel;
import cm.platform.myleaninig.core.ParticipantModel;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${isis.oauth2.clientid}")
    private String clientId ;

    @Value("${isis.oauth2.secret}")
    private String secret ;

    @Value("${isis.oauth2.token.ttl}")
    private Integer tokenTtl ;

    public String generateAccessToken(final ParticipantModel user){
        return Jwts.builder()
                .setSubject(String.format("%s,%s",user.getPK(),user.getCode()))
                .setIssuer(clientId)
                .setIssuedAt(new Date())
                .setExpiration(DateUtils.addMinutes(new Date(),tokenTtl))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserId(final String token){
        Claims claims = getClaims(token);
        return claims.getSubject().split(",")[0];
    }



    public String getusername(final String token){
        Claims claims = getClaims(token);
        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(final String token){
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    public boolean validate(final String token){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
          LOG.error("Invalid JWT signature - {}", ex.getMessage());
        }catch (MalformedJwtException ex){
            LOG.error("Invalid JWT Token - {}", ex.getMessage());
        }catch (ExpiredJwtException ex){
            LOG.error("Expired JWT Token - {}", ex.getMessage());
        }catch (UnsupportedJwtException ex){
            LOG.error("Unsupported JWT signature - {}", ex.getMessage());
        }catch (IllegalArgumentException ex){
            LOG.error("JWT claims strings is empty - {}", ex.getMessage());
        }
        return false;
    }

    private Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
