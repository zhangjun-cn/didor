package com.dali.didor.utils;

public class StringUtils {

	public static final String EMPTY = "";

	public static boolean isBlank(String str) {
		return str == null || EMPTY.equals(str) || EMPTY.equals(str.trim());
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static String defaultString(String str) {
		return str == null ? EMPTY : str;
	}

	public static long toLong(String str, long defaultValue) {
		long result = defaultValue;
		if (isNotBlank(str)) {
			try {
				result = Long.parseLong(str);
			} catch (NumberFormatException ex) {
			}
		}
		return result;
	}

}
