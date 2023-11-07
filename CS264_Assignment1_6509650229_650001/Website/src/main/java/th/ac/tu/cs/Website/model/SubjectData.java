/*
 * กาญจนพ บัวรอด
 * 6509650229
 * 25/10/2566
 * Interface เพื่อให้ AddSubject และ DropSubject implement เนื่องจากเป็นประเภทและข้อมูลเดียวกัน
 */

package th.ac.tu.cs.Website.model;

public interface SubjectData {

        public String getSubjectCode();

        public void setSubjectCode(String subjectCode);

        public String getSubjectName();

        public void setSubjectName(String subjectName);

        public String getSubjectSection();

        public void setSubjectSection(String subjectSection);

        public String getSubjectDate();

        public void setSubjectDate(String subjectDate);

        public Integer getSubjectCredit();

        public void setSubjectCredit(Integer subjectCredit);

        public String getSubjectTeacher();

        public void setSubjectTeacher(String subjectTeacher);

        public String getSubjectTeacherCheck();

        public void setSubjectTeacherCheck(String subjectTeacherCheck) ;
        public Long getStudentFormId();
        public void setStudentFormId(Long studentFormId);

    }

