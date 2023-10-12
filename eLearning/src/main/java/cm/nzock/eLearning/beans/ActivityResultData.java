package cm.nzock.eLearning.beans;

import java.io.Serializable;

public class ActivityResultData implements Serializable {
    private AbstractActivityData activity;
    private ParticipantData concern ;

    public ActivityResultData() {
    }

    public AbstractActivityData getActivity() {
        return activity;
    }

    public void setActivity(AbstractActivityData activity) {
        this.activity = activity;
    }

    public ParticipantData getConcern() {
        return concern;
    }

    public void setConcern(ParticipantData concern) {
        this.concern = concern;
    }
}
