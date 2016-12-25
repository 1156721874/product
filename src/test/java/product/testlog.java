package product;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import junit.framework.TestCase;

public class testlog extends TestCase {
	
	
	public static void testlog1(){
		//------程序初始化阶段----
		//读取配置
		String currentDir = System.getProperty("user.dir");
		PropertyConfigurator.configure("E:\\Study\\tarenaspace\\product\\src\\main\\resources\\log4j.properties");
		//如果是xml配置
		//DOMConfigurator.configure(currentDir+"/conf/log4j.xml");
		//此外还可以调用configureAndWatch监听配置的变动并重新加载。


		//-----log调用-------
		Log dbLog = LogFactory.getLog("dbLog");
		if(dbLog.isErrorEnabled()){  // 先判断log级别再调用，减少不必要的代码执行。
		dbLog.error("test db log");			
		}

		//-----程序结束阶段------
		//关闭log    稍后文章中会介绍在某些场合关闭log的必要性。
		LogManager.shutdown();    
	}
	public static void main(String[] args) {
		testlog1();
	}
}
