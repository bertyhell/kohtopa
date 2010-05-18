package gui;

import Language.Language;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class Logger {

	public static org.apache.log4j.Logger logger;
	private static final String LOG_FILE = System.getProperty("user.home") + "\\AppData\\Roaming\\kohtopa\\log.txt";

	public static void init() {
		try {
			logger = org.apache.log4j.Logger.getRootLogger();
			logger.addAppender(new FileAppender(new PatternLayout(), LOG_FILE, false));
			logger.addAppender(new ConsoleAppender(new PatternLayout()));
			Logger.logger.info("log file under: " + LOG_FILE);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "failed to start logger \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}
}
