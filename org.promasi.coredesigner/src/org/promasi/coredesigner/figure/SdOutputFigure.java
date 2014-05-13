package org.promasi.coredesigner.figure;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;

import org.eclipse.swt.graphics.Image;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.promasi.coredesigner.resources.IImageKeys;
/**
 * Represents the visual representation of Output SdObject
 * 
 * @author antoxron
 *
 */
public class SdOutputFigure extends ImageFigure{
	
	/**
	 * 
	 */
	private TextFlow _name = new TextFlow();
	
	
	@SuppressWarnings("deprecation")
	public SdOutputFigure( ) { 
		
		FlowLayout layout = new FlowLayout(); 
		layout.setMajorSpacing( MAX_FLAG );
		setLayoutManager( layout );
		ParagraphTextLayout textLayout = new ParagraphTextLayout( _name, ParagraphTextLayout.WORD_WRAP_SOFT );
		_name.setLayoutManager( textLayout );
		FlowPage flowPage = new FlowPage();
		flowPage.add( _name );
		add( flowPage , FlowLayout.VERTICAL );
		Image image = AbstractUIPlugin.imageDescriptorFromPlugin( "org.promasi.coredesigner",
				IImageKeys.OUTPUT_ICON ).createImage();
		setImage( image );
	} 
	/**
	 * 
	 * @param the name of Visual SdObject
	 */
	public void setName( String name ) { 
		_name.setText( name );
	} 
	/**
	 * 
	 * @param rectangle
	 */
	public void setLayout( Rectangle rectangle ) { 
		getParent().setConstraint( this, rectangle ); 
	}
}