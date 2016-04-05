package mx.trillas.versioncfdi.util;

public class OSValidator {

	private static String OS = System.getProperty("os.name").toLowerCase();

	public static enum OS_TYPE {
		WINDOWS, MAC, UNIX, SOLARIS, UNKNOW
	}

	public static OS_TYPE getOsType() {

		if (isWindows()) {
			return OS_TYPE.WINDOWS;
		} else if (isMac()) {
			return OS_TYPE.MAC;
		} else if (isUnix()) {
			return OS_TYPE.UNIX;
		} else if (isSolaris()) {
			return OS_TYPE.SOLARIS;
		} else {
			return OS_TYPE.UNKNOW;
		}
	}

	private static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	private static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	private static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS
				.indexOf("aix") > 0);
	}

	private static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}
}
