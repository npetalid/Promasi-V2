package org.promasi.coredesigner.model.builder.model;

public class XmlBridge {
	
	
	private String _inputObjectName;
	private String _inputModelName;
	private String _outputObjectName;
	private String _outputModelName;
	
	
	
	public XmlBridge() {
		
	}
	
	
	public void setInputObjectName(String inputObjectName) {
		_inputObjectName = inputObjectName;
	}
	public String getInputObjectName() {
		return _inputObjectName;
	}
	public void setInputModelName(String inputModelName) {
		_inputModelName = inputModelName;
	}
	public String getInputModelName() {
		return _inputModelName;
	}
	
	public void setOutputObjectName(String outputObjectName) {
		_outputObjectName = outputObjectName;
	}
	public String getOutputObjectName() {
		return _outputObjectName;
	}
	public void setOutputModelName(String outputModelName) {
		_outputModelName = outputModelName;
	}
	public String getOutputModelName() {
		return _outputModelName;
	}

}
