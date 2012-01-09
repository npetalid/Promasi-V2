/**
 * 
 */
package org.promasi.utilities.logger;

import org.apache.log4j.Logger;

/**
 * @author alekstheod
 *
 */
public class Log4JLogger implements ILogger {

	/**
	 * 
	 */
	private Logger _logger;
	
	/**
	 * 
	 * @param logger
	 */
	public Log4JLogger(Class<?> clazz)
	{
		_logger = Logger.getLogger(clazz);
	}
	
	@Override
	public void trace(String message) {
		_logger.trace(message);
	}

	@Override
	public void debug(String message) {
		_logger.debug(message);
	}

	@Override
	public void info(String message) {
		_logger.info(message);
	}

	@Override
	public void warn(String message) {
		_logger.warn(message);
	}

	@Override
	public void error(String message) {
		_logger.error(message);
	}

	@Override
	public void fatal(String message) {
		_logger.fatal(message);
	}

}
