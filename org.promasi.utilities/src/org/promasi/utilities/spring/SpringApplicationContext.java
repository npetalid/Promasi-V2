/**
 * 
 */
package org.promasi.utilities.spring;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author alekstheod
 * Represent the application context
 * used in order to generate the spring's
 * beans instances.
 */
public class SpringApplicationContext {
	
	/**
	 * Instance of {@link ApplicationContext}
	 * needed in order for the spring beans
	 * instatiation.
	 */
	private ApplicationContext _context;
	
	/**
	 * Instance of SpringApplicationContext class
	 */
	private static SpringApplicationContext _instance;
	
	/**
	 * Instance of {@link Lock} interface implementation.
	 * needed in order to synchronize the getInstance method.
	 */
	private static final Lock _lockObject = new ReentrantLock();
	
	/**
	 * 
	 * @param fileName
	 * @throws SpringException 
	 */
	private SpringApplicationContext( ){
	}
	
	/**
	 * Will return the 
	 * @param clazz
	 * @return
	 */
	public <T> T getBean(Class<T> clazz){
		return _context.getBean(clazz);
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean init( String fileName ){
		boolean result = false;
		
		try{
			if( _context == null ){
				_context = new ClassPathXmlApplicationContext(fileName);
				result = true;
			}
		}catch( Exception e ){
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	public static SpringApplicationContext getInstance(){
		try{
			_lockObject.lock();
			if( _instance == null ){
				_instance = new SpringApplicationContext();
			}
		}finally{
			_lockObject.unlock();
		}
		
		return _instance;
	}
	
	/**
	 * 
	 * @param beanName
	 * @param objects
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName, Object... objects){
		return (T)_context.getBean(beanName, objects);
	}
	
	/**
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T getBean(String id, Class<T> clazz){
		return _context.getBean(id, clazz);
	}
	
}
