package com.uchain.network.message;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.uchain.common.Serializable;
import com.uchain.common.Serializabler;
import com.uchain.crypto.UInt256;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Inventory implements Serializable {
	private InventoryType invType;
	private List<UInt256> hashs;

	@Override
	public void serialize(DataOutputStream os) {
		try {
			os.writeByte(InventoryType.getInventoryTypeByType(invType));
			Serializabler.writeSeq(os, hashs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static List<UInt256> readSeq(DataInputStream is) {
		int size;
		List<UInt256> uInt256s = null;
		try {
			size = is.readInt();
			uInt256s = new ArrayList<UInt256>(size);
			for(int i = 0; i < size; i++){
				uInt256s.add(UInt256.deserialize(is));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uInt256s;
	}
	
	private static Inventory deserialize(DataInputStream is) {
		InventoryType invTypeTemp;
		try {
			invTypeTemp = InventoryType.getInventoryTypeByValue(is.readByte());
			List<UInt256> hashTemps = readSeq(is);
			new Inventory(invTypeTemp, hashTemps);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Inventory fromBytes(byte[] data) {
		ByteArrayInputStream bs = new ByteArrayInputStream(data);
		DataInputStream is = new DataInputStream(bs);
		return deserialize(is);
	}
}
