package com.gdut.supervisor.utils;

import java.util.ArrayList;
import java.util.List;

public class ClassNameHandler {
	public static String[] exchangeStringToArray(String s) {
		List<Integer> numList = new ArrayList<Integer>();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ',') {
				numList.add(i);
			}
		}
		if (numList.size() == 0) {
			list.add(s);
		} else {
			int j;
			for (j = 0; j < numList.size(); j++) {
				if (j == 0) {
					list.add(s.substring(0, numList.get(0)));

				} else {
					list.add(s.substring(numList.get(j - 1) + 1, numList.get(j)));
				}
			}
			list.add(s.substring(numList.get(j - 1) + 1, s.length()));
		}
		return StringHandler.exchangeListToStringArr(list);
	}
}
