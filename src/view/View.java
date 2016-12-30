package view;

import java.beans.PropertyChangeEvent;

import controller.Controller;

public interface View {
	void setController(Controller controller);
	
	void modelPropertyChange(final PropertyChangeEvent evt);
}
