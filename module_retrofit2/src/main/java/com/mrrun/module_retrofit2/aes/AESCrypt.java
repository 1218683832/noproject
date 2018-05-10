package com.mrrun.module_retrofit2.aes;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/*
 * Aes 加密解密类
 */
public class AESCrypt {

	public AESCrypt() {
	}

	/*
	 * 加密为十六进制字符串
	 */
	public static String EncrypttoHex(String data, String key)  {

		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = data.getBytes("UTF-8");// data.getBytes();
			int plaintextLength = dataBytes.length;

			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength
						+ (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			byte[] enCodeFormat = shortmd5(key);
			SecretKeySpec keyspec = new SecretKeySpec(enCodeFormat, "AES");
			IvParameterSpec ivspec = new IvParameterSpec(enCodeFormat);

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);

			return byte2hex(encrypted).toLowerCase();

		} catch (Exception e) {
			return null;
		}
	}

	// ===========================

	public static byte[] shortmd5(String b) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(b.getBytes("UTF-8"));
		byte[] digest = md.digest();
		return digest;
	}

	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer(b.length);
		String stmp = "";
		int len = b.length;
		for (int n = 0; n < len; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs.append("0").append(stmp);
			else {
				hs = hs.append(stmp);
			}
		}
		return String.valueOf(hs);
	}
}
