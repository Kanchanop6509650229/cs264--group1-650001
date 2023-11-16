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

    @Override
    public Late searchLateForm(String formId){
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM LateForm WHERE id = ?",
                    new Object[]{formId},
                    (rs, rowNum) -> {
                        Late late = new Late();
                        late.setId(rs.getLong("id"));
                        late.setStudentDataId(rs.getLong("studentDataId"));
                        late.setSemester(rs.getString("semester"));
                        late.setPayDate(rs.getString("payDate"));
                        late.setYear(rs.getString("year"));
                        late.setReason(rs.getString("reason"));
                        late.setFormStatus(rs.getString("formStatus"));
                        late.setCreated_at(rs.getString("created_at"));
                        return late;
                    }
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Quit searchQuitForm(String formId){
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM QuitForm WHERE id = ?",
                    new Object[]{formId},
                    (rs, rowNum) -> {
                        Quit quit = new Quit();
                        quit.setId(rs.getLong("id"));
                        quit.setStudentDataId(rs.getLong("studentDataId"));
                        quit.setSemester(rs.getString("semester"));
                        quit.setYear(rs.getString("year"));
                        quit.setQuitReason(rs.getString("quitReason"));
                        quit.setTuFaculty(rs.getString("tuFaculty"));
                        quit.setTuDepartment(rs.getString("tuDepartment"));
                        quit.setUniversity(rs.getString("university"));
                        quit.setFaculty(rs.getString("faculty"));
                        quit.setDepartment(rs.getString("department"));
                        quit.setOutstandingDebt(rs.getString("outstandingDebt"));
                        if(rs.getBoolean("reqGrade")){
                            quit.setReqGrade("yes");
                        } else {
                            quit.setReqGrade("no");
                        }
                        quit.setFormStatus(rs.getString("formStatus"));
                        quit.setCreated_at(rs.getString("created_at"));
                        return quit;
                    }
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Other searchOtherForm(String formId){
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM OtherForm WHERE id = ?",
                    new Object[]{formId},
                    (rs, rowNum) -> {
                        Other other = new Other();
                        other.setId(rs.getLong("id"));
                        other.setStudentDataId(rs.getLong("studentDataId"));
                        other.setInfo(rs.getString("info"));
                        other.setFormStatus(rs.getString("formStatus"));
                        other.setCreated_at(rs.getString("created_at"));
                        return other;
                    }
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public AddDrop searchAddDropForm(String formId){
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM AddDropSubjectList WHERE id = ?",
                    new Object[]{formId},
                    (rs, rowNum) -> {
                        AddDrop addDrop = new AddDrop();
                        addDrop.setId(rs.getLong("id"));
                        addDrop.setStudentDataId(rs.getLong("studentDataId"));
                        addDrop.setSelection(rs.getString("selection"));
                        addDrop.setSubjectCode(rs.getString("subjectCode"));
                        addDrop.setSubjectName(rs.getString("subjectName"));
                        addDrop.setSubjectSection(rs.getString("subjectSection"));
                        addDrop.setSubjectDate(rs.getString("subjectDate"));
                        addDrop.setSubjectCredit(rs.getInt("subjectCredit"));
                        addDrop.setSubjectTeacher(rs.getString("subjectTeacher"));
                        if (rs.getBoolean("subjectTeacherCheck")){
                            addDrop.setSubjectTeacherCheck("true");
                        } else {
                            addDrop.setSubjectTeacherCheck("false");
                        }
                        addDrop.setFormStatus(rs.getString("formStatus"));
                        addDrop.setCreated_at(rs.getString("created_at"));
                        return addDrop;
                    }
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateLateForm(String formId, Late late) {
        String sql = "UPDATE LateForm SET semester = ?, year = ?, payDate = ?, reason = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                late.getSemester(),
                late.getYear(),
                late.getPayDate(),
                late.getReason(),
                formId
        );
    }

    @Override
    public void updateQuitForm(String formId, Quit quit) {
        boolean reqGrade;
        reqGrade = quit.getReqGrade().equals("yes");
        if (quit.getQuitReason().equals("tu")){
            String sql = "UPDATE QuitForm SET semester = ?, year = ?, quitReason = ?, tuFaculty = ?, tuDepartment = ?, university = NULL, faculty = NULL, department = NULL, outstandingDebt = ?, reqGrade = ? WHERE id = ?";
            jdbcTemplate.update(sql, quit.getSemester(), quit.getYear(), quit.getQuitReason(), quit.getTuFaculty(), quit.getTuDepartment(), quit.getOutstandingDebt(), reqGrade, formId);
        } else {
            String sql = "UPDATE QuitForm SET semester = ?, year = ?, quitReason = ?, tuFaculty = NULL, tuDepartment = NULL, university = ?, faculty = ?, department = ?, outstandingDebt = ?, reqGrade = ? WHERE id = ?";
            jdbcTemplate.update(sql, quit.getSemester(), quit.getYear(), quit.getQuitReason(), quit.getUniversity(), quit.getFaculty(), quit.getDepartment(), quit.getOutstandingDebt(), reqGrade, formId);
        }
    }

    @Override
    public void updateOtherForm(String formId, Other other) {
        String sql = "UPDATE OtherForm SET info = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                other.getInfo(),
                formId
        );
    }

    @Override
    public void updateAddDropForm(String formId, AddDrop addDrop) {
        boolean teacherCheck;
        teacherCheck = addDrop.getSubjectTeacherCheck().equals("true");
        String sql = "UPDATE AddDropSubjectList SET selection = ?, subjectCode = ?, subjectName = ?, subjectSection = ?, subjectDate = ?, subjectCredit = ?, subjectTeacher = ?, subjectTeacherCheck = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                addDrop.getSelection(),
                addDrop.getSubjectCode(),
                addDrop.getSubjectName(),
                addDrop.getSubjectSection(),
                addDrop.getSubjectDate(),
                addDrop.getSubjectCredit(),
                addDrop.getSubjectTeacher(),
                teacherCheck,
                formId
        );
    }

}
