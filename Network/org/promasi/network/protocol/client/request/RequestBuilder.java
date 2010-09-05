/**
 *
 */
package org.promasi.network.protocol.client.request;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.net.ProtocolException;
import java.util.NoSuchElementException;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class RequestBuilder
{
	public static Object buildRequest(String message)throws ProtocolException,NullArgumentException
	{
		XMLDecoder decoder=new XMLDecoder(new ByteArrayInputStream(message.getBytes()));
		
		try
		{
			Object object=decoder.readObject();
			decoder.close();
			return object;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new ProtocolException("Wrong protocol");
		}
		catch(NoSuchElementException e)
		{
			throw new ProtocolException("Wrong protocol");
		}
	}
}
