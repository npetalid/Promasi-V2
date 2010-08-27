/**
 *
 */
package org.promasi.network.protocol.response;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

/**
 * @author m1cRo
 *
 */
public abstract class AbstractResponse {
	/**
	 *
	 * @return
	 */
	public String toXML()
	{
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		XMLEncoder xmlEncoder=new XMLEncoder(outputStream);
		xmlEncoder.writeObject(this);
		xmlEncoder.close();
		String temp=outputStream.toString();
		String result=new String("");
		for(int i=0;i<temp.length();i++)
		{
			char ch=temp.charAt(i);
			if(ch!='\n' && ch!='\r')
			{
				result=result+ch;
			}
		}
		
		result=result+"\r\n";
		return result;
	}
}