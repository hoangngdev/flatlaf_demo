package hoa.flatlaf.demo.util;

import java.util.PropertyResourceBundle;

import hoa.flatlaf.demo.ApplicationContext;

public class ResourceBundleUtil {

	public static String getDisplayKey(String key) {
		var bundle = PropertyResourceBundle.getBundle("messages", ApplicationContext.getInstance().getLocale());
		return bundle.getString(key);
	}
	
}
