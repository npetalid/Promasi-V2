package org.promasi.utilities.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.ImageView;

import sun.misc.BASE64Decoder;


public class Base64ImageView extends ImageView {

	public Base64ImageView(Element element) {
		super(element);
	}
	
	@Override
	public Image getImage(){
		if(getImageURL()==null){
			return loadImage();
		}else{
			Image image=super.getImage();
			return image;
		}
	}
    
    
    private Image loadImage() {
    	Image image=null;
        String elementString = (String) getElement().
        getAttributes().getAttribute(HTML.Attribute.SRC);

        if (elementString != null && elementString.length() > 4 && elementString.substring(0, 5).toLowerCase().equals("data:")) {
              int b64index = elementString.indexOf(";base64,");
              if (b64index != -1) {
	              b64index += 8;
	              try {
	            	  String imgString=elementString.substring(b64index);
					  byte[] imgData=new BASE64Decoder().decodeBuffer(imgString);
					  BufferedImage bufferedImg=ImageIO.read(new ByteArrayInputStream(imgData));
					  if(bufferedImg==null){
						  return null;
					  }

					  image = Toolkit.getDefaultToolkit().createImage(bufferedImg.getSource());
					  
					}catch (IOException e) {
						return null;
					}
              }
        }
        
        return image;
    }

}
