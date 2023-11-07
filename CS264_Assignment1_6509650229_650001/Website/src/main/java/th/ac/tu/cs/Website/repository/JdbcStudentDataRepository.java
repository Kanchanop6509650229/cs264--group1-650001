/*
 * กาญจนพ บัวรอด
 * 6509650229
 * 25/10/2566
 * Repository สำหรับเชื่อมต่อกับตัว Database ใน microsoft sql
 */

package th.ac.tu.cs.Website.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import th.ac.tu.cs.Website.model.AddSubject;
import th.ac.tu.cs.Website.model.DropSubject;
import th.ac.tu.cs.Website.model.StudentData;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcStudentDataRepository implements StudentDataRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(StudentData studentData) {

        String sql = "INSERT INTO StudentForm (date, studentFirstName, studentLastName, studentId, studentYear, studyField, advisor, addressNumber, moo, tumbol, amphur, province, postalCode, mobilePhone, phone, cause) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, studentData.getDate(), studentData.getStudentFirstName(), studentData.getStudentLastName(), studentData.getStudentId(), studentData.getStudentYear(), studentData.getStudyField(), studentData.getAdvisor(), studentData.getAddressNumber(), studentData.getMoo(), studentData.getTumbol(), studentData.getAmphur(), studentData.getProvince(), studentData.getPostalCode(), studentData.getMobilePhone(), studentData.getPhone(), studentData.getCause());

        // Get the student ID
        List<Long> studentIds = jdbcTemplate.queryForList("SELECT id FROM StudentForm WHERE studentId = ?", Long.class, studentData.getStudentId());

        // Insert the add subject list
        for (AddSubject addSubject : studentData.getAddSubjectList()) {
            boolean subjectTeacherChecks = addSubject.getSubjectTeacherCheck().equals("true");
            String sql2 = "INSERT INTO AddSubjectList (studentFormId, subjectCode, subjectName, subjectSection, subjectDate, subjectCredit, subjectTeacher, subjectTeacherCheck) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql2, studentIds.get(studentIds.size()-1), addSubject.getSubjectCode(), addSubject.getSubjectName(), addSubject.getSubjectSection(), addSubject.getSubjectDate(), addSubject.getSubjectCredit(), addSubject.getSubjectTeacher(), subjectTeacherChecks);
        }

        // Insert the drop subject list
        for (DropSubject dropSubject : studentData.getDropSubjectList()) {
            boolean subjectTeacherChecks = dropSubject.getSubjectTeacherCheck().equals("true");
            String sql3 = "INSERT INTO DropSubjectList (studentFormId, subjectCode, subjectName, subjectSection, subjectDate, subjectCredit, subjectTeacher, subjectTeacherCheck) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql3, studentIds.get(studentIds.size()-1), dropSubject.getSubjectCode(), dropSubject.getSubjectName(), dropSubject.getSubjectSection(), dropSubject.getSubjectDate(), dropSubject.getSubjectCredit(), dropSubject.getSubjectTeacher(), subjectTeacherChecks);
        }
    }

    @Override
    public void update(StudentData studentData) {
        String sql = "UPDATE StudentForm SET date = ?, studentFirstName = ?, studentLastName = ?, studentYear = ?, studyField = ?, advisor = ?, addressNumber = ?, moo = ?, tumbol = ?, amphur = ?, province = ?, postalCode = ?, mobilePhone = ?, phone = ?, cause = ? WHERE id = ?";
        jdbcTemplate.update(sql, studentData.getDate(), studentData.getStudentFirstName(), studentData.getStudentLastName(), studentData.getStudentYear(), studentData.getStudyField(), studentData.getAdvisor(), studentData.getAddressNumber(), studentData.getMoo(), studentData.getTumbol(), studentData.getAmphur(), studentData.getProvince(), studentData.getPostalCode(), studentData.getMobilePhone(), studentData.getPhone(), studentData.getCause(), studentData.getId());

        // Update the add subject list
        for (AddSubject addSubject : studentData.getAddSubjectList()) {
            boolean subjectTeacherChecks = addSubject.getSubjectTeacherCheck().equals("true");
            String sql2 = "UPDATE AddSubjectList SET subjectCode = ?, subjectName = ?, subjectSection = ?, subjectDate = ?, subjectCredit = ?, subjectTeacher = ?, subjectTeacherCheck = ? WHERE id = ?";
            jdbcTemplate.update(sql2, addSubject.getSubjectCode(), addSubject.getSubjectName(), addSubject.getSubjectSection(), addSubject.getSubjectDate(), addSubject.getSubjectCredit(), addSubject.getSubjectTeacher(), subjectTeacherChecks, addSubject.getId());
        }

        // Update the drop subject list
        for (DropSubject dropSubject : studentData.getDropSubjectList()) {
            boolean subjectTeacherChecks = dropSubject.getSubjectTeacherCheck().equals("true");
            String sql3 = "UPDATE DropSubjectList SET subjectCode = ?, subjectName = ?, subjectSection = ?, subjectDate = ?, subjectCredit = ?, subjectTeacher = ?, subjectTeacherCheck = ? WHERE id = ?";
            jdbcTemplate.update(sql3, dropSubject.getSubjectCode(), dropSubject.getSubjectName(), dropSubject.getSubjectSection(), dropSubject.getSubjectDate(), dropSubject.getSubjectCredit(), dropSubject.getSubjectTeacher(), subjectTeacherChecks, dropSubject.getId());
        }
    }

    @Override
    public StudentData findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM StudentForm WHERE id=?",
                    BeanPropertyRowMapper.newInstance(StudentData.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM AddSubjectList WHERE studentFormId IN (SELECT id FROM StudentForm WHERE id=?", id);
        jdbcTemplate.update("DELETE FROM DropSubjectList WHERE studentFormId IN (SELECT id FROM StudentForm WHERE id=?", id);

        // Delete selected id rows from the StudentForm table.
        return jdbcTemplate.update("DELETE FROM StudentForm WHERE id=?", id);
    }

    @Override
    public List<StudentData> findAll() {
        // Get the StudentForm data
        List<StudentData> studentData = jdbcTemplate.query("SELECT * from StudentForm",
                BeanPropertyRowMapper.newInstance(StudentData.class));

        // Get the AddSubjectList data
        List<AddSubject> addSubjectLists = jdbcTemplate.query("SELECT * from AddSubjectList WHERE studentFormId IN (SELECT id FROM StudentForm)",
                BeanPropertyRowMapper.newInstance(AddSubject.class));

        // Get the DropSubjectList data
        List<DropSubject> dropSubjectLists = jdbcTemplate.query("SELECT * from DropSubjectList WHERE studentFormId IN (SELECT id FROM StudentForm)",
                BeanPropertyRowMapper.newInstance(DropSubject.class));

        // Set the AddSubjectList and DropSubjectList for each StudentData
        for (StudentData studentDataEntry : studentData) {
            studentDataEntry.setAddSubjectList(addSubjectLists.stream().filter(addSubjectList -> addSubjectList.getStudentFormId().equals(studentDataEntry.getId())).collect(Collectors.toList()));
            studentDataEntry.setDropSubjectList(dropSubjectLists.stream().filter(dropSubjectList -> dropSubjectList.getStudentFormId().equals(studentDataEntry.getId())).collect(Collectors.toList()));
        }

        return studentData;
    }

    @Override
    public List<StudentData> findByStudentId(String studentId) {
        // Get the StudentForm data
        List<StudentData> studentData = jdbcTemplate.query("SELECT * from StudentForm WHERE studentId=?",
                BeanPropertyRowMapper.newInstance(StudentData.class), studentId);

        // Get the AddSubjectList data
        List<AddSubject> addSubjectLists = jdbcTemplate.query("SELECT * from AddSubjectList WHERE studentFormId IN (SELECT id FROM StudentForm WHERE studentId=?)",
                BeanPropertyRowMapper.newInstance(AddSubject.class), studentId);

        // Get the DropSubjectList data
        List<DropSubject> dropSubjectLists = jdbcTemplate.query("SELECT * from DropSubjectList WHERE studentFormId IN (SELECT id FROM StudentForm WHERE studentId=?)",
                BeanPropertyRowMapper.newInstance(DropSubject.class), studentId);

        // Set the AddSubjectList and DropSubjectList for each StudentData
        for (StudentData studentDataEntry : studentData) {
            studentDataEntry.setAddSubjectList(addSubjectLists.stream().filter(addSubjectList -> addSubjectList.getStudentFormId().equals(studentDataEntry.getId())).collect(Collectors.toList()));
            studentDataEntry.setDropSubjectList(dropSubjectLists.stream().filter(dropSubjectList -> dropSubjectList.getStudentFormId().equals(studentDataEntry.getId())).collect(Collectors.toList()));
        }

        return studentData;
    }

    @Override
    public int deleteAll() {
        jdbcTemplate.update("DELETE FROM DropSubjectList");
        jdbcTemplate.update("DELETE FROM AddSubjectList");

        // Delete all rows from the StudentForm table.
        return jdbcTemplate.update("DELETE FROM StudentForm");
    }
}
