package th.ac.tu.cs.Website.model;

public class AddDrop {
    public AddDrop(){}
    public AddDrop(String selection, String subjectCode, String subjectName, String subjectSection, String subjectDate, Integer subjectCredit, String subjectTeacher, String subjectTeacherCheck) {
        this.selection = selection;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectSection = subjectSection;
        this.subjectDate = subjectDate;
        this.subjectCredit = subjectCredit;
        this.subjectTeacher = subjectTeacher;
        this.subjectTeacherCheck = subjectTeacherCheck;
    }
    public AddDrop(String selection, String subjectCode, String subjectName, String subjectSection, String subjectDate, Integer subjectCredit, String subjectTeacher, String subjectTeacherCheck, String formStatus) {
        this.selection = selection;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectSection = subjectSection;
        this.subjectDate = subjectDate;
        this.subjectCredit = subjectCredit;
        this.subjectTeacher = subjectTeacher;
        this.subjectTeacherCheck = subjectTeacherCheck;
        this.formStatus = formStatus;
    }

    private Long id;
    private Long studentDataId;
    private String userId;
    private String selection;
    private String subjectCode;
    private String subjectName;
    private String subjectSection;
    private String subjectDate;
    private Integer subjectCredit;
    private String subjectTeacher;
    private String subjectTeacherCheck;
    private String formStatus;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setStudentDataId(Long studentDataId) {
        this.studentDataId = studentDataId;
    }

    public Long getStudentDataId() {
        return studentDataId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getSelection() {
        return selection;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectSection() {
        return subjectSection;
    }

    public void setSubjectSection(String subjectSection) {
        this.subjectSection = subjectSection;
    }

    public String getSubjectDate() {
        return subjectDate;
    }

    public void setSubjectDate(String subjectDate) {
        this.subjectDate = subjectDate;
    }

    public Integer getSubjectCredit() {
        return subjectCredit;
    }

    public void setSubjectCredit(Integer subjectCredit) {
        this.subjectCredit = subjectCredit;
    }

    public String getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(String subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    public String getSubjectTeacherCheck() {
        return subjectTeacherCheck;
    }

    public void setSubjectTeacherCheck(String subjectTeacherCheck) {
        this.subjectTeacherCheck = subjectTeacherCheck;
    }

    public String getFormStatus() {
        return formStatus;
    }
    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }
}
