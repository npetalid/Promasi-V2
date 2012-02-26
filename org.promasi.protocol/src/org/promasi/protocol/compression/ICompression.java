/**
 * 
 */
package org.promasi.protocol.compression;

/**
 * @author alekstheod
 *
 */
public interface ICompression {
    /**
     * 
     * @param message
     * @return
     * @throws NullArgumentException
     */
    byte[] compress ( byte[] buffer ) throws CompressionException;
    
    /**
     * 
     * @param message
     * @return
     * @throws NullArgumentException
     */
    byte[] deCompress ( byte[] buffer )throws CompressionException;

}
