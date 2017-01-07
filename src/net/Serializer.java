package net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface Serializer {
	public static Serializable deserializeObject(byte[] array){
		ByteArrayInputStream bis = new ByteArrayInputStream(array);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Serializable packet = (Serializable) in.readObject();
			return packet;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static byte[] serializeObject(Serializable obj){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
        	ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(obj);
			out.flush();
			return bos.toByteArray();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return null;
	}
}
