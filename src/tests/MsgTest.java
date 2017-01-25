package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

import net.EventMsg;
import net.InputMsg;
import net.PositionMsg;

public class MsgTest {

	@Test
	public void testInputMsg() {
		InputMsg msg = new InputMsg(1,true,101,234);
		Field[] fields = InputMsg.class.getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			fields[i].setAccessible(true);
		}
		try {
			assertEquals(1,fields[1].get(msg));
			assertEquals(true,fields[2].get(msg));
			assertEquals(101,fields[3].get(msg));
			assertEquals(234,fields[4].get(msg));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEventMsg() {
		EventMsg msg = new EventMsg(1,5,10);
		Field[] fields = EventMsg.class.getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			fields[i].setAccessible(true);
		}
		try {
			assertEquals(1,fields[1].get(msg));
			assertEquals(5,fields[2].get(msg));
			assertEquals(10,fields[3].get(msg));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPositionMsg() {
		PositionMsg msg = new PositionMsg(1,235,232,2.3);
		Field[] fields = PositionMsg.class.getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			fields[i].setAccessible(true);
		}
		try {
			assertEquals(1,fields[1].get(msg));
			assertEquals(235,fields[2].get(msg));
			assertEquals(232,fields[3].get(msg));
			assertEquals(2.3,fields[4].get(msg));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
