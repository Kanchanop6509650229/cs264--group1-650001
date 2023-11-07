/*
 * กาญจนพ บัวรอด
 * 6509650229
 * 25/10/2566
 * StudentData สำหรับเก็บข้อมูลโดยรวมของแบบฟอร์ม
 */

package th.ac.tu.cs.Website.model;

import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;


public class StudentData {

    private Long id;

    private String date;

    private String studentFirstName;
    private String studentLastName;

    private String studentId;

    private Integer studentYear;

    private String studyField;

    private String addressNumber;

    private Integer moo;

    private String tumbol;

    private String amphur;

    private String province;

    private Integer postalCode;

    private String mobilePhone;

    private String phone;

    private String advisor;

    private String cause;

    private List<AddSubject> addSubjectList;


    private List<DropSubject> dropSubjectList;


    public StudentData() {
    }

    public StudentData(String date, String studentFirstName, String studentLastName, String studentId, Integer studentYear, String studyField, String addressNumber, Integer moo, String tumbol, String amphur, String province, Integer postalCode, String mobilePhone, String phone, String advisor, String cause, List<AddSubject> AddSubjectItems , List<DropSubject> DropSubjectItems) {
        this.date = date;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentId = studentId;
        this.studentYear = studentYear;
        this.studyField = studyField;
        this.addressNumber = addressNumber;
        this.moo = moo;
        this.tumbol = tumbol;
        this.amphur = amphur;
        this.province = province;
        this.postalCode = postalCode;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.advisor = advisor;
        this.cause = cause;
        this.addSubjectList = AddSubjectItems;
        this.dropSubjectList = DropSubjectItems;
    }

    public static StudentData fromFormData(MultiValueMap<String, String> formData) {
        StudentData studentData = new StudentData();
        studentData.setDate(formData.getFirst("date"));
        String name = formData.getFirst("nameTitle") + formData.getFirst("name");
        String[] splitName = name.split(" ");
        if(splitName.length <= 1){
            studentData.setStudentFirstName(splitName[0]);
        } else if(splitName.length > 1){
            studentData.setStudentFirstName(splitName[0]);
            studentData.setStudentLastName(splitName[1]);
        }
        studentData.setStudentId(formData.getFirst("studentId"));
        studentData.setStudentYear(Integer.parseInt(formData.getFirst("studentYear")));
        studentData.setStudyField(formData.getFirst("studyField"));
        studentData.setAddressNumber(formData.getFirst("addressNumber"));
        studentData.setMoo(Integer.parseInt(formData.getFirst("moo")));
        studentData.setTumbol(formData.getFirst("tumbol"));
        studentData.setAmphur(formData.getFirst("amphur"));
        studentData.setProvince(formData.getFirst("province"));
        studentData.setPostalCode(Integer.parseInt(formData.getFirst("postalCode")));
        studentData.setMobilePhone(formData.getFirst("mobilePhone"));
        if(formData.getFirst("phone").isEmpty()){
            studentData.setPhone("-");
        } else {
            studentData.setPhone(formData.getFirst("phone"));
        }
        studentData.setAdvisor(formData.getFirst("advisor"));
        studentData.setCause(formData.getFirst("cause"));
        // Parse and set the table data
        List<AddSubject> addDataList = new ArrayList<>();
        List<DropSubject> dropDataList = new ArrayList<>();
        List<String> subjectSelect = formData.get("selection");
        List<String> subjectCodes = formData.get("subjectCode");
        List<String> subjectNames = formData.get("subjectName");
        List<String> subjectSections = formData.get("subjectSection");
        List<String> subjectDates = formData.get("subjectDate");
        List<String> subjectCredits = formData.get("subjectCredit");
        List<String> subjectTeachers = formData.get("subjectTeacher");
        List<String> subjectTeacherChecks = formData.get("subjectTeacherCheck");

        for (int i = 0; i < subjectCodes.size(); i++) {
            if(subjectSelect.get(i).equals("add")){
                AddSubject subjectData = new AddSubject();
                subjectData.setSubjectCode(subjectCodes.get(i));
                subjectData.setSubjectName(subjectNames.get(i));
                subjectData.setSubjectSection(subjectSections.get(i));
                subjectData.setSubjectDate(subjectDates.get(i));
                subjectData.setSubjectCredit(Integer.parseInt(subjectCredits.get(i)));
                subjectData.setSubjectTeacher(subjectTeachers.get(i));
                subjectData.setSubjectTeacherCheck(subjectTeacherChecks.get(i));
                addDataList.add(subjectData);
            } else if(subjectSelect.get(i).equals("remove")){
                DropSubject subjectData = new DropSubject();
                subjectData.setSubjectCode(subjectCodes.get(i));
                subjectData.setSubjectName(subjectNames.get(i));
                subjectData.setSubjectSection(subjectSections.get(i));
                subjectData.setSubjectDate(subjectDates.get(i));
                subjectData.setSubjectCredit(Integer.parseInt(subjectCredits.get(i)));
                subjectData.setSubjectTeacher(subjectTeachers.get(i));
                subjectData.setSubjectTeacherCheck(subjectTeacherChecks.get(i));
                dropDataList.add(subjectData);
            }
        }
        studentData.setAddSubjectList(addDataList);
        studentData.setDropSubjectList(dropDataList);
        return studentData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }
    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentYear() {
        return studentYear;
    }

    public void setStudentYear(Integer studentYear) {
        this.studentYear = studentYear;
    }

    public String getStudyField() {
        return studyField;
    }

    public void setStudyField(String studyField) {
        this.studyField = studyField;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public Integer getMoo() {
        return moo;
    }

    public void setMoo(Integer moo) {
        this.moo = moo;
    }

    public String getTumbol() {
        return tumbol;
    }

    public void setTumbol(String tumbol) {
        this.tumbol = tumbol;
    }

    public String getAmphur() {
        return amphur;
    }

    public void setAmphur(String amphur) {
        this.amphur = amphur;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public List<AddSubject> getAddSubjectList() {
        return addSubjectList;
    }

    public void setAddSubjectList(List<AddSubject> addSubjectList) {
        this.addSubjectList = addSubjectList;
    }
    public List<DropSubject> getDropSubjectList() {
        return dropSubjectList;
    }

    public void setDropSubjectList(List<DropSubject> dropSubjectList) {
        this.dropSubjectList = dropSubjectList;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
