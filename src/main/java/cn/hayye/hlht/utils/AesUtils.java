package cn.hayye.hlht.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AesUtils {

    /**
     * @Description AES算法加密明文
     * @param data 明文
     * @param key 密钥，长度16
     * @param iv 偏移量，长度16
     * @return 密文
     */
    public static String encryptAES(String data,String key,String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());  // CBC模式，需要一个向量iv，可增加加密算法的强度

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return encode(encrypted).trim(); // BASE64做转码。

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description AES算法解密密文
     * @param data 密文
     * @param key 密钥，长度16
     * @param iv 偏移量，长度16
     * @return 明文
     */
    public static String decryptAES(String data,String key,String iv){
        try
        {
            byte[] encrypted1 = decode(data);//先用base64解密

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 编码
     * @param byteArray
     * @return
     */
    private static String encode(byte[] byteArray) {
        return new String(new Base64().encode(byteArray));
    }

    /**
     * 解码
     * @param base64EncodedString
     * @return
     */
    private static byte[] decode(String base64EncodedString) {
        return new Base64().decode(base64EncodedString);
    }

}
