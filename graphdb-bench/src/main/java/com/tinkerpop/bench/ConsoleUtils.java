package com.tinkerpop.bench;

/**
 * Utilities for text output to the console
 */
public class ConsoleUtils {

	private static final int COLOR_WARN = 1;
	private static final int COLOR_ERROR = 8 + 1;
	private static final int COLOR_HEADER = 8 + 7;
	private static final int COLOR_SECTION_HEADER = 8 + 4;
	
	private static boolean useEscapeCharacter = false;


	/**
	 * Determine whether the escape characters can be used in the standard output
	 */
	static {
		String osName = System.getProperty("os.name");
		useEscapeCharacter = (osName.startsWith("Linux") || osName.startsWith("Mac"));
	}
	
	
	/**
	 * Returns a stdout escape sequence for foreground color, or an empty string if the escape character
	 * should not be used
	 */
	private static String escapeColor(int color) {
		if (!useEscapeCharacter) return "";
		if ((color < 0) || (color >= 16)) return "";
		
		return "" + (char)27 + ((color < 8) ? ("[0;3" + color) : ("[1;3" + (color - 8))) + "m";
	}
	
	
	/**
	 * Returns a stdout escape sequence for normal color output
	 */
	private static String escapeNormal() {
		if (!useEscapeCharacter) return "";
		return "" + (char)27 + "[0;0m";
	}
	
	
	/**
	 * Print a warning to the screen
	 */
	public static void warn(String message) {
		System.out.println(escapeColor(COLOR_WARN) + "Warning:" + escapeNormal() + " " + message);
	}	
	
	
	/**
	 * Print an error to the screen
	 */
	public static void error(String message) {
		System.out.println(escapeColor(COLOR_ERROR) + "Error:" + escapeNormal() + " " + message);
	}	
	

	/**
	 * Print a header to the screen
	 */
	public static void header(String name) {
		System.out.println(escapeColor(COLOR_HEADER) + name + escapeNormal());
	}	

	
	/**
	 * Print a section header to the screen
	 */
	public static void sectionHeader(String name) {
		System.out.println("\n\n" + escapeColor(COLOR_SECTION_HEADER) + name + escapeNormal() + "\n");
	}
}
