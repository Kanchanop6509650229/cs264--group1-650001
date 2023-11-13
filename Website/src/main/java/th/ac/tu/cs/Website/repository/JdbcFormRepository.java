package th.ac.tu.cs.Website.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import th.ac.tu.cs.Website.model.AddDrop;

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
}
