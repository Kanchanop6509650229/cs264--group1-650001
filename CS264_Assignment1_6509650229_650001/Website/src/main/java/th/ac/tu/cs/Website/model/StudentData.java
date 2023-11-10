package th.ac.tu.cs.Website.model;

public class StudentData {
    private long id;
    private String studentId;
    private String firstName;
    private String lastName;
    private int studentYear;
    private String faculty;
    private String department;
    private String addressNumber;
    private int moo;
    private String tumbol;
    private String amphur;
    private String province;
    private String postalCode;
    private String mobilePhone;
    private String phone;
    private String advisor;

    public StudentData(){

    }

    public StudentData(String studentId, String firstName, String lastName, int studentYear, String faculty, String department, String addressNumber, int moo, String tumbol, String amphur, String province, String postalCode, String mobilePhone, String phone, String advisor){
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentYear = studentYear;
        this.faculty = faculty;
        this.department = department;
        this.addressNumber = addressNumber;
        this.moo = moo;
        this.tumbol = tumbol;
        this.amphur = amphur;
        this.province = province;
        this.postalCode = postalCode;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.advisor = advisor;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getStudentId() {
        return studentId;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setStudentYear(int studentYear) {
        this.studentYear = studentYear;
    }
    public int getStudentYear() {
        return studentYear;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    public String getFaculty() {
        return faculty;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDepartment() {
        return department;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }
    public String getAddressNumber() {
        return addressNumber;
    }

    public void setMoo(int moo) {
        this.moo = moo;
    }
    public int getMoo() {
        return moo;
    }

    public void setTumbol(String tumbol) {
        this.tumbol = tumbol;
    }
    public String getTumbol() {
        return tumbol;
    }

    public void setAmphur(String amphur) {
        this.amphur = amphur;
    }
    public String getAmphur() {
        return amphur;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getProvince() {
        return province;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getPostalCode() {
        return postalCode;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }
    public String getAdvisor() {
        return advisor;
    }
}
