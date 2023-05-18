package com.nk.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Secure {
	public String[] securePwd(String pepwd, String sal) { // 로그인시 이용
		String pePwd = pepwd;
		String hex = "";
		String userInfo[] = new String[2];
		String hexSalt = "";
		// "SHA1PRNG"은 알고리즘 이름
//		SecureRandom random;
		try {
		//	random = SecureRandom.getInstance("SHA1PRNG");
		//	byte[] bytes = new byte[16];
		//	random.nextBytes(bytes);
			// SALT 생성
			String salt = sal;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// 평문 암호화
			md.update(pePwd.getBytes());
			hex = String.format("%064x", new BigInteger(1, md.digest()));

			// 평문암호화 + 솔트
			hexSalt = hex + salt;
			md.update(hexSalt.getBytes());
			hex = String.format("%064x", new BigInteger(1, md.digest()));

			userInfo[0] = salt;
			userInfo[1] = hex;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return userInfo;

	}

	public String[] securePwd(String pwd) { // db에 저장되는 값은 salt , (salt+해싱)을 해싱한 값 회원가입시 이용
		String pepwd = pwd;
		String hex = "";
		String userInfo[] = new String[2];
		String hexSalt = "";
		// "SHA1PRNG"은 알고리즘 이름
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			// SALT 생성
			String salt = new String(Base64.getEncoder().encode(bytes));
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// 평문 암호화
			md.update(pepwd.getBytes());
			hex = String.format("%064x", new BigInteger(1, md.digest()));

			// 평문암호화 + 솔트
			hexSalt = hex + salt;
			md.update(hexSalt.getBytes());
			hex = String.format("%064x", new BigInteger(1, md.digest()));

			userInfo[0] = salt;
			userInfo[1] = hex;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	
}
