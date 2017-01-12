package engine;

import java.awt.Image;

public interface Model {
	Image getImage();
	double getRotation();
	void setRotation(double rot);
	int getX();
	void setX(int x);
	int getY();
	void setY(int y);
	void setID(int i);
	int getID();
}
