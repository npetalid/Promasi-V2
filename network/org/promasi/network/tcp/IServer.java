/**
 *
 */
package org.promasi.network.tcp;

/**
 * @author m1cRo
 *	IServer interface.
 * 	Interface for server class implementation.
 */
public interface IServer
{
	boolean start(int portNumber);
	boolean stop();
}
