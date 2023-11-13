package th.ac.tu.cs.Website.model;

public class Quit {
    private Long id;
    private Long studentDataId;
    private String studentId;
    private String semester;
    private String year;
    private String quitReason;
    private String tuFaculty;
    private String tuDepartment;
    private String university;
    private String faculty;
    private String department;
    private String outstandingDebt;
    private String reqGrade;
    private String formStatus;
    public Quit(){}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentDataId() {
        return studentDataId;
    }
    public void setStudentDataId(Long studentDataId) {
        this.studentDataId = studentDataId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getOutstandingDebt() {
        return outstandingDebt;
    }

    public String getQuitReason() {
        return quitReason;
    }

    public String getReqGrade() {
        return reqGrade;
    }

    public String getSemester() {
        return semester;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getTuDepartment() {
        return tuDepartment;
    }

    public String getTuFaculty() {
        return tuFaculty;
    }

    public String getUniversity() {
        return university;
    }

    public String getYear() {
        return year;
    }

    public void setOutstandingDebt(String outstandingDebt) {
        this.outstandingDebt = outstandingDebt;
    }

    public void setQuitReason(String quitReason) {
        this.quitReason = quitReason;
    }

    public void setReqGrade(String reqGrade) {
        this.reqGrade = reqGrade;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setTuDepartment(String tuDepartment) {
        this.tuDepartment = tuDepartment;
    }

    public void setTuFaculty(String tuFaculty) {
        this.tuFaculty = tuFaculty;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public String getFormStatus() {
        return formStatus;
    }
}
