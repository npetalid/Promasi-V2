/**
 * 
 */
package org.promasi.utilities.logger;

import org.apache.log4j.BasicConfigurator;

/**
 * @author alekstheod
 *
 */
public class LoggerFactory {

	/**
	 * 
	 */
	private static boolean _isInitialized = false;
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static ILogger getInstance(Class<?> clazz) {
		if( !_isInitialized ){
			BasicConfigurator.configure();
			_isInitialized = true;
		}
		
		return new Log4JLogger(clazz);
	}

}
