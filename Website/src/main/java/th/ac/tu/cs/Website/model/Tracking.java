package th.ac.tu.cs.Website.model;

public class Tracking {

    public Tracking() {}

    private Long formId;
    private Long studentDataId;
    private int order;
    private String dateTime;
    private String status;
    private String formName;
    private String studentName;

    public void setStudentDataId(Long studentDataId) {
        this.studentDataId = studentDataId;
    }

    public Long getStudentDataId() {
        return studentDataId;
    }

    public int getOrder() {
        return order;
    }

    public Long getFormId() {
        return formId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getFormName() {
        return formName;
    }

    public String getStatus() {
        return status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
