package databasepoller;

import java.awt.Toolkit;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bert Verhelst
 */
public class Poller {

	Timer timer;

	public Poller(int minutes) {
		timer = new Timer();
		timer.schedule(new RemindTask(), minutes * 1000);
	}

	class RemindTask extends TimerTask {

		public void run() {
			new Poller(3);
			preformAction();
			timer.cancel();
		}

		//specify the actions that need to happen
		private void preformAction() {
			checkInvoicesToBeSend();
			checkTaskReminder();
		}

		private void checkInvoicesToBeSend() {
			System.out.println("getting invoices");
			try {
				for (String xml : DataConnector.selectInvoicesToBeSend()) {
					System.out.println("xml file");
					//TODO read xml and print pdf
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}

		private void checkTaskReminder() {
			
		}
	}

	public static void main(String args[]) {
		DataConnector.init();
		System.out.println("start");
		new Poller(1);
	}
}
