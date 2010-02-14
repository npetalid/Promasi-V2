/**
 *
 */
package org.promasi.server.core;

import java.net.ProtocolException;


/**
 * @author m1cRo
 *
 */
public interface IProtocolState
{
	public void OnReceive(ProMaSiClient client,String recData)throws ProtocolException;
}
