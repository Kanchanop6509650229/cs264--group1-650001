/*
 * กาญจนพ บัวรอด
 * 6509650229
 * 25/10/2566
 * DropSubject สำหรับเก็บข้อมูลวิชาที่ทำการถอน
*/

package th.ac.tu.cs.Website.model;

public class DropSubject implements SubjectData{

    private Long id;
    private Long studentFormId;

    private String subjectCode;

    private String subjectName;

    private String subjectSection;

    private String subjectDate;

    private Integer subjectCredit;

    private String subjectTeacher;

    private String subjectTeacherCheck;


    public DropSubject() {
    }

    public DropSubject(String subjectCode, String subjectName, String subjectSection, String subjectDate, Integer subjectCredit, String subjectTeacher, String subjectTeacherCheck) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectSection = subjectSection;
        this.subjectDate = subjectDate;
        this.subjectCredit = subjectCredit;
        this.subjectTeacher = subjectTeacher;
        this.subjectTeacherCheck = subjectTeacherCheck;
    }

    public void setStudentFormId(Long studentFormId) {
        this.studentFormId = studentFormId;
    }

    public Long getStudentFormId() {
        return studentFormId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


}