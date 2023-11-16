package th.ac.tu.cs.Website.model;

import java.util.List;

public class AllData {
    StudentData studentData;
    List<AddDrop> addDropList;
    List<Late> lateList;
    List<Other> otherList;
    List<Quit> quitList;

    public AllData(){}

    public StudentData getStudentData() {
        return studentData;
    }

    public void setStudentData(StudentData studentData) {
        this.studentData = studentData;
    }

    public List<AddDrop> getAddDropList() {
        return addDropList;
    }

    public List<Late> getLateList() {
        return lateList;
    }

    public List<Other> getOtherList() {
        return otherList;
    }

    public List<Quit> getQuitList() {
        return quitList;
    }

    public void setAddDropList(List<AddDrop> addDropList) {
        this.addDropList = addDropList;
    }

    public void setLateList(List<Late> lateList) {
        this.lateList = lateList;
    }

    public void setOtherList(List<Other> otherList) {
        this.otherList = otherList;
    }

    public void setQuitList(List<Quit> quitList) {
        this.quitList = quitList;
    }
}
