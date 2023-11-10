package th.ac.tu.cs.Website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.ac.tu.cs.Website.model.StudentData;
import th.ac.tu.cs.Website.repository.StudentDataRepository;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentDataRepository studentDataRepository;

    @PostMapping("/submitStudentData")
    public String saveStudentData(@RequestBody StudentData studentData) {
        try {
            studentDataRepository.saveStudent(studentData);
            return "success"; // Return a success status
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Return an error status
        }
    }

}
