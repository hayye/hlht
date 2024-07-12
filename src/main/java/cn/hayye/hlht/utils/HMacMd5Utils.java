package cn.hayye.hlht.utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static cn.hayye.hlht.utils.AbstractHMacMd5Utils.assembleData;


public class HMacMd5Utils {

    private final static byte keyLen = 64;
    private final static byte ipad = 0x36;
    private final static byte opad = 0x5c;
    /**
     * 处理密钥.
     * <p>密钥补为64字节;长于64字节的用md5散列结果.</p>
     * @param key 签名密钥.
     * @return 结果.
     */
    private static byte[] generateKey(String key) {

        ByteBuffer buf = ByteBuffer.allocate(keyLen);
        if(key.length()>keyLen){
            buf.put(Md5Utils.md5Bytes(key.getBytes(StandardCharsets.UTF_8))) ;
        }else{
            buf.put(key.getBytes());
        }

        return buf.array();

    }

    /**
     * 生成istr数据.
     * @param key 签名密钥.
     *            <p>64字节长.</p>
     * @param out 输出数组.
     * @return 结果.
     */
    public static byte[] generateIstr(byte[] key, byte[] out){
        for(int i=0;i<keyLen;i++){
            out[i]=(byte)(key[i]^ipad);
        }
        return out;
    }

    /**
     * 生成ostr数据.
     * @param key 签名密钥.
     *            <p>64字节长.</p>
     * @param out 输出数组.
     * @return 结果.
     */
    private static byte[] generateOstr(byte[] key, byte[] out) {
        for(int i=0;i<keyLen;i++){
            out[i]=(byte)(key[i]^opad);
        }
        return out;
    }

    /**
     * 数据签名.
     * @param key 签名密钥.
     * @param data 数据.
     * @return 签名.
     */
    public static byte[] sign(String key, byte[] data){

        byte[] k = generateKey(key);
        byte[] str = new byte[keyLen];

        byte[] data1 = new byte[keyLen+data.length];
        System.arraycopy(generateIstr(k, str), 0, data1, 0, keyLen);
        System.arraycopy(data, 0, data1, keyLen, data.length);
        byte[] md5One = Md5Utils.md5Bytes(data1);

        byte[] data2 = new byte[keyLen+md5One.length];
        System.arraycopy(generateOstr(k, str), 0, data2, 0, keyLen);
        System.arraycopy(md5One, 0, data2, keyLen, md5One.length);

        return Md5Utils.md5Bytes(data2);
    }


    /**
     * 生成签名字符串.
     * @param key 签名密钥.
     * @param operatorID 运营商Id.
     * @param data 数据.
     * @param timestamp 时间戳.
     * @param seq 序列.
     * @return 签名.
     */
    public static String signHex(String key, String operatorID, String data, String timestamp, String seq){
        return HexUtils.bytes2HexString(sign(key, operatorID, data, timestamp, seq));
    }

    /**
     * 生成签名.
     * @param key 签名密钥.
     * @param operatorID 运营商Id.
     * @param data 数据.
     * @param timestamp 时间戳.
     * @param seq 序列.
     * @return 签名.
     */
    public static byte[] sign(String key, String operatorID, String data, String timestamp, String seq){
        return sign(key, assembleData(operatorID,data, timestamp, seq, StandardCharsets.UTF_8));
    }


    public static String signHex(String key, String ret,  String msg, String data){
        return HexUtils.bytes2HexString(sign(key, ret,msg, data));
    }
    public static byte[] sign(String key, String ret,String msg, String data){
        return sign(key, assembleData(ret,msg,data, StandardCharsets.UTF_8));
    }
}
