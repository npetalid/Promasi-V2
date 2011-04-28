/**
 * 
 */
package org.promasi.utilities.serialization;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;


/**
 * @author m1cRo
 *
 */
public class SerializableObject
{
	/**
	 * 
	 * @return
	 */
	public String serialize(){
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		XMLEncoder xmlEncoder=new XMLEncoder(outputStream);
		xmlEncoder.writeObject(this);
		xmlEncoder.close();
		return outputStream.toString();
	}
}
