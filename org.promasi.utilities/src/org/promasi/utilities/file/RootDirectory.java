/**
 * 
 */
package org.promasi.utilities.file;

import java.io.File;
import java.io.IOException;

/**
 * @author m1cRo
 *
 */
public class RootDirectory 
{
	/**
	 * 
	 */
	public static final String CONST_DATA_DIRECTORY_NAME="Data";
	
	/**
	 * 
	 */
	public static final String CONST_RESOURCES_DIRECTORY_NAME="Resources";
	
	/**
	 * 
	 */
	public static final String CONST_IMAGES_DIRECTORY_NAME="Images";
	
	/**
	 * 
	 */
	private File _file;
	
	/**
	 * 
	 */
	private static RootDirectory _instance=null;
	
	/**
	 * 
	 * @param path
	 * @param delimiter
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	private RootDirectory()throws IOException{	
		_file=new File("");
	}
	
	/**
	 * 
	 * @param path
	 * @param delimiter
	 * @return
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	static public RootDirectory getInstance()throws IOException{
		if(_instance==null){
			return new RootDirectory();
		}

		return _instance;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getImagesDirectory(){
		return _file.getAbsolutePath()+File.separator+CONST_DATA_DIRECTORY_NAME+File.separator+CONST_RESOURCES_DIRECTORY_NAME+File.separator+CONST_IMAGES_DIRECTORY_NAME+File.separator;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getResourcesDirectory(){
		return _file.getAbsolutePath()+File.separator+CONST_DATA_DIRECTORY_NAME+File.separator+CONST_RESOURCES_DIRECTORY_NAME+File.separator;
	}

	/**
	 * @return the path
	 */
	public String getRootDirectory() {
		return _file.getAbsolutePath()+File.separator+CONST_DATA_DIRECTORY_NAME+File.separator;
	}

	/**
	 * @return the delimiter
	 */
	public String getSeparator() {
		return File.separator;
	}
}
