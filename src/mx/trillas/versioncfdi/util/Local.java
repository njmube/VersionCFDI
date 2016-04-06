package mx.trillas.versioncfdi.util;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Local {

//	private static Logger log = Logger.getLogger(Local.class);

	private static final String NUMBER = "[0-9]+";
	private static final String NUMBERVERSION_PATTERN = "(Revision|Revisión):\\s[0-9]+";

	public static String getLocalNumberVersion(String command) throws Exception {

		String expression = null;
		String localVersion = null;
		String stringCommand = null;

		try {
			stringCommand = execCommand(command);
		} catch (Exception e) {
			throw e;
		}

		if (stringCommand != null) {
			expression = containExpression(stringCommand);
			localVersion = getNumber(expression);
			return localVersion;
		}
		return null;
	}

	public static String execCommand(String cmdline) throws Exception {

		String stringCommand = null;
		Process process = null;
		InputStream is = null;

		try {
			process = Runtime.getRuntime().exec(cmdline);
			is = process.getInputStream();

			byte[] buffer = new byte[1024];

			for (; (is.read(buffer)) >= 0;) {
				stringCommand = new String(buffer);
			}
		} catch (Exception e) {
			throw e;
		}
		return stringCommand;
	}

	public static String containExpression(String expression) throws IllegalStateException {
		Pattern pattern = null;
		Matcher matcher = null;

		pattern = Pattern.compile(NUMBERVERSION_PATTERN);
		matcher = pattern.matcher(expression);
		try {
			if (matcher.find() == true) {
				// System.out.println(matcher.group(0));
				return matcher.group(0);
			}
		} catch (IllegalStateException e) {
			throw e;
		}
		return null;
	}

	public static String getNumber(String expression) throws IllegalStateException {
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile(NUMBER);
		matcher = pattern.matcher(expression);
		try {
			if (matcher.find() == true) {
				// System.out.println(matcher.group(0));
				return matcher.group(0);
			}
		} catch (IllegalStateException e) {
			throw e;
		}
		return null;
	}
}