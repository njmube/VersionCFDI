package mx.trillas.versioncfdi.util;

import org.apache.log4j.Logger;

public class SVNUtil {
	private static String COMMAND_WINDOWS = "cmd /C cd C:\\SISFA\\ARCXML\\facturar\\cfdi & C:\\SISFA\\tmp\\Software\\Windows\\SlikSvn\\svn.exe info";
	private static String COMMAND_LINUX = "svn info /home/geduardo/facturacion/cfdi";
	private static Logger logger = Logger.getLogger(SVNUtil.class);

	public static Integer getLocalRevision() {
		Integer localRevision = null;
		String localRevisionStr = null;
		if (OSValidator.getOsType().equals(OSValidator.OS_TYPE.WINDOWS))
			try {
				localRevisionStr = Local
						.getLocalNumberVersion(COMMAND_WINDOWS);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		if (OSValidator.getOsType().equals(OSValidator.OS_TYPE.UNIX)) {

			try {
				localRevisionStr = Local
						.getLocalNumberVersion(COMMAND_LINUX);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		if (localRevisionStr != null && !localRevisionStr.equals(""))
			localRevision = new Integer(localRevisionStr).intValue();
		return localRevision;
	}
}
