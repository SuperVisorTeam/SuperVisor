package com.gdut.supervisor.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

public class ClassGroupXMLHandler {
	public static String[] exchangeXmlToStringarr(Context context, int ID)
			throws XmlPullParserException, IOException {
		List<String> list = null;
		Resources r = context.getResources();
		XmlResourceParser xrp = r.getXml(ID);
		int event = xrp.getEventType();// 产生第一个事件
		while (event != XmlResourceParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:// 判断当前事件是否是文档开始事件
				list = new ArrayList<String>();
				list.add("");
				break;
			case XmlPullParser.START_TAG:// 判断当前事件是否是标签元素开始事件
				if (xrp.getName().equals("string-array")) {
					list.add(xrp.getAttributeValue(0));
				}
				break;
			case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
				break;
			}
			event = xrp.next();// 进入下一个元素并触发相应事件
		}
		return StringHandler.exchangeListToStringArr(list);
	}
}
