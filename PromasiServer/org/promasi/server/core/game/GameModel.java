/**
 *
 */
package org.promasi.server.core.game;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class GameModel
{
	public GameModel(String xmlModel)throws NullArgumentException,IllegalArgumentException
	{
		if(xmlModel==null)
		{
			throw new NullArgumentException("Wrong argument xmlModel==null");
		}

		if(xmlModel.isEmpty())
		{
			throw new IllegalArgumentException("Wrong argument xmlModel.isEmpty()");
		}
	}

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
		return result;
	}
}
