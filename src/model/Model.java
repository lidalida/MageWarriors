package model;

import java.awt.Image;
import java.beans.PropertyChangeListener;

public interface Model {
	void addPropertyChangeListener(PropertyChangeListener l);
	void firePropertyChange(String propertyName,Object oldValue, Object newValue);
	Image getImage();
	double getRotation();
	int getX();
	int getY();
}
