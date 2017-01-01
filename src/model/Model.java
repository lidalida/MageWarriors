package model;

import java.awt.Image;
import java.beans.PropertyChangeListener;

public interface Model {
	Image getImage();
	double getRotation();
	int getX();
	int getY();
}
