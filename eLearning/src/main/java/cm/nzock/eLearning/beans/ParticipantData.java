package cm.nzock.eLearning.beans;

import java.io.Serializable;

public class ParticipantData implements Serializable {
    private String code;
    private String name;
    private String surname;
    private String email;
    private String gender ;
    private String town ;

    public ParticipantData() {
    }

    public String getCode() {
        return code;
    }

    public ParticipantData setCode(String code) {
        this.code = code;
        return this ;
    }

    public String getName() {
        return name;
    }

    public ParticipantData setName(String name) {
        this.name = name;
        return this ;
    }

    public String getSurname() {
        return surname;
    }

    public ParticipantData setSurname(String surname) {
        this.surname = surname;
        return this ;
    }

    public String getEmail() {
        return email;
    }

    public ParticipantData setEmail(String email) {
        this.email = email;
        return this ;
    }

    public String getGender() {
        return gender;
    }

    public ParticipantData setGender(String gender) {
        this.gender = gender;
        return this ;
    }

    public String getTown() {
        return town;
    }

    public ParticipantData setTown(String town) {
        this.town = town;
        return this ;
    }
}
