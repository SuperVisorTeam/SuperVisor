package com.gdut.supervisor.utils;

import java.util.List;

public class StringHandler {

	public static String[] exchangeListToStringArr(List<String> list) {
		if(list == null)
			return null;
		String[] str = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			str[i] = list.get(i);
		}
		return str;
	}
}
