package team.os.start;

import java.util.TimerTask;

import org.apache.log4j.Logger;

public class OsStart extends TimerTask {
	private static Logger log = Logger.getLogger(OsStart.class);
    public void run() {
    	log.info("系统初始化开始");
    	new SystemStart().execute();
    }
}