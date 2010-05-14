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

	public static void init() {
		try {
			System.out.println("log file under: " + System.getProperty("user.home") + "\\Local Settings\\Application Data\\kohtopa\\log.txt");
			logger = org.apache.log4j.Logger.getRootLogger();
			logger.addAppender(new FileAppender(new PatternLayout(), System.getProperty("user.home") + "\\Local Settings\\Application Data\\kohtopa\\log.txt", false));
			logger.addAppender(new ConsoleAppender(new PatternLayout()));
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "failed to start logger \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}
}