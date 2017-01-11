package net;

import java.io.Serializable;

public class EventMsg implements Serializable{

	private static final long serialVersionUID = 7769300176771980978L;
	int id;
	int attr; //który atrybut zmieniæ, jakaœ konkretna wartoœæ tego parametru bêdzie oznacza³a usuniêcie modelu a nie zmianê
	int val; //na jak¹ wartoœæ
	

}
