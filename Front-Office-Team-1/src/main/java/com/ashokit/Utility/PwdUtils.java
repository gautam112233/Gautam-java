package com.ashokit.Utility;

import org.apache.commons.lang3.RandomStringUtils;

public class PwdUtils {

	public  static String generateRandomPwd() {
		String characters ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		String pwd=RandomStringUtils.random(6,characters);
		return pwd;
	}
}
