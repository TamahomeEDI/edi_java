package jp.co.keepalive.springbootfw.util.uid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;

public class UidUtil {
    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO 自動生成された catch ブロック
            throw new CoreRuntimeException(e);
        }
    }

    public static String getUID(Object obj) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(byteStream).writeObject(obj);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            throw new CoreRuntimeException(e);
        }
        byte[] serialized = byteStream.toByteArray();
        return getUID(serialized);
    }

    public static String getUID(byte[] str) {
        return hashByte2MD5(md.digest(str));
    }

    public static byte[] getUidBytes(byte[] str){
        return md.digest(str);
    }

    //MD5　ハッシュ関数
    public static String hashByte2MD5(byte []hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }

        return hexString.toString();
    }

    public static String createShortUID(int num){
        String seed = new Long(System.currentTimeMillis()).toString();
        String longUid = getUID(seed);
        String shortUid = longUid.substring(0, num);
        System.out.println(longUid + " : " + shortUid);
        return shortUid;
    }

}
