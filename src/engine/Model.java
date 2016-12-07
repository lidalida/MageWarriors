package engine;

import java.beans.PropertyChangeListener;

public interface Model {
	void addPropertyChangeListener(PropertyChangeListener l);
	void firePropertyChange(String propertyName,Object oldValue, Object newValue);
}
