package org.promasi.coredesigner.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
/**
 * 
 * Represents the visual representation of Model SdObject
 * @author antoxron
 *
 */
public class SdModelFigure extends Figure {

	/**
	 * 
	 */
	private Label _name = new Label(); 
	/**
	 * 
	 */
	private XYLayout _layout;
	
	public SdModelFigure( ) {
		_layout = new XYLayout(); 
		setLayoutManager( _layout ); 
		_name.setForegroundColor( ColorConstants.lightBlue ); 
		add( _name ); 
		setConstraint( _name, new Rectangle( 5, 5, -1, -1 ) ); 
		setBorder( new LineBorder( 1 ) );
	}
	/**
	 * 
	 * @param rectangle
	 */
	public void setLayout( Rectangle rectangle ) { 
		setBounds( rectangle ); 
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
	 * @return name
	 */
	public String getName( ) {
		return _name.getText();
	}
}
