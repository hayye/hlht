package cn.hayye.hlht.utils;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5工具.
 */
public class Md5Utils {

    /**
     * 字符串的md5.
     * @param plainText 输入字符串.
     * @return md5(utf8编码).
     */
    public static String md5(String plainText) {
        return md5(plainText, StandardCharsets.UTF_8);
    }

    /**
     * 字符串的md5.
     * @param plainText 输入字符串.
     * @param encode 字符集.
     * @return md5.
     * @see #md5(String, Charset)
     */
    @Deprecated
    public static String md5(String plainText, String encode) {
        try {
            return md5(plainText.getBytes(encode));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 字符串的md5.
     * @param plainText 输入字符串.
     * @param charset 字符集.
     * @return md5.
     */
    public static String md5(String plainText, Charset charset) {
        return md5(plainText.getBytes(charset));
    }

    /**
     * 字符串的md5.
     * @param plainText 输入字符串.
     * @return md5(utf8编码).
     */
    public static String md5_16(String plainText) {
        return md5_16(plainText, StandardCharsets.UTF_8);
    }

    /**
     * 字符串的md5.
     * @param plainText 输入字符串.
     * @param encode 字符集.
     * @return md5.
     * @see #md5(String, Charset)
     */
    @Deprecated
    public static String md5_16(String plainText, String encode) {
        try {
            return md5(plainText.getBytes(encode)).substring(8,24);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 字符串的16位md5.
     * @param plainText 输入字符串.
     * @param charset 字符集.
     * @return md5.
     */
    public static String md5_16(String plainText, Charset charset){
        return md5(plainText, charset).substring(8,24);
    }

    /**
     * 数据的md5.
     * @param data 输入字符串.
     * @return md5.
     */
    public static byte[] md5Bytes(byte[] data) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(data);
            //获得加密后的数据
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法");
        }
    }

    /**
     * 数据的md5.
     * @param data 输入字符串.
     * @return md5.
     */
    public static String md5(byte[] data) {
        return HexUtils.bytes2HexString(md5Bytes(data));
    }


}