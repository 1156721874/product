package product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Test {
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Test.class);
		PropertyConfigurator.configure("E:\\Study\\tarenaspace\\product\\src\\main\\resources\\log4j.properties");
		logger.error("logger测试");
	}
}
