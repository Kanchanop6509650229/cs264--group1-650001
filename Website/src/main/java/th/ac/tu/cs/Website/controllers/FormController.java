package th.ac.tu.cs.Website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.ac.tu.cs.Website.model.*;
import th.ac.tu.cs.Website.repository.FormRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/form")
public class FormController {
    @Autowired
    private FormRepository formRepository;

    @PostMapping("/add-dropForm")
    public ResponseEntity<String> submitAddDropForm(@RequestBody List<AddDrop> addDropList) {
        try {

            for (AddDrop addDrop : addDropList) {
                // Save the AddDrop object
                formRepository.saveAddDrop(addDrop);
            }

            return ResponseEntity.ok("Form submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting form");
        }
    }

    @PostMapping("/quitForm")
    public ResponseEntity<String> submitQuitForm(@RequestBody Quit quit) {
        try {
            
            formRepository.saveQuitForm(quit);

            return ResponseEntity.ok("Form submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting form");
        }
    }

    @PostMapping("/lateForm")
    public ResponseEntity<String> submitQuitForm(@RequestBody Late late) {
        try {

            formRepository.saveLateForm(late);

            return ResponseEntity.ok("Form submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting form");
        }
    }

    @PostMapping("/otherForm")
    public ResponseEntity<String> submitOtherForm(@RequestBody Other other) {
        try {

            formRepository.saveOtherForm(other);

            return ResponseEntity.ok("Form submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting form");
        }
    }

    @GetMapping("/getAllFormById")
    public ResponseEntity<List<Tracking>> getFormData(@RequestParam String studentId) {
        try {
            AllData allData = formRepository.searchForm(studentId);
            if (allData != null) {
                List<Tracking> trackingList = new ArrayList<Tracking>();
                for(AddDrop addDrop : allData.getAddDropList()) {
                    Tracking tracking = new Tracking();
                    tracking.setFormId(addDrop.getId());
                    if (addDrop.getSelection().equals("Add")) {
                        tracking.setFormName("เพิ่มรายวิชา");
                    } else {
                        tracking.setFormName("ถอนรายวิชา");
                    }
                    tracking.setStudentDataId(addDrop.getStudentDataId());
                    tracking.setStatus(addDrop.getFormStatus());
                    tracking.setStudentName(allData.getStudentData().getFirstName() + " " + allData.getStudentData().getLastName());
                    tracking.setDateTime(addDrop.getCreated_at() + " น.");
                    trackingList.add(tracking);
                }
                for (Late late : allData.getLateList()) {
                    Tracking tracking = new Tracking();
                    tracking.setFormId(late.getId());
                    tracking.setFormName("ขอผ่อนผัน");
                    tracking.setStudentDataId(late.getStudentDataId());
                    tracking.setStatus(late.getFormStatus());
                    tracking.setDateTime(late.getCreated_at() + " น.");
                    tracking.setStudentName(allData.getStudentData().getFirstName() + " " + allData.getStudentData().getLastName());
                    trackingList.add(tracking);
                }
                for (Other other : allData.getOtherList()) {
                    Tracking tracking = new Tracking();
                    tracking.setFormId(other.getId());
                    tracking.setFormName("อื่นๆ");
                    tracking.setStudentDataId(other.getStudentDataId());
                    tracking.setStatus(other.getFormStatus());
                    tracking.setDateTime(other.getCreated_at() + " น.");
                    tracking.setStudentName(allData.getStudentData().getFirstName() + " " + allData.getStudentData().getLastName());
                    trackingList.add(tracking);
                }
                for (Quit quit : allData.getQuitList()) {
                    Tracking tracking = new Tracking();
                    tracking.setFormId(quit.getId());
                    tracking.setFormName("ขอลาออก");
                    tracking.setStudentDataId(quit.getStudentDataId());
                    tracking.setStatus(quit.getFormStatus());
                    tracking.setDateTime(quit.getCreated_at() + " น.");
                    tracking.setStudentName(allData.getStudentData().getFirstName() + " " + allData.getStudentData().getLastName());
                    trackingList.add(tracking);
                }

                // Sort trackingList based on dateTime
                Collections.sort(trackingList, Comparator.comparing(Tracking::getDateTime));
                return new ResponseEntity<>(trackingList, HttpStatus.OK);
            } else {
                // Handle the case when no student data is found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
