/**
 * 
 */
package org.promasi.utilities.logger;

/**
 * @author alekstheod
 *
 */
public class LoggerFactory {

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static ILogger getInstance(Class<?> clazz) {
		return new Log4JLogger(clazz);
	}

}
