package team.os.start;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class OsListener implements ServletContextListener {
	
	private static Logger log = Logger.getLogger(OsListener.class);
	private java.util.Timer timer = null;

	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		log.info("tomecat shoudown");
	}

	public void contextInitialized(ServletContextEvent event) {
		timer = new java.util.Timer(true);
		log.info("tomcat start");
		timer.schedule(new OsStart(), 500);
	}

}