package cn.stylefeng.guns.core.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.io.File;

public class Sha256 {

    public static void main(String[] args) {
        //输入文件路径
        File file = new File("C:\\Users\\Admin\\Desktop\\读书小程序.txt");
        //输出hash值
        System.out.println(getFileSHA1(file));
    }

    public static String getFileSHA1(File file) {
        String str = "";
        try {
            str = getHash(file, "SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private static String getHash(File file, String hashType) throws Exception {
        InputStream fis = new FileInputStream(file);
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }
    private static String toHexString(byte b[]) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            sb.append(Integer.toHexString(aB & 0xFF));
        }
        return sb.toString();
    }

}
