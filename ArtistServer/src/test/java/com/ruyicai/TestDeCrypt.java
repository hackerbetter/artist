package com.ruyicai;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by hacker on 2014/5/9.
 */
public class TestDeCrypt {
    /**
     * 解密算法
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            byte[] raw = sKey.getBytes("GBK");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = hex2byte(sSrc.getBytes());
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original);
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
    /**
     * 十六进制转化成byte数组
     *
     * @param b
     * @return
     */
    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    @Test
    public void testDecrypt(){
        BufferedInputStream bis=null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("F:/request.txt"));
            String data = br.readLine();//一次读入一行，直到读入null为文件结束
            while( data!=null){
                String[]s=data.split(" ");
                try {
                    System.out.println("=========================================================================");
                    System.out.print(s[0]);
                    System.out.println(decrypt(s[1],"<>hj12@#$$%^~~ff"));
                } catch (Exception e) {
                    continue;
                }
                data = br.readLine(); //接着读下一行
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
