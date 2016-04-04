package mx.trillas.versioncfdi.util;

public class OSValidator {

	private static String OS = System.getProperty("os.name").toLowerCase();

	public static enum OS_TYPE {
		WINDOWS, MAC, UNIX, SOLARIS, UNKNOW
	}

	public static OS_TYPE getOsType() {

		// System.out.println(OS);

		if (isWindows()) {
			return OS_TYPE.WINDOWS;
			// System.out.println("This is Windows");
		} else if (isMac()) {
			return OS_TYPE.MAC;
			// System.out.println("This is Mac");
		} else if (isUnix()) {
			return OS_TYPE.UNIX;
			// System.out.println("This is Unix or Linux");
		} else if (isSolaris()) {
			return OS_TYPE.SOLARIS;
			// System.out.println("This is Solaris");
		} else {
			return OS_TYPE.UNKNOW;
			// System.out.println("Your OS is not support!!");
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
