package cn.gzhu.test.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class AESCryptoUtil {

    // 加密算法
    private static final String ALG = "AES";
    // 秘药
    private static final String secret = "exam100";
    // 字符编码
    private static final String ENC = "UTF-8";
    // 密钥正规化算法
    private static final String SEC_NORMALIZE_ALG = "MD5";

    // 加密
    public static String encrypt(String secret, String data) throws Exception {
        MessageDigest dig = MessageDigest.getInstance(SEC_NORMALIZE_ALG);
        byte[] key = dig.digest(secret.getBytes(ENC));
        SecretKeySpec secKey = new SecretKeySpec(key, ALG);

        Cipher aesCipher = Cipher.getInstance(ALG);
        byte[] byteText = data.getBytes(ENC);

        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(byteText);
        return new String(Base64.getEncoder().withoutPadding().encode(byteCipherText), ENC);
    }

    // 解密
    public static String decrypt(String secret, String ciphertext) throws Exception {
        MessageDigest dig = MessageDigest.getInstance(SEC_NORMALIZE_ALG);
        byte[] key = dig.digest(secret.getBytes(ENC));
        SecretKeySpec secKey = new SecretKeySpec(key, ALG);

        Cipher aesCipher = Cipher.getInstance(ALG);
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] cipherbytes = Base64.getDecoder().decode(ciphertext.getBytes());
        byte[] bytePlainText = aesCipher.doFinal(cipherbytes);

        return new String(bytePlainText, ENC);
    }

    public static void main(String[] args) {
        String data = "小明同学";
        try {
            System.out.println("原始数据=" + data);
            String encryptData = AESCryptoUtil.encrypt(secret, data);
            System.out.println("加密后=" + encryptData);
            String decryptData = AESCryptoUtil.decrypt(secret, encryptData);
            System.out.println("解密后=" + decryptData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
