/**
 * 
 */
package org.promasi.utilities.logger;

import org.apache.log4j.xml.DOMConfigurator;

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
	public static ILogger getInstance( Class<?> clazz ) {
		if( !_isInitialized ){
			DOMConfigurator.configure("log4j.xml");
			_isInitialized = true;
		}
		
		return new Log4JLogger(clazz);
	}

}
