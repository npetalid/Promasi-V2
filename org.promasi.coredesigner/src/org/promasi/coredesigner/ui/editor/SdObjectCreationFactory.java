package org.promasi.coredesigner.ui.editor;

import org.eclipse.gef.requests.CreationFactory;

import org.promasi.coredesigner.model.SdCalculate;
import org.promasi.coredesigner.model.SdFlow;
import org.promasi.coredesigner.model.SdInput;
import org.promasi.coredesigner.model.SdLookup;
import org.promasi.coredesigner.model.SdOutput;
import org.promasi.coredesigner.model.SdStock;
import org.promasi.coredesigner.model.SdVariable;
/**
 * 
 * @author antoxron
 *
 */
public class SdObjectCreationFactory  implements CreationFactory{

	private Class<?> template;
	
	public SdObjectCreationFactory( Class<?> t ) { 
		this.template = t; 
	}
	@Override
	public Object getNewObject( ) {
		if ( template == null ) {
			return null;
		}
		if ( template == SdCalculate.class ) {
			SdCalculate sdCalculate = new SdCalculate();
			sdCalculate.setName( "Unknown" );
			return sdCalculate;
		}
		if ( template == SdLookup.class ) {
			SdLookup sdLookup = new SdLookup();
			sdLookup.setName( "Unknown" );
			return sdLookup;
		}
		if ( template == SdFlow.class ) {
			SdFlow sdFlow = new SdFlow();
			sdFlow.setName( "Unknown" );
			return sdFlow;
		}
		if ( template == SdInput.class ) {
			SdInput sdInput = new SdInput();
			sdInput.setName( "Unknown" );
			return sdInput;
		}
		if ( template == SdOutput.class ) {
			SdOutput sdOutput = new SdOutput();
			sdOutput.setName( "Unknown" );
			return sdOutput;
		}
		if ( template == SdStock.class ) {
			SdStock sdStock = new SdStock();
			sdStock.setName( "Unknown" );
			return sdStock;
		}
		if ( template == SdVariable.class ) {
			SdVariable sdVariable = new SdVariable();
			sdVariable.setName( "Unknown" );
			return sdVariable;
		}
		return null;
	}
	@Override
	public Object getObjectType( ) {
		// TODO Auto-generated method stub
		return template;
	}
}