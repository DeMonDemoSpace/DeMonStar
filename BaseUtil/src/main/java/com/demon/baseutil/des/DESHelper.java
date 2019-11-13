package com.demon.baseutil.des;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESHelper {

    // 采用的是DES算法
    private SecretKey secretKey;// 密钥
    private IvParameterSpec iv;// 偏移量
    private Cipher decryptCipher;// 解密对象
    private byte[] keyBytes = new byte[8];
    private byte[] ivBytes = new byte[8];

    /*
     * strKey必须是8位长度的字符串，即64bit。
     */
    public DESHelper(String key, boolean isDecode) {
        try {
            byte[] md5Bytes = MD5Util.MD5Bytes(key);
            for (int i = 0; i < 8; i++) {
                keyBytes[i] = md5Bytes[i];
                ivBytes[i] = md5Bytes[i + 8];
            }
            // 指定算法产生解密对象
            decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); //选择模式和填充方式，与.NET对应
            // 由用户密钥产生系统密钥
            secretKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(keyBytes));
            // 产生偏移量
            iv = new IvParameterSpec(ivBytes);
            // 加载解密对象
            decryptCipher.init(isDecode ? Cipher.DECRYPT_MODE : Cipher.ENCRYPT_MODE, secretKey, iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密.net平台上C#加密过的字节数组
     *
     * @param arrB 待解密的字节数组
     * @return 解密后的字节数组
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] decrypt(byte[] arrB) throws IllegalBlockSizeException, BadPaddingException {
        return decryptCipher.doFinal(arrB);
    }


} 
