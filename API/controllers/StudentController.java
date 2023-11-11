package th.ac.tu.cs.Website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import th.ac.tu.cs.Website.model.StudentData;
import th.ac.tu.cs.Website.repository.StudentDataRepository;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentDataRepository studentDataRepository;

    @PostMapping("/submitStudentData")
    public ResponseEntity<String> saveStudent(@RequestBody StudentData studentData) {
        try {
            StudentData existingStudent = studentDataRepository.findByStudentId(studentData.getStudentId());

            if (existingStudent != null) {
                studentDataRepository.updateStudent(studentData); // Update the existing record
            } else {
                // If no record exists, save a new record
                studentDataRepository.saveStudent(studentData);
            }
            return new ResponseEntity<>("Data saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getStudentData")
    public ResponseEntity<StudentData> getStudentData(@RequestParam String id) {
        try {
            StudentData studentData = studentDataRepository.findByStudentId(id);

            if (studentData != null) {
                return new ResponseEntity<>(studentData, HttpStatus.OK);
            } else {
                // Handle the case when no student data is found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
