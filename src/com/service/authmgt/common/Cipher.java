package com.service.authmgt.common;

import java.math.*;
import java.io.*;

/**
 * 
 * 字符串加密工具类
 * @author hull
 * @version 1.0
 * 
 */
public class Cipher {
	/** 
	 * 最大能处理的块大小 
	 */
	public static final int MAXBLOCKSIZE = 255;

	/** 
	 * 密文中的块分割符 
	 */
	private static final String BLOCKSEPARATOR = "X";

	/**
	 * 加密String，采用不可逆算法，不能解密
	 * 
	 * @param key
	 *            CipherKey 加密密钥
	 * @param data
	 *            String 待加密数据，不得为null,"",长度无最大限制
	 * @return 密文，String形式
	 */
	public static String encrypt(CipherKey key, String data) {

		// 校验参数，若非法抛IllegalArgumentException
		if (key == null || data == null || data.length() == 0) {
			throw new java.lang.IllegalArgumentException("非法参数");
		}

		// 待加密数据转换为byte[]
		byte[] bData;
		try {
			bData = data.getBytes("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			return null;
		}

		// 下面将bData按照MAXBLOCKSIZE的大小一块一块加密，并拼成密文
		// 尚未处理的数据长度
		int len = bData.length;

		// 用于保存最终结果的StringBuffer
		StringBuffer result = new StringBuffer();
		// 循环，直到没有需要处理的数据
		while (len > 0) {
			// 计算本次处理的长度
			int thisLen = len > MAXBLOCKSIZE ? MAXBLOCKSIZE : len;
			// 将数据拷贝为一个byte[]
			byte[] tmp = new byte[thisLen];
			System.arraycopy(bData, bData.length - len, tmp, 0, thisLen);
			// 转换为BigInteger
			BigInteger m = new BigInteger(1, tmp);
			// 加密该段数据
			BigInteger c = m.modPow(key.getE(), key.getN());
			// 将加密结果编码为16进制串，并添加块分割符
			result.append(c.toString(16) + BLOCKSEPARATOR);
			// 修改待处理的数据长度
			len -= thisLen;
		}
		// 删除最后一个块分割符
		result.deleteCharAt(result.length() - 1);

		return result.toString();
	}

	/**
	 * 加密字节数组，采用不可逆算法，不能解密
	 * 
	 * @param key
	 *            CipherKey 加密密钥
	 * @param data
	 *            byte[]
	 *            待加密数据，不得为null,byte[0];data[0]不得为0;data.len必须为1..MAXBLOCKSIZE
	 * @return 密文，String形式
	 */
	public static String encrypt(CipherKey key, byte[] data) {

		if (key == null) {
			throw new IllegalArgumentException("key is null.");
		}
		// 校验参数，若非法抛IllegalArgumentException
		if (data == null || data.length == 0 || data.length > MAXBLOCKSIZE
				|| data[0] == 0) {
			IllegalArgumentException ex = new IllegalArgumentException(
					"Illegal argument,data length must be 1.." + MAXBLOCKSIZE
							+ " and data[0] must not be 0.");
			throw ex;
		}

		// 将data转换为BigInteger，符号取正
		BigInteger m = new BigInteger(1, data);

		// 进行加密运算
		BigInteger c = m.modPow(key.getE(), key.getN());

		// 将加密后的数据编码为16进制串后返回
		return c.toString(16);
	}
}
