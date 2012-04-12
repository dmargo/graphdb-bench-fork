package com.tinkerpop.bench;

/**
 * Utilities for text output to the console
 * 
 * @author Peter Macko (pmacko@eecs.harvard.edu)
 */
public class ConsoleUtils {
	
	private static final String SPACES = "                                        ";
	private static final String BACKSPACES = "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b";

	private static final int COLOR_WARN = 1;
	private static final int COLOR_ERROR = 8 + 1;
	private static final int COLOR_HEADER = 8 + 7;
	private static final int COLOR_SECTION_HEADER = 8 + 4;
	
	private static boolean useEscapeCharacter = false;
	
	private static long lastProgressDraw = 0;
	private static int lastProgressExtra = 0;


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
	
	
	/**
	 * Print a progress indicator
	 * 
	 * @param value the position
	 * @param max the maximum value
	 * @param extra extra text to display before the percentages
	 */
	public static void printProgressIndicator(int value, int max, String extra) {
		if (value > max) value = max;
		long t = System.currentTimeMillis();
		if (t < lastProgressDraw + 100 && value != max) return;
		lastProgressDraw = t;
		
		String before = extra == null ? "" : "[" + extra + "] ";
		String before_b = "";
		if (extra != null) {
			int l = BACKSPACES.length();
			int n = before.length();
			while (n >= l) {
				before_b += BACKSPACES;
				n -= l;
			}
			before_b += BACKSPACES.substring(l - n);
		}
			
		String clear = "";
		if (lastProgressExtra > 0) {
			int l = SPACES.length();
			int n = lastProgressExtra;
			while (n >= l) {
				clear += SPACES;
				n -= l;
			}
			clear += SPACES.substring(l - n);
			l = BACKSPACES.length();
			n = lastProgressExtra;
			while (n >= l) {
				clear += BACKSPACES;
				n -= l;
			}
			clear += BACKSPACES.substring(l - n);
		}
		lastProgressExtra = before_b.length();
		
		System.out.printf(": %s%6.2f%%%s%s\b\b\b\b\b\b\b\b\b", before, 100 * value / (float) max, clear, before_b);
	}
	
	
	/**
	 * Print a progress indicator
	 * 
	 * @param value the position
	 * @param max the maximum value
	 */
	public static void printProgressIndicator(int value, int max) {
		printProgressIndicator(value, max, null);
	}
}
