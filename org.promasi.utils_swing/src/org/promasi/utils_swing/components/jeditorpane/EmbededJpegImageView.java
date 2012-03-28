package org.promasi.utils_swing.components.jeditorpane;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

import org.apache.commons.codec.binary.Base64;
import org.promasi.utils_swing.components.ComponentException;

/**
 * 
 * @author alekstheod
 *
 */
public class EmbededJpegImageView extends ImageView {

	/**
	 * 
	 */
	public static final String CONST_IMG_TAG_NAME = "img";
	
	/**
	 * 
	 */
	public static final String CONST_SRC_ATTR_NAME = "src";
	
	/**
	 * 
	 */
	public static final String CONST_EMBEDED_IMG_DATA_HDR = "data:image/jpeg;base64";
	
	/**
	 * 
	 */
	public static final String CONST_EMBEDED_IMAGE_URL = "http://new.gr/image.jpeg";
	
	/**
	 * 
	 */
	private Image _image;
	
	/**
	 * 
	 * @param element
	 * @throws ComponentException 
	 */
	public EmbededJpegImageView(Element element) throws ComponentException {
		super(element);
		
		if( element == null ){
			throw new ComponentException("Invalid argument element == null");
		}
		
		AttributeSet tagAttributes =  element.getAttributes();
	    Enumeration<?> attrEnum = tagAttributes.getAttributeNames();
	    while (attrEnum.hasMoreElements()) {
	      Object name = attrEnum.nextElement();
	      if( CONST_SRC_ATTR_NAME.equals(name.toString()) ){
		      Object value = tagAttributes.getAttribute(name);
		      String header = value.toString().substring(0, CONST_EMBEDED_IMG_DATA_HDR.length() );
		      if(header.equals(CONST_EMBEDED_IMG_DATA_HDR) ){
		    	  String data = value.toString().substring( header.length() + 1 );
		    	  byte[] imgBuffer=Base64.decodeBase64(data.getBytes());
		    	  InputStream in = new ByteArrayInputStream(imgBuffer);
		    	  try {
		    		  _image = ImageIO.read(in);
		    	  } catch (IOException e) {
		    		  throw new ComponentException("Wrong argument element");
		    	  }
		      }
	      }
	    }
	    
	    if( _image == null ){
	    	throw new ComponentException("Wrong argument element");
	    }
	}
	
	/**
	 * 
	 */
	public Image getImage(){
		return _image;
	}
	
	/**
	 * 
	 */
	public URL	getImageURL(){
		try {
			return new URL( CONST_EMBEDED_IMAGE_URL );
		} catch (MalformedURLException e) {
			return null;
		}
	}
}
