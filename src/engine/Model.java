package engine;

import java.awt.Image;

public interface Model {
	Image getImage();
	double getRotation();
	int getX();
	int getY();
	void setID(int i);
	int getID();
}
