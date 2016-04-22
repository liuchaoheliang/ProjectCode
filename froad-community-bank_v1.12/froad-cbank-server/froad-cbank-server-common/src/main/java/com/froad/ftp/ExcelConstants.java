package com.froad.ftp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelConstants {
	
	private static long tmpID = 0L;
	
	private static boolean tmpIDlocked = false;
	
	public static synchronized long getUniqueId() {
		long ltime = 0;
		while (true) {
			if (tmpIDlocked == false) {
				tmpIDlocked = true;
				ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS")
						.format(new Date()).toString()) * 10000;
				if (tmpID < ltime) {
					tmpID = ltime;
				} else {
					tmpID = tmpID + 1;
					ltime = tmpID;
				}
				tmpIDlocked = false;
				return ltime;
			}
		}
	}
}
