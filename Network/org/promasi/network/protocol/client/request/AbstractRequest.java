/**
 *
 */
package org.promasi.network.protocol.client.request;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import org.w3c.tools.codec.Base64Encoder;

/**
 * @author m1cRo
 *
 */
public abstract class  AbstractRequest implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 * @return
	 */
	public String toProtocolString()
	{
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		XMLEncoder xmlEncoder=new XMLEncoder(outputStream);
		xmlEncoder.writeObject(this);
		xmlEncoder.close();
		String temp=new Base64Encoder(outputStream.toString()).processString();
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
