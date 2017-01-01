package view;

import java.beans.PropertyChangeEvent;

public interface View {
	
	void modelPropertyChange(final PropertyChangeEvent evt);
}
