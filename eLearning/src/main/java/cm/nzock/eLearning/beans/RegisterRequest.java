package cm.nzock.eLearning.beans;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RegisterRequest implements Serializable {

    @NotNull
    private String username ;

    @NotNull
    private String password;

    @NotNull
    private String oldPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
