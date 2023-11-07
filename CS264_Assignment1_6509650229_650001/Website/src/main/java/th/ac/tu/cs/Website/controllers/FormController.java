/*
* กาญจนพ บัวรอด
* 6509650229
* 25/10/2566
* Controller สำหรับเชื่อมต่อระหว่างหน้า html กับ repository
*/

package th.ac.tu.cs.Website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import th.ac.tu.cs.Website.model.StudentData;
import th.ac.tu.cs.Website.repository.StudentDataRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FormController {

    @Autowired
    StudentDataRepository studentDataRepository;
    @PostMapping("/submitForm")
    public ResponseEntity<String> createStudentData(@RequestBody MultiValueMap<String, String> formData) {
        StudentData studentData = StudentData.fromFormData(formData);
        try {
            studentDataRepository.save(studentData);
            return new ResponseEntity<>("StudentForm was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deleteAll")
    public ResponseEntity<String> deleteAllTutorials() {
        try {
            int numRows = studentDataRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " StudentForm(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete StudentForms.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByStudentId")
    public ResponseEntity<List<StudentData>> findByStudentId(@RequestParam(value = "searchId") String studentId) {
        try {
            List<StudentData> studentData = studentDataRepository.findByStudentId(studentId);

            if (studentData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(studentData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<StudentData>> findAll() {
        try {
            List<StudentData> studentData = studentDataRepository.findAll();

            if (studentData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(studentData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
