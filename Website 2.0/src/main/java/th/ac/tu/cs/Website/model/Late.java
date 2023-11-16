package th.ac.tu.cs.Website.model;

public class Late {

    private Long id;
    private Long studentDataId;
    private String studentId;
    private String semester;
    private String year;
    private String payDate;
    private String reason;
    private String formStatus;
    private String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public Late(){}

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSemester() {
        return semester;
    }

    public Long getStudentDataId() {
        return studentDataId;
    }

    public String getYear() {
        return year;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPayDate() {
        return payDate;
    }

    public String getReason() {
        return reason;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStudentDataId(Long studentDataId) {
        this.studentDataId = studentDataId;
    }
}
