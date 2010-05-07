package databasepoller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Bert Verhelst
 */
public class Poller {

	public static void main(String args[]) {
		DataConnector.init();

		ScheduledExecutorService deamonInvoices = Executors.newSingleThreadScheduledExecutor();
		ScheduledExecutorService deamonTask = Executors.newSingleThreadScheduledExecutor();
		ScheduledExecutorService deamonClean = Executors.newSingleThreadScheduledExecutor();

		deamonInvoices.scheduleAtFixedRate(new RunnableInvoice(), 0, 20, TimeUnit.SECONDS);
		deamonTask.scheduleAtFixedRate(new RunnableTask(), 0, 5, TimeUnit.SECONDS);
		deamonClean.scheduleAtFixedRate(new RunnableClean(), 0, 24, TimeUnit.HOURS);

	}
}
