package th.ac.tu.cs.Website.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import th.ac.tu.cs.Website.model.AddDrop;
import th.ac.tu.cs.Website.model.Quit;

import java.util.List;

@Repository
public class JdbcFormRepository implements FormRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveAddDrop(AddDrop addDropSubject){
        List<Long> studentDataId = jdbcTemplate.queryForList("SELECT id FROM StudentData WHERE studentId = ?", Long.class, addDropSubject.getUserId());
        
        boolean subjectTeacherChecks;
        subjectTeacherChecks = addDropSubject.getSubjectTeacherCheck().equals("true");

        String sql = "INSERT INTO AddDropSubjectList (studentDataId, selection, subjectCode, subjectName, subjectSection, subjectDate, subjectCredit, subjectTeacher, subjectTeacherCheck, formStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, studentDataId.get(0), addDropSubject.getSelection(), addDropSubject.getSubjectCode(), addDropSubject.getSubjectName(), addDropSubject.getSubjectSection(), addDropSubject.getSubjectDate(), addDropSubject.getSubjectCredit(), addDropSubject.getSubjectTeacher(), subjectTeacherChecks, addDropSubject.getFormStatus());
        
    }

    @Override
    public void saveQuitForm(Quit quit){
        List<Long> studentDataId = jdbcTemplate.queryForList("SELECT id FROM StudentData WHERE studentId = ?", Long.class, quit.getStudentId());

        boolean reqGrade;
        reqGrade = quit.getReqGrade().equals("yes");
        if (quit.getQuitReason().equals("tu")){
            String sql = "INSERT INTO QuitForm (studentDataId, semester, year, quitReason, tuFaculty, tuDepartment, outstandingDebt, reqGrade, formStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, studentDataId.get(0), quit.getSemester(), quit.getYear(), quit.getQuitReason(), quit.getTuFaculty(), quit.getTuDepartment(), quit.getOutstandingDebt(), reqGrade, quit.getFormStatus());
        } else {
            String sql = "INSERT INTO QuitForm (studentDataId, semester, year, quitReason, university, faculty, department, outstandingDebt, reqGrade, formStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, studentDataId.get(0), quit.getSemester(), quit.getYear(), quit.getQuitReason(), quit.getUniversity(), quit.getFaculty(), quit.getDepartment(), quit.getOutstandingDebt(), reqGrade, quit.getFormStatus());
        }

    }
}
