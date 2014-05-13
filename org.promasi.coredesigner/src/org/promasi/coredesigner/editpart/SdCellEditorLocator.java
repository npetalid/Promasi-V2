package org.promasi.coredesigner.editpart;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.tools.CellEditorLocator;

import org.eclipse.jface.viewers.CellEditor;

import org.eclipse.swt.widgets.Text;
import org.promasi.coredesigner.figure.SdModelFigure;

/**
 * This class is used to visually display an input field name
 * 
 * @author antoxron
 *
 */
public class SdCellEditorLocator implements CellEditorLocator  {

	/**
	 * The figure (SdFlowFigure , SdStockFigure etc) that will display the input name
	 */
	private IFigure _figure;
	
	public SdCellEditorLocator( IFigure figure ) {
		_figure = figure;
	}

	@Override
	public void relocate( CellEditor cellEditor ) {

		if ( _figure instanceof SdModelFigure ) {
			Text text = ( Text ) cellEditor.getControl();
			Rectangle rectangle = _figure.getBounds().getCopy();
			_figure.translateToAbsolute( rectangle );
			//to appear in the upper left point
			text.setBounds( rectangle.x, rectangle.y, 100, 50 );
		}
		else {
			Text text = ( Text ) cellEditor.getControl();
			Rectangle rectangle = _figure.getBounds().getCopy();
			_figure.translateToAbsolute( rectangle );
			text.setBounds( rectangle.x, rectangle.y, rectangle.width, rectangle.height );
		}	
	}
}