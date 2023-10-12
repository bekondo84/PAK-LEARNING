package cm.nzock.eLearning.beans;

import java.io.Serializable;

public class LoginComponentData implements Serializable {

    private String title ;
    private String welcomeMsge ;
    private String loginMsge ;
    private String errorMsge ;
    private String mediaUrl ;
    private String message ;
    private String loginLabel ;
    private String passwordLabel ;
    private String connexionBtnLabel;

    public LoginComponentData() {
    }

    public String getTitle() {
        return title;
    }

    public LoginComponentData setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getWelcomeMsge() {
        return welcomeMsge;
    }

    public LoginComponentData setWelcomeMsge(String welcomeMsge) {
        this.welcomeMsge = welcomeMsge;
        return this;
    }

    public String getLoginMsge() {
        return loginMsge;
    }

    public LoginComponentData setLoginMsge(String loginMsge) {
        this.loginMsge = loginMsge;
        return this;
    }

    public String getErrorMsge() {
        return errorMsge;
    }

    public LoginComponentData setErrorMsge(String errorMsge) {
        this.errorMsge = errorMsge;
        return this;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public LoginComponentData setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LoginComponentData setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getLoginLabel() {
        return loginLabel;
    }

    public LoginComponentData setLoginLabel(String loginLabel) {
        this.loginLabel = loginLabel;
        return this;
    }

    public String getPasswordLabel() {
        return passwordLabel;
    }

    public LoginComponentData setPasswordLabel(String passwordLabel) {
        this.passwordLabel = passwordLabel;
        return this;
    }

    public String getConnexionBtnLabel() {
        return connexionBtnLabel;
    }

    public LoginComponentData setConnexionBtnLabel(String connexionBtnLabel) {
        this.connexionBtnLabel = connexionBtnLabel;
        return this;
    }
}
