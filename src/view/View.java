package view;

import java.beans.PropertyChangeEvent;

import controller.Controller;
import model.Model;

public interface View {
	void updateView();
	
	void setController(Controller controller);
	
	void modelPropertyChange(final PropertyChangeEvent evt);
	
	void addModel(Model model);
}
