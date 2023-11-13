package th.ac.tu.cs.Website.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.ac.tu.cs.Website.model.AddDrop;
import th.ac.tu.cs.Website.model.Quit;
import th.ac.tu.cs.Website.repository.FormRepository;

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
}
