package org.promasi.coredesigner.utilities;

import java.util.regex.Pattern;

public class NumberUtils {

	
	public static boolean isDouble(String string) {
		
		boolean results = false;
		
		if (string != null) {
			if (!string.trim().isEmpty()) {
				Pattern doublePattern = Pattern.compile("-?\\d+(\\.\\d*)?");
				
				results =  doublePattern.matcher(string).matches();
			}
		}
	    return results;
	}
}
