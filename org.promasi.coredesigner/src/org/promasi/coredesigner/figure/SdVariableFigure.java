package org.promasi.coredesigner.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
/**
 * Represents the visual representation of Variable SdObject
 * 
 * @author antoxron
 *
 */
public class SdVariableFigure extends Figure{

	/**
	 * 
	 */
	private TextFlow _name = new TextFlow();
	
	
	public SdVariableFigure( ) {

		ToolbarLayout layout = new ToolbarLayout(); 
		setLayoutManager( layout ); 
		ParagraphTextLayout textLayout = new ParagraphTextLayout( _name, 
				ParagraphTextLayout.WORD_WRAP_SOFT );
		_name.setLayoutManager( textLayout );
		_name.setForegroundColor( ColorConstants.black ); 
		FlowPage flowPage = new FlowPage();
		flowPage.add( _name );
		add( flowPage, ToolbarLayout.ALIGN_CENTER ); 
		setForegroundColor( ColorConstants.white ); 
		setBackgroundColor( ColorConstants.white ); 
		setBorder( new LineBorder( 1 ) );
		setOpaque( true ); 
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
