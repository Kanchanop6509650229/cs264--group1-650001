package th.ac.tu.cs.Website.repository;

import th.ac.tu.cs.Website.model.StudentData;

public interface StudentDataRepository {
    void saveStudent(StudentData studentData);
    void updateStudent(StudentData studentData);
    StudentData findByStudentId(String id);
}
