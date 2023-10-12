package cm.nzock.eLearning.beans;

public class AuthResponse {
    private String access_token ;
    private String token_type = "bearer";
    private Long expires_in ;
    private UserData user ;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }


    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

   public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public AuthResponse accessToken(String access_token){
        this.access_token = access_token;
        return this;
    }

    public AuthResponse typeToken(String token_type){
        this.token_type = token_type;
        return this ;
    }

    public AuthResponse expiresIn(Long duration){
        this.expires_in = duration;
        return this ;
    }

    public AuthResponse user(UserData user) {
        this.user = user;
        return this;
    }
}
