package net;

import java.io.Serializable;

public class EventMsg implements Serializable{

	private static final long serialVersionUID = 7769300176771980978L;
	int id;
	int attr; //kt�ry atrybut zmieni�, jaka� konkretna warto�� tego parametru b�dzie oznacza�a usuni�cie modelu a nie zmian�
	int val; //na jak� warto��
	

}
