package th.ac.tu.cs.Website.repository;

import th.ac.tu.cs.Website.model.*;

public interface FormRepository {
    void saveAddDrop(AddDrop addDropSubject);
    void saveQuitForm(Quit quit);
    void saveLateForm(Late late);
    void saveOtherForm(Other other);
    AllData searchForm(String id);
    Late searchLateForm(String formId);
    Quit searchQuitForm(String formId);
    Other searchOtherForm(String formId);
    AddDrop searchAddDropForm(String formId);
    void updateLateForm(String formId, Late late);
    void updateQuitForm(String formId, Quit quit);
    void updateOtherForm(String formId, Other other);
    void updateAddDropForm(String formId, AddDrop addDrop);
}
