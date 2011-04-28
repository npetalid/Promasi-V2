/**
 * 
 */
package org.promasi.sdsystem.sdobject.equation;

import java.util.Map;
import java.util.Stack;

import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.SymbolTable;
import org.nfunk.jep.Variable;
import org.nfunk.jep.function.PostfixMathCommandI;
import org.promasi.sdsystem.serialization.ISerializableEquation;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class CalculatedEquation implements IEquation{
	/**
	 * 
	 */
	protected String _equationString;
	
	/**
     * The lower value of a double in order to be considered as 0.
     */
    private static final double CONST_ACTUAL_ZERO = 1.0 * Math.pow( Math.E, -6.0 );
	
    /**
     * 
     */
    private SymbolTable _symbolTable;
    
    /**
     * 
     */
    private JEP _jep;
    
	/**
	 * 
	 * @param equationString
	 */
	public CalculatedEquation(final String equationString)throws NullArgumentException, IllegalArgumentException
	{
		if(equationString==null){
			throw new NullArgumentException("Wrong argument equationString==null");
		}
			
		_jep = new JEP( );
		_jep.addStandardFunctions( );
		_jep.addStandardConstants( );
	    _jep.addFunction( "Max", new PostfixMathCommandI() {

				@Override
				public boolean checkNumberOfParameters(int n) {
					return n == 2;
				}

				@Override
				public int getNumberOfParameters() {
					return 2;
				}

				@Override
				public void run(Stack stack) throws ParseException {
			        double var1 = (Double) stack.pop( );
			        double var2 = (Double) stack.pop( );
			        stack.push( Math.max( var1, var2 ) );
				}

				@Override
				public void setCurNumberOfParameters(int arg0) {
					// TODO Auto-generated method stub
					
				};
	        
	        });

	   _jep.addFunction( "Min", new PostfixMathCommandI(){

				@Override
				public boolean checkNumberOfParameters(int n) {
					return n==2;
				}

				@Override
				public int getNumberOfParameters() {
					return 2;
				}

				@Override
				public void run(Stack stack) throws ParseException {
			        double var1 = (Double) stack.pop( );
			        double var2 = (Double) stack.pop( );
			        stack.push( Math.min( var1, var2 ) );
				}

				@Override
				public void setCurNumberOfParameters(int arg0) {
					// TODO Auto-generated method stub
					
				};
	        
	        });
	        
	  _jep.addFunction( "Zidz", new PostfixMathCommandI(){

				@Override
				public boolean checkNumberOfParameters(int n) {
					return n==2;
				}

				@Override
				public int getNumberOfParameters() {
					return 2;
				}

				@Override
				public void run(Stack stack) throws ParseException {
			        double var2 = (Double) stack.pop( );
			        double var1 = (Double) stack.pop( );

			        double val;
			        if ( var2 < CONST_ACTUAL_ZERO )
			        {
			            val = 0.0;
			        }
			        else
			        {
			            val = var1 / var2;
			        }
			        stack.push( val );
					
				}

				@Override
				public void setCurNumberOfParameters(int arg0) {
					// TODO Auto-generated method stub
					
				}
	        });

	  _jep.setAllowUndeclared( true );
	  Node topNode = _jep.parseExpression( equationString );
	  if(topNode==null)
	  {
	       throw new IllegalArgumentException("Wrong argument equationString");
	  }
	        
	  _symbolTable = _jep.getSymbolTable( );
	  _equationString=equationString;
	}

	@Override
	public Double calculateEquation(Map<String, Double> systemValues)throws NullArgumentException, IllegalArgumentException, CalculationExeption, IllegalArgumentException {
		if(systemValues==null){
			throw new NullArgumentException("Wrong argument systemValues==null");
		}
		
		for(Map.Entry<String, Double> entry : systemValues.entrySet()){
			if(entry.getKey()==null || entry.getValue()==null){
				throw new IllegalArgumentException("Wrong argument systemValues contains null");
			}
		}
		
		for(Object object : _symbolTable.values()){
			if(object instanceof Variable){
				Variable var=(Variable)object;
				if(!var.isConstant()){
					if(systemValues.containsKey(var.getName())){

						Double value= systemValues.get(var.getName());
						if(value==null || value.isNaN()){
							value=0.0;
						}
						
						_jep.setVarValue(var.getName(),value);
					}else{
						throw new IllegalArgumentException("Calculation failed value for object named " + var.getName() + " not found");
					}
				}		
			}else{
				throw new CalculationExeption("Calculation error JEP failed");
			}
		}
		
		Double value=_jep.getValue();
		if(value.isNaN()){
			return 0.0;
		}
		
		return value;
	}

	@Override
	public ISerializableEquation getSerializableEquation()throws SerializationException {
		return new SerializableCalculatedEquation(this);
	}
}
