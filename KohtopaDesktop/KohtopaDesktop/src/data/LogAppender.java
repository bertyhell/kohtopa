package data;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class LogAppender implements Appender {

	public void addFilter(Filter filter) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Filter getFilter() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void clearFilters() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void close() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void doAppend(LoggingEvent le) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public String getName() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setErrorHandler(ErrorHandler eh) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public ErrorHandler getErrorHandler() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setLayout(Layout layout) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Layout getLayout() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setName(String string) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public boolean requiresLayout() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
}
