package cm.nzock.eLearning.beans;

import java.io.Serializable;
import java.util.Date;

public class TimesheetData implements Serializable {
    private Date day;
    private String startAt;
    private String endAt;
    private String subject;

    public TimesheetData() {
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
