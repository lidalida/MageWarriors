package engine;

import java.beans.PropertyChangeEvent;

public interface Controller {
	void addModel(Model model);
	void addView(View view);
	
	void propertyChange(PropertyChangeEvent evt);
}
