/**
 * 
 */
package org.promasi.utilities.logger;

/**
 * @author alekstheod
 *
 */
public interface ILoggerFactory {
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public ILogger getInstance( Class<?> clazz );
}
