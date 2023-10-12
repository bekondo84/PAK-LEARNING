package cm.nzock.eLearning.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrainingData implements Serializable {
    private String code;
    private String name;
    private String image;
    private String startAt;
    private String endAt;
    private String resume ;
    private String description ;
    private MediaData media ;
    private ThemeData theme ;
    private List<AbstractActivityData> activities;
    private List<InvolveData> involves;
    private InvolveData concern;
    protected Float skill;
    private String state ;

    public TrainingData() {
        activities = new ArrayList<>();
        skill  = 0.0f ;
    }

    public String getCode() {
        return code;
    }

    public TrainingData setCode(String code) {
        this.code = code;
        return this ;
    }

    public String getName() {
        return name;
    }

    public TrainingData setName(String name) {
        this.name = name;
        return this ;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getResume() {
        return resume;
    }

    public TrainingData setResume(String resume) {
        this.resume = resume;
        return this ;
    }

    public String getDescription() {
        return description;
    }

    public TrainingData setDescription(String description) {
        this.description = description;
        return this ;
    }

    public MediaData getMedia() {
        return media;
    }

    public TrainingData setMedia(MediaData media) {
        this.media = media;
        return this ;
    }

    public ThemeData getTheme() {
        return theme;
    }

    public TrainingData setTheme(ThemeData theme) {
        this.theme = theme;
        return this ;
    }

    public Float getSkill() {
        return skill;
    }

    public void setSkill(Float skill) {
        this.skill = skill;
    }

    public List<AbstractActivityData> getActivities() {
        return activities;
    }

    public void setActivities(List<AbstractActivityData> activities) {
        this.activities = activities;
    }

    public void addActivity(AbstractActivityData item) {
        activities.add(item);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<InvolveData> getInvolves() {
        return involves;
    }

    public void setInvolves(List<InvolveData> involves) {
        this.involves = involves;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public InvolveData getConcern() {
        return concern;
    }

    public void setConcern(InvolveData concern) {
        this.concern = concern;
    }
}
