package cm.nzock.eLearning.beans;

import java.io.Serializable;
import java.util.Date;

public class InvolveData implements Serializable {
    private Long pk ;
    private ParticipantData concern;
    private Boolean register;
    private String registerDate;
    private String involveType ;
    private TrainingData training;
    private Boolean registrable;
    private short progress = 0;
    private Float mark = 0.0f;
    private String decision ;
    private boolean present ;
    private boolean onlineRegister ;
    private String state;
    private String finishedDate;
    private String startingDate;
    private short nbreOfCompleteActivities;

    public InvolveData() {
        registrable = true;
    }

    public ParticipantData getConcern() {
        return concern;
    }

    public void setConcern(ParticipantData concern) {
        this.concern = concern;
    }

    public Boolean getRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getInvolveType() {
        return involveType;
    }

    public void setInvolveType(String involveType) {
        this.involveType = involveType;
    }

    public TrainingData getTraining() {
        return training;
    }

    public void setTraining(TrainingData training) {
        this.training = training;
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public Boolean getRegistrable() {
        return registrable;
    }

    public void setRegistrable(Boolean registrable) {
        this.registrable = registrable;
    }

    public short getProgress() {
        return progress;
    }

    public void setProgress(short progress) {
        this.progress = progress;
    }

    public Float getMark() {
        return mark;
    }

    public void setMark(Float mark) {
        this.mark = mark;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isOnlineRegister() {
        return onlineRegister;
    }

    public void setOnlineRegister(boolean onlineRegister) {
        this.onlineRegister = onlineRegister;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(String finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public short getNbreOfCompleteActivities() {
        return nbreOfCompleteActivities;
    }

    public void setNbreOfCompleteActivities(short nbreOfCompleteActivities) {
        this.nbreOfCompleteActivities = nbreOfCompleteActivities;
    }
}
