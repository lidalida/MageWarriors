package engine;

import java.beans.PropertyChangeEvent;

public interface View {
	void paintView();
	
	void setController(Controller controller);
	
	void modelPropertyChange(final PropertyChangeEvent evt);
	
	void addModel(Model model);
}
