package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import net.InputMsg;
import net.Serializer;

public class SerializationTest {
	
	InputMsg msg, msg2;
	
	@Before
	public void setUp(){
		msg = new InputMsg(1,true,10,10);
		byte [] data = Serializer.serializeObject(msg);
		msg2 = (InputMsg) Serializer.deserializeObject(data);
	}
	
	@Test
	public void test() {
		try {
			Field[] field = InputMsg.class.getDeclaredFields();
			for(int i=0;i<field.length;i++){
				field[i].setAccessible(true);
				assertEquals(field[i].get(msg),field[i].get(msg2));
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}