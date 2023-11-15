package th.ac.tu.cs.Website.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import th.ac.tu.cs.Website.model.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;

@Repository
public class JdbcFormRepository implements FormRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveAddDrop(AddDrop addDropSubject){
        // Get the current timestamp
        Timestamp timestamp = new Timestamp(new Date().getTime());

        // Format the timestamp as "dd/MM/yyyy HH:mm"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedTimestamp = dateFormat.format(timestamp);

        List<Long> studentDataId = jdbcTemplate.queryForList("SELECT id FROM StudentData WHERE studentId = ?", Long.class, addDropSubject.getUserId());
        
        boolean subjectTeacherChecks;
        subjectTeacherChecks = addDropSubject.getSubjectTeacherCheck().equals("true");

        String sql = "INSERT INTO AddDropSubjectList (studentDataId, selection, subjectCode, subjectName, subjectSection, subjectDate, subjectCredit, subjectTeacher, subjectTeacherCheck, formStatus, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, studentDataId.get(0), addDropSubject.getSelection(), addDropSubject.getSubjectCode(), addDropSubject.getSubjectName(), addDropSubject.getSubjectSection(), addDropSubject.getSubjectDate(), addDropSubject.getSubjectCredit(), addDropSubject.getSubjectTeacher(), subjectTeacherChecks, addDropSubject.getFormStatus(), formattedTimestamp);
        
    }

    @Override
    public void saveQuitForm(Quit quit){
        // Get the current timestamp
        Timestamp timestamp = new Timestamp(new Date().getTime());

        // Format the timestamp as "dd/MM/yyyy HH:mm"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedTimestamp = dateFormat.format(timestamp);

        List<Long> studentDataId = jdbcTemplate.queryForList("SELECT id FROM StudentData WHERE studentId = ?", Long.class, quit.getStudentId());

        boolean reqGrade;
        reqGrade = quit.getReqGrade().equals("yes");
        if (quit.getQuitReason().equals("tu")){
            String sql = "INSERT INTO QuitForm (studentDataId, semester, year, quitReason, tuFaculty, tuDepartment, outstandingDebt, reqGrade, formStatus, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, studentDataId.get(0), quit.getSemester(), quit.getYear(), quit.getQuitReason(), quit.getTuFaculty(), quit.getTuDepartment(), quit.getOutstandingDebt(), reqGrade, quit.getFormStatus(), formattedTimestamp);
        } else {
            String sql = "INSERT INTO QuitForm (studentDataId, semester, year, quitReason, university, faculty, department, outstandingDebt, reqGrade, formStatus, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, studentDataId.get(0), quit.getSemester(), quit.getYear(), quit.getQuitReason(), quit.getUniversity(), quit.getFaculty(), quit.getDepartment(), quit.getOutstandingDebt(), reqGrade, quit.getFormStatus(), formattedTimestamp);
        }

    }

    @Override
    public void saveLateForm(Late late){
        // Get the current timestamp
        Timestamp timestamp = new Timestamp(new Date().getTime());

        // Format the timestamp as "dd/MM/yyyy HH:mm"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedTimestamp = dateFormat.format(timestamp);

        List<Long> studentDataId = jdbcTemplate.queryForList("SELECT id FROM StudentData WHERE studentId = ?", Long.class, late.getStudentId());

            String sql = "INSERT INTO LateForm (studentDataId, semester, year, payDate, reason, formStatus, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, studentDataId.get(0), late.getSemester(), late.getYear(), late.getPayDate(), late.getReason(), late.getFormStatus(), formattedTimestamp);

    }

    @Override
    public void saveOtherForm(Other other){
        // Get the current timestamp
        Timestamp timestamp = new Timestamp(new Date().getTime());

        // Format the timestamp as "dd/MM/yyyy HH:mm"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedTimestamp = dateFormat.format(timestamp);

        List<Long> studentDataId = jdbcTemplate.queryForList("SELECT id FROM StudentData WHERE studentId = ?", Long.class, other.getStudentId());

        String sql = "INSERT INTO OtherForm (studentDataId, info, formStatus, created_at) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, studentDataId.get(0), other.getInfo(), other.getFormStatus(), formattedTimestamp);

    }

    @Override
    public AllData searchForm(String id) {
        try {
            List<StudentData> studentData = jdbcTemplate.query("SELECT * from StudentData WHERE studentId=?",
                    BeanPropertyRowMapper.newInstance(StudentData.class), id);

            List<Late> lateList = jdbcTemplate.query("SELECT * from LateForm WHERE studentDataId IN (SELECT id FROM StudentData WHERE studentId=?)",
                    BeanPropertyRowMapper.newInstance(Late.class), id);

            List<AddDrop> addDropList = jdbcTemplate.query("SELECT * from AddDropSubjectList WHERE studentDataId IN (SELECT id FROM StudentData WHERE studentId=?)",
                    BeanPropertyRowMapper.newInstance(AddDrop.class), id);

            List<Other> otherList = jdbcTemplate.query("SELECT * from OtherForm WHERE studentDataId IN (SELECT id FROM StudentData WHERE studentId=?)",
                    BeanPropertyRowMapper.newInstance(Other.class), id);

            List<Quit> quitList = jdbcTemplate.query("SELECT * from QuitForm WHERE studentDataId IN (SELECT id FROM StudentData WHERE studentId=?)",
                    BeanPropertyRowMapper.newInstance(Quit.class), id);

            AllData alldata = new AllData();
            alldata.setLateList(lateList);
            alldata.setAddDropList(addDropList);
            alldata.setOtherList(otherList);
            alldata.setQuitList(quitList);
            alldata.setStudentData(studentData.get(0));

            return  alldata;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
