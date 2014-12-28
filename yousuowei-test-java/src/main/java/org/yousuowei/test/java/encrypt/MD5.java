package org.yousuowei.test.java.encrypt;

import java.security.MessageDigest;

/**
 * MD5 MD5 -- message-digest algorithm 5
 * （信息-摘要算法）缩写，广泛用于加密和解密技术，常用于文件校验。校验？不管文件多大
 * ，经过MD5后都能生成唯一的MD5值。好比现在的ISO校验，都是MD5校验
 * 。怎么用？当然是把ISO经过MD5后产生MD5的值。一般下载linux-ISO的朋友都见过下载链接旁边放着MD5的串。就是用来验证文件是否一致的。
 * 
 * 通常我们不直接使用上述MD5加密。通常将MD5产生的字节数组交给BASE64再加密一把，得到相应的字符串。
 * 
 * @author jie
 * 
 */
public class MD5 {
	private static final String KEY_MD5 = "MD5";

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();
	}

}
