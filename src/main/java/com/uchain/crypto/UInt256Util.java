package com.uchain.crypto;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.bouncycastle.util.encoders.Hex;

public class UInt256Util  {

	public static UInt256 fromBytes(byte []bytes) {
        if (bytes.length == UIntUtil.UInt256_Size){
            return new UInt256(bytes);
        }
        throw new IllegalArgumentException();
    }

    public static UInt256 parse(String str) {
        if (str == null || str.isEmpty()){
            return null;
        } else {
            String s;
            if (str.startsWith("0x")){
                s = str.substring(2);
            } else {
                s = str;
            }
            if (s.length() != 64){
                return null;
            } else {
                return fromBytes(Hex.decode(s));
            }
        }
    }

    public static UInt256 deserialize(DataInputStream is) {
        byte []data = new byte[UIntUtil.UInt256_Size];
        Arrays.fill(data, (byte)0);
        try {
            is.read(data, 0, UIntUtil.UInt256_Size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fromBytes(data);
    }
    
    public static UInt256 Zero() {
        byte []data = new byte[UIntUtil.UInt256_Size];
        Arrays.fill(data, (byte)0);
        return fromBytes(data);
    }
}
