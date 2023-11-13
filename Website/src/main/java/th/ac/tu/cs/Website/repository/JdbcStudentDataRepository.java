package th.ac.tu.cs.Website.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import th.ac.tu.cs.Website.model.StudentData;

import java.util.List;

@Repository
public class JdbcStudentDataRepository implements StudentDataRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveStudent(StudentData studentData) {
        String sql = "INSERT INTO StudentData (studentId, firstName, lastName, studentYear, faculty, department, addressNumber, moo, tumbol, amphur, province, postalCode, mobilePhone, phone, advisor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, studentData.getStudentId(), studentData.getFirstName(), studentData.getLastName(), studentData.getStudentYear(), studentData.getFaculty(), studentData.getDepartment(), studentData.getAddressNumber(), studentData.getMoo(), studentData.getTumbol(), studentData.getAmphur(), studentData.getProvince(), studentData.getPostalCode(), studentData.getMobilePhone(), studentData.getPhone(), studentData.getAdvisor());
    }

    @Override
    public void updateStudent(StudentData studentData) {
        String sql = "UPDATE StudentData SET firstName = ?, lastName = ?, studentYear = ?, faculty = ?, department = ?, addressNumber = ?, moo = ?, tumbol = ?, amphur = ?, province = ?, postalCode = ?, mobilePhone = ?, phone = ?, advisor = ? WHERE studentId = ?";
        jdbcTemplate.update(
                sql,
                studentData.getFirstName(),
                studentData.getLastName(),
                studentData.getStudentYear(),
                studentData.getFaculty(),
                studentData.getDepartment(),
                studentData.getAddressNumber(),
                studentData.getMoo(),
                studentData.getTumbol(),
                studentData.getAmphur(),
                studentData.getProvince(),
                studentData.getPostalCode(),
                studentData.getMobilePhone(),
                studentData.getPhone(),
                studentData.getAdvisor(),
                studentData.getStudentId()
        );
    }

    @Override
    public StudentData findByStudentId(String id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM StudentData WHERE studentId = ?",
                    new Object[]{id},
                    (rs, rowNum) -> {
                        StudentData student = new StudentData();
                        student.setId(rs.getLong("id"));
                        student.setStudentId(rs.getString("studentId"));
                        student.setFirstName(rs.getString("firstName"));
                        student.setLastName(rs.getString("lastName"));
                        student.setStudentYear(rs.getInt("studentYear"));
                        student.setFaculty(rs.getString("faculty"));
                        student.setDepartment(rs.getString("department"));
                        student.setAddressNumber(rs.getString("addressNumber"));
                        student.setMoo(rs.getInt("moo"));
                        student.setTumbol(rs.getString("tumbol"));
                        student.setAmphur(rs.getString("amphur"));
                        student.setProvince(rs.getString("province"));
                        student.setPostalCode(rs.getString("postalCode"));
                        student.setMobilePhone(rs.getString("mobilePhone"));
                        student.setPhone(rs.getString("phone"));
                        student.setAdvisor(rs.getString("advisor"));
                        return student;
                    }
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

}