package cm.nzock.eLearning.beans;

import java.io.Serializable;

public class PasswordResetData implements Serializable {

    private String old;

    private String current ;

    private String confirm ;

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
