package hoa.flatlaf.demo;

import java.util.Locale;

public class ApplicationContext {
	
	private static ApplicationContext instance;

	// Default locale is German
	private Locale locale = Locale.GERMAN;

	// Private constructor to prevent direct instantiation
	private ApplicationContext() {
		// Initialize the application context
	}

	// Method to get the singleton instance of the application context
	public static synchronized ApplicationContext getInstance() {
		if (instance == null) {
			instance = new ApplicationContext();
		}
		return instance;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}