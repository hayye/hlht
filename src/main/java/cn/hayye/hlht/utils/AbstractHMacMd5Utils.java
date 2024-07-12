package cn.hayye.hlht.utils;

import java.nio.charset.Charset;

public class AbstractHMacMd5Utils {

    public static byte[] assembleData(String operatorID, String data, String timestamp, String seq, Charset charset){
        return String.format("%s%s%s%s", operatorID,data, timestamp, seq).getBytes(charset);
    }

    public static byte[] assembleData(String ret,String msg, String data, Charset charset){
        return String.format("%s%s%s", ret,msg,data).getBytes(charset);
    }

}