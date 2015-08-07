package com.service.authmgt.common;

import java.math.*;

/**
 * <p>
 * Title: OmcrCipherKey类
 * </p>
 * <p>
 * Description: 该类用于封装密钥
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: CPIT
 * </p>
 * 
 * @author hull
 * @version 1.0
 */

public class CipherKey {
	/**
	 * 参数1
	 */
	private BigInteger e;

	/**
	 * 参数2
	 */
	private BigInteger n;

	/**
	 * 构造参数
	 * 
	 * @param e
	 *            参数1，BigInteger类型
	 * @param n
	 *            参数2，BigInteger类型
	 */
	public CipherKey(BigInteger e, BigInteger n) {
		if (e == null) {
			throw new IllegalArgumentException("e is null.");
		}
		if (n == null) {
			throw new IllegalArgumentException("n is null.");
		}
		this.e = e;
		this.n = n;
	}

	/**
	 * 获取参数1
	 * 
	 * @return 参数1
	 */
	public BigInteger getE() {
		return e;
	}

	/**
	 * 获取参数2
	 * 
	 * @return 参数2
	 */
	public BigInteger getN() {
		return n;
	}
}
