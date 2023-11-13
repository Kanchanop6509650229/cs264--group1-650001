package th.ac.tu.cs.Website.repository;

import th.ac.tu.cs.Website.model.AddDrop;
import th.ac.tu.cs.Website.model.Quit;

public interface FormRepository {
    void saveAddDrop(AddDrop addDropSubject);
    void saveQuitForm(Quit quit);
}
