package cn.hayye.hlht.utils;

@SuppressWarnings("unused")
public class HexUtils {

    /**
     * bytes2HexString.
     * 字节数组转16进制字符串.
     * @param bs 字节数组.
     * @return 16进制字符串.
     */
    public static String bytes2HexString(byte[] bs) {
        StringBuilder result = new StringBuilder();
        for (byte b1 : bs) {
            result.append(String.format("%02X", b1));
        }
        return result.toString();
    }

    /**
     * 字节数组转大写16进制字符串.
     * @param bs 字节数组.
     * @return 16进制字符串.
     */
    public static String bytes2UpperCaseHexString(byte[] bs) {
        return bytes2HexString(bs).toUpperCase();
    }

    /**
     * 字节数组转小写16进制字符串.
     * @param bs 字节数组.
     * @return 16进制字符串.
     */
    public static String bytes2LowerCaseHexString(byte[] bs) {
        return bytes2HexString(bs).toLowerCase();
    }


    /**
     * bytes2HexString.
     * 字节数组转16进制字符串.
     * @param bs 字节数组.
     * @return 16进制字符串.
     */
    public static String bytes2HexString(byte[] bs, int offset, int length) {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<length; i++) {
            byte b1 = bs[offset+i];
            result.append(String.format("%02X", b1));
        }
        return result.toString();
    }

    /**
     * hexString2Bytes.
     * 16进制字符串转字节数组.
     * @param src 16进制字符串.
     * @return 字节数组.
     *
     */
    public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }


    /**
     * string2HexUTF8.
     * 字符UTF8串转16进制字符串.
     * @param strPart 字符串.
     * @return 16进制字符串.
     *
     */
    public static String string2HexUTF8(String strPart) {

        return string2HexString(strPart,"UTF-8");
    }

    /**
     * string2HexUTF8.
     * 字符UTF-16LE串转16进制字符串,此UTF-16LE等同于C#中的Unicode
     * @param strPart 字符串.
     * @return 16进制字符串.
     *
     */
    public static String string2HexUTF16LE(String strPart) {

        return string2HexString(strPart,"UTF-16LE");
    }

    /**
     * string2HexUnicode.
     * 字符Unicode串转16进制字符串.
     * @param strPart 字符串.
     * @return 16进制字符串.
     *
     */
    public static String string2HexUnicode(String strPart) {

        return string2HexString(strPart,"Unicode");
    }
    /**
     * string2HexGBK.
     * 字符GBK串转16进制字符串.
     * @param strPart 字符串.
     * @return 16进制字符串.
     *
     */
    public static String string2HexGBK(String strPart) {

        return string2HexString(strPart,"GBK");
    }

    /**
     * string2HexString.
     * 字符串转16进制字符串.
     * @param strPart 字符串.
     * @param tochartype hex目标编码.
     * @return 16进制字符串.
     *
     */
    public static String string2HexString(String strPart,String tochartype) {
        try{
            return bytes2HexString(strPart.getBytes(tochartype));
        }catch (Exception e){
            return "";
        }
    }

    /**
     * hexUTF82String.
     * 16进制UTF-8字符串转字符串.
     * @param src 16进制字符串.
     * @return 字节数组.
     *
     */
    public static String hexUTF82String(String src) {

        return hexString2String(src,"UTF-8","UTF-8");
    }

    /**
     * hexUTF16LE2String.
     * 16进制UTF-8字符串转字符串，,此UTF-16LE等同于C#中的Unicode.
     * @param src 16进制字符串.
     * @return 字节数组.
     *
     */
    public static String hexUTF16LE2String(String src) {

        return hexString2String(src,"UTF-16LE","UTF-8");
    }

    /**
     * hexGBK2String.
     * 16进制GBK字符串转字符串.
     * @param src 16进制字符串.
     * @return 字节数组.
     *
     */
    public static String hexGBK2String(String src) {

        return hexString2String(src,"GBK","UTF-8");
    }

    /**
     * hexUnicode2String.
     * 16进制Unicode字符串转字符串.
     * @param src 16进制字符串.
     * @return 字节数组.
     *
     */
    public static String hexUnicode2String(String src) {
        return hexString2String(src,"Unicode","UTF-8");
    }

    /**
     * hexString2String 16进制字符串转字符串.
     * @param src 16进制字符串.
     * @return 字节数组.
     *
     */
    public static String hexString2String(String src,String oldchartype, String chartype) {
        byte[] bts=hexString2Bytes(src);
        try{if(oldchartype.equals(chartype))
            return new String(bts,oldchartype);
        else
            return new String(new String(bts,oldchartype).getBytes(),chartype);
        }
        catch (Exception e){

            return"";
        }
    }
}
