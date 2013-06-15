/**
 * 
 */
package org.promasi.game.project;

import org.promasi.game.GameException;


/**
 * @author m1cRo
 * Represent the bridge between different
 * instances of {@link ISimulator}. Needed in order
 * to pass information between Sd systems.
 */
public class SimulatorBridge 
{	
	private ISimulator _inputSimulator;
	
	private ISimulator _outputSimulator;
	
	private String _inputId;
	
	private String _outputId;
	
	/**
	 * 
	 * @param outputSdObjectId
	 * @param outputSimulator
	 * @param inputSdObjectId
	 * @param inputSimulator
	 */
	public SimulatorBridge(String outputId, ISimulator outputSimulator, String inputId, ISimulator inputSimulator)throws GameException{
		if(outputId==null){
			throw new GameException("Wrong argument outputSdObjectId==null");
		}
		
		if(inputId==null){
			throw new GameException("Wrong argument inputSdObjectId==null");
		}
		
		if(outputSimulator==null){
			throw new GameException("Wrong argument outputSdSystem==null");
		}
		
		if(inputSimulator==null){
			throw new GameException("Wrong argument inputSdSystem==null");
		}
		
		if( inputSimulator == outputSimulator ){
			throw new GameException("Wrong arguments inputSdSystem and outputSdSystem are the same");
		}
		
		if(!inputSimulator.hasInput(inputId) ){
			throw new GameException("Wrong arguments input named '"+inputId+"' does not exist in inputSdSystem");
		}
		
		if(!outputSimulator.hasOutput(outputId) ){
			throw new GameException("Wrong arguments input named '" + outputId+ "' does not exist in outputSdSystem");
		}
		
		_inputId = inputId;
		_outputId = outputId;
		_inputSimulator=inputSimulator;
		_inputSimulator=outputSimulator;
	}
	
	/**
	 * 
	 */
	public boolean executeStep(){
		boolean result = false;
		try{
			result = _inputSimulator.setInput( _inputId, _outputSimulator.getValue(_outputId) );
		}catch( Exception ex ){
			ex.printStackTrace();
		}
		
		return result;
	}
}
