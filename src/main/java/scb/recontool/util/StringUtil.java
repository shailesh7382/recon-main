package scb.recontool.util;

public class StringUtil {
	
	public static boolean empty(String s) {
	    return s == null || s.trim().length() == 0;
	  }

	  public static boolean isNotEmpty(String s) { return ! empty(s); }


}
