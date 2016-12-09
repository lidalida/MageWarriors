package controller;

import java.beans.PropertyChangeEvent;

import model.Model;
import view.View;

public interface Controller {
	void addModel(Model model);
	void addView(View view);
	
	void propertyChange(PropertyChangeEvent evt);
}
