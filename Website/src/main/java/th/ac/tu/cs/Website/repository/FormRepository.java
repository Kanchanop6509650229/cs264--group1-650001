package th.ac.tu.cs.Website.repository;

import th.ac.tu.cs.Website.model.*;

public interface FormRepository {
    void saveAddDrop(AddDrop addDropSubject);
    void saveQuitForm(Quit quit);
    void saveLateForm(Late late);
    void saveOtherForm(Other other);
    AllData searchForm(String id);
}
