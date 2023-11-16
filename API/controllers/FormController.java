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
    public ResponseEntity<String> submitLateForm(@RequestBody Late late) {
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
                for (AddDrop addDrop : allData.getAddDropList()) {
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

    @GetMapping("/getLateFormByFormId")
    public ResponseEntity<Late> getLateData(@RequestParam String formId) {
        try {
            Late lateForm = formRepository.searchLateForm(formId);
            if (lateForm != null) {
                return new ResponseEntity<>(lateForm, HttpStatus.OK);
            } else {
                // Handle the case when no student data is found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getQuitFormByFormId")
    public ResponseEntity<Quit> getQuitData(@RequestParam String formId) {
        try {
            Quit quitForm = formRepository.searchQuitForm(formId);
            if (quitForm != null) {
                return new ResponseEntity<>(quitForm, HttpStatus.OK);
            } else {
                // Handle the case when no student data is found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getOtherFormByFormId")
    public ResponseEntity<Other> getOtherData(@RequestParam String formId) {
        try {
            Other otherForm = formRepository.searchOtherForm(formId);
            if (otherForm != null) {
                return new ResponseEntity<>(otherForm, HttpStatus.OK);
            } else {
                // Handle the case when no student data is found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAddDropFormByFormId")
    public ResponseEntity<AddDrop> getAddDropData(@RequestParam String formId) {
        try {
            AddDrop addDropForm = formRepository.searchAddDropForm(formId);
            if (addDropForm != null) {
                return new ResponseEntity<>(addDropForm, HttpStatus.OK);
            } else {
                // Handle the case when no student data is found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateLateForm")
    public ResponseEntity<String> updateLateForm(@RequestBody Late late, @RequestParam String formId) {
        try {

            formRepository.updateLateForm(formId, late);

            return ResponseEntity.ok("Form submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting form");
        }
    }

    @PostMapping("/updateQuitForm")
    public ResponseEntity<String> updateQuitForm(@RequestBody Quit quit, @RequestParam String formId) {
        try {

            formRepository.updateQuitForm(formId, quit);

            return ResponseEntity.ok("Form submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting form");
        }
    }

    @PostMapping("/updateOtherForm")
    public ResponseEntity<String> updateOtherForm(@RequestBody Other other, @RequestParam String formId) {
        try {

            formRepository.updateOtherForm(formId, other);

            return ResponseEntity.ok("Form submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting form");
        }
    }

    @PostMapping("/updateAddDropForm")
    public ResponseEntity<String> updateAddDropForm(@RequestBody AddDrop addDrop, @RequestParam String formId) {
        try {

            formRepository.updateAddDropForm(formId, addDrop);

            return ResponseEntity.ok("Form submitted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting form");
        }
    }

    @GetMapping("/getTrackingLateForm")
    public ResponseEntity<Tracking> getTrackingLate(@RequestParam String formId) {
        try {
            Late lateForm = formRepository.searchLateForm(formId);
            if (lateForm != null) {
                Tracking tracking = new Tracking();
                tracking.setFormId(lateForm.getId());
                tracking.setFormName("ขอผ่อนผัน");
                tracking.setStudentDataId(lateForm.getStudentDataId());
                tracking.setStatus(lateForm.getFormStatus());
                tracking.setDateTime(lateForm.getCreated_at() + " น.");
                return new ResponseEntity<>(tracking, HttpStatus.OK);
            } else {
                // Handle the case when no student data is found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTrackingQuitForm")
    public ResponseEntity<Tracking> getTrackingQuit(@RequestParam String formId) {
        try {
        Quit quitForm = formRepository.searchQuitForm(formId);
            if (quitForm != null) {
                Tracking tracking = new Tracking();
                tracking.setFormId(quitForm.getId());
                tracking.setFormName("ขอลาออก");
                tracking.setStudentDataId(quitForm.getStudentDataId());
                tracking.setStatus(quitForm.getFormStatus());
                tracking.setDateTime(quitForm.getCreated_at() + " น.");
                return new ResponseEntity<>(tracking, HttpStatus.OK);
            } else {
                // Handle the case when no student data is found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTrackingOtherForm")
    public ResponseEntity<Tracking> getTrackingOther(@RequestParam String formId) {
        try {
            Other otherForm = formRepository.searchOtherForm(formId);
            if (otherForm != null) {
                Tracking tracking = new Tracking();
                tracking.setFormId(otherForm.getId());
                tracking.setFormName("อื่นๆ");
                tracking.setStudentDataId(otherForm.getStudentDataId());
                tracking.setStatus(otherForm.getFormStatus());
                tracking.setDateTime(otherForm.getCreated_at() + " น.");
                return new ResponseEntity<>(tracking, HttpStatus.OK);
            } else {
                // Handle the case when no student data is found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTrackingAddDropForm")
    public ResponseEntity<Tracking> getTrackingAddDrop(@RequestParam String formId) {
        try {
            AddDrop addDropForm = formRepository.searchAddDropForm(formId);
            if (addDropForm != null) {
                Tracking tracking = new Tracking();
                tracking.setFormId(addDropForm.getId());
                if (addDropForm.getSelection().equals("Add")){
                    tracking.setFormName("เพิ่มรายวิชา");
                } else {
                    tracking.setFormName("ถอนรายวิชา");
                }
                tracking.setStudentDataId(addDropForm.getStudentDataId());
                tracking.setStatus(addDropForm.getFormStatus());
                tracking.setDateTime(addDropForm.getCreated_at() + " น.");
                return new ResponseEntity<>(tracking, HttpStatus.OK);
            } else {
                // Handle the case when no student data is found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
