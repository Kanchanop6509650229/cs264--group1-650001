/*
 * กาญจนพ บัวรอด
 * 6509650229
 * 25/10/2566
 * Interface สำหรับ repository
 */

package th.ac.tu.cs.Website.repository;

import th.ac.tu.cs.Website.model.StudentData;

import java.util.List;

public interface StudentDataRepository {
    void save(StudentData studentData);

    void update(StudentData studentData);

    StudentData findById(Long id);

    int deleteById(Long id);

    List<StudentData> findAll();

    List<StudentData> findByStudentId(String studentId);
    int deleteAll();
}
