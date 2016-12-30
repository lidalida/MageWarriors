package controller;

import java.beans.PropertyChangeEvent;

import model.Drawable;
import model.Model;
import view.View;

public interface Controller {
	void addModel(Drawable model);
	void addView(View view);
	
	void propertyChange(PropertyChangeEvent evt);
}
