package team.os.start;

import org.zxy.sqlhelper.init.InitializeMySqlHelper;

import team.core.init.SystemInit;





public class SystemStart{

    public void execute() {
    	InitializeMySqlHelper.initialize();
    	SystemInit sysInit = new SystemInit();
    	sysInit.startUDP();
    	sysInit.startTCP();
    	sysInit = null;
    }
}
