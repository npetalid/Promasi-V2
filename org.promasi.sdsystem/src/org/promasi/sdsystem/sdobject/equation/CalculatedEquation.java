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
import org.promasi.sdsystem.SdSystemException;
import org.promasi.sdsystem.serialization.IEquationMemento;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;

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
	 * Instance of {@link ILogger} interface implementation
	 * needed for logging.
	 */
	private static final ILogger _logger = LoggerFactory.getInstance(CalculatedEquation.class);
	
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
	public CalculatedEquation(final String equationString)throws SdSystemException
	{
		if(equationString==null){
			throw new SdSystemException("Wrong argument equationString==null");
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
				public void setCurNumberOfParameters(int arg0) {}
	        });

	  _jep.setAllowUndeclared( true );
	  Node topNode = _jep.parseExpression( equationString );
	  if(topNode==null)
	  {
		  _logger.error("Jep parseExpression failed for equation string : '" + equationString + "'"); 
	      throw new IllegalArgumentException("Wrong argument equationString");
	  }
	        
	  _symbolTable = _jep.getSymbolTable( );
	  _equationString=equationString;
	}

	@Override
	public Double calculateEquation(Map<String, Double> systemValues)throws SdSystemException {
		if(systemValues==null){
			_logger.error("Wrong argument systemValues==null"); 
			throw new SdSystemException("Wrong argument systemValues==null");
		}
		
		for(Map.Entry<String, Double> entry : systemValues.entrySet()){
			if(entry.getKey()==null || entry.getValue()==null){
				_logger.error("Wrong argument systemValues contains null"); 
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
						_logger.error("Calculation failed value for object named " + var.getName() + " not found in equation : '" + _equationString +"'"); 
						throw new SdSystemException("Calculation failed value for object named " + var.getName() + " not found");
					}
				}		
			}else{
				_logger.error("Calculation error JEP failed for equation : '" + _equationString +"'");
				throw new SdSystemException("Calculation error JEP failed");
			}
		}
		
		Double value=_jep.getValue();
		if(value.isNaN()){
			return 0.0;
		}
		
		return value;
	}

	@Override
	public IEquationMemento getMemento() {
		return new CalculatedEquationMemento(this);
	}
}
