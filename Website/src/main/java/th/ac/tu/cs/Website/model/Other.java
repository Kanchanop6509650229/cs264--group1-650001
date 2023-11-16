package th.ac.tu.cs.Website.model;

public class Other {
    public Other(){}

    private Long id;
    private Long studentDataId;
    private String studentId;
    private String info;
    private String formStatus;
    private String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public Long getStudentDataId() {
        return studentDataId;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public String getInfo() {
        return info;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStudentDataId(Long studentDataId) {
        this.studentDataId = studentDataId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
