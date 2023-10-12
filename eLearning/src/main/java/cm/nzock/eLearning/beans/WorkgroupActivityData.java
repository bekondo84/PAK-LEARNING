package cm.nzock.eLearning.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WorkgroupActivityData extends AbstractActivityData implements Serializable {
    private Boolean virtual;
    private ClassroomData classroom;
    private Short size;
    private Short available;
    private Short reserve;
    private TrainingData training ;
    private Boolean onlineRegister;
    private List<TimesheetData> timesheets ;

    public WorkgroupActivityData() {
        super();
        timesheets = new ArrayList<>();
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public ClassroomData getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomData classroom) {
        this.classroom = classroom;
    }

    public Short getSize() {
        return size;
    }

    public void setSize(Short size) {
        this.size = size;
    }

    public Short getAvailable() {
        return available;
    }

    public void setAvailable(Short available) {
        this.available = available;
    }

    public Short getReserve() {
        return reserve;
    }

    public void setReserve(Short reserve) {
        this.reserve = reserve;
    }

    public List<TimesheetData> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<TimesheetData> timesheets) {
        this.timesheets = Collections.unmodifiableList(timesheets);
    }

    public void addTimesheet(final TimesheetData timesheet) {
        this.timesheets.add(timesheet);
    }

    public TrainingData getTraining() {
        return training;
    }

    public void setTraining(TrainingData training) {
        this.training = training;
    }

    public Boolean getOnlineRegister() {
        return onlineRegister;
    }

    public void setOnlineRegister(Boolean onlineRegister) {
        this.onlineRegister = onlineRegister;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
