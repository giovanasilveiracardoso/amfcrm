package com.amf.crm.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class UtilFormat {

    public static String formatString(String value, String mask) {
        MaskFormatter mf;
		try {
			mf = new MaskFormatter(mask);
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(value);
		} catch (ParseException e) {
			return value;
		}
    }
	
}
