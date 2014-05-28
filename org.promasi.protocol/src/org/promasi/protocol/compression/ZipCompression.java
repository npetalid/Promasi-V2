/**
 * 
 */
package org.promasi.protocol.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author mits, alekstheod
 * Represent the Zip compression algorithm.
 */
public class ZipCompression implements ICompression{

	@Override
	public byte[] compress(byte[] buffer) throws CompressionException {
        if(buffer==null){
	            throw new CompressionException("Wrong argument buffer==null");
	    }
	    
	    ByteArrayOutputStream outstream = new ByteArrayOutputStream( );
	    GZIPOutputStream gzipOut;
	    try{
	            gzipOut = new GZIPOutputStream( outstream );
	            gzipOut.write( buffer );
	            gzipOut.finish( );
	    }catch ( IOException e ){
	    	throw new CompressionException(e);
	    }                       
	    
	    return outstream.toByteArray();

	}

	@Override
	public byte[] deCompress(byte[] buffer) throws CompressionException {
        if(buffer==null){
	            throw new CompressionException("Wrong argument buffer==null");
	    }
	    
	    ByteArrayInputStream in = new ByteArrayInputStream(buffer);
	    GZIPInputStream gzipIn;
	    ByteArrayOutputStream data=new ByteArrayOutputStream();
	    byte[]uncompressed = new byte[100];
	    int len=100;
	    int recLen=0;
	    try{
            gzipIn = new GZIPInputStream(in);
            while((recLen = gzipIn.read(uncompressed, 0, len)) >0){
                data.write(uncompressed,0, recLen);
            }
	    }catch (IOException e) {
	    	throw new CompressionException(e);
	    }
	    
	    return data.toByteArray();
	}

}
