/**
 * 
 */
package org.promasi.utilities.logger;

/**
 * @author alekstheod
 *
 */
public interface ILogger {
	/**
	 * 
	 * @param message
	 */
    public void trace(String message);
    
    /**
     * 
     * @param message
     */
    public void debug(String message);
    
    /**
     * 
     * @param message
     */
    public void info(String message);
    
    /**
     * 
     * @param message
     */
    public void warn(String message);
    
    /**
     * 
     * @param message
     */
    public void error(String message);
    
    /**
     * 
     * @param message
     */
    public void fatal(String message);
}
