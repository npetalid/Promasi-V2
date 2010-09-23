/**
 * 
 */
package org.promasi.network.protocol.dtos;

import java.io.Serializable;

import org.apache.commons.lang.NullArgumentException;
import org.joda.time.LocalTime;

/**
 * @author m1cRo
 *
 */
public class CompanyDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 
	 */
	public static final String CONST_DEFAULT_COMPANY_NAME="UBM";
	
	
	/**
	 * 
	 */
	public static final String CONST_DEFAULT_COMPANY_DESCRIPTION="Description";
	
	/**
	 * 
	 */
	private String _companyName;
	
	
	/**
	 * 
	 */
	private String _startTime;
	
	
	/**
	 * 
	 */
	private String _endTime;

	
	/**
	 * 
	 */
	private String _description;
	
	
	/**
	 * 
	 */
	public CompanyDto(){
		_companyName=new String(CONST_DEFAULT_COMPANY_NAME);
		_description=new String(CONST_DEFAULT_COMPANY_DESCRIPTION);
		_startTime=new LocalTime().toString();
		_endTime=new LocalTime().toString();
	}
	
	
	
	public CompanyDto(CompanyDto companyDto)throws NullArgumentException,IllegalArgumentException{
		if(companyDto==null){
			throw new NullArgumentException("Wrong argument companyDto==null");
		}
		
		_companyName=companyDto.getCompanyName();
		_startTime=companyDto.getStartTime().toString();
		_endTime=companyDto.getEndTime().toString();
		_description=companyDto.getCompanyDescription();
	}
	
	/**
	 * 
	 * @param companyName
	 * @param startTime
	 * @param endTime
	 * @param description
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public CompanyDto(String companyName,LocalTime startTime,LocalTime endTime,String description)throws NullArgumentException,IllegalArgumentException{
		if(companyName==null){
			throw new NullArgumentException("Wrong argument companyName==null");
		}
		
		if(startTime==null){
			throw new NullArgumentException("Wrong argument startTime==null");
		}
		
		if(endTime==null){
			throw new NullArgumentException("Wrong argument exception endTime==null");
		}
		
		if(description==null){
			throw new NullArgumentException("Wrong argument description==null");
		}
		
		if(startTime.isAfter(endTime)){
			throw new IllegalArgumentException("Wrong argument startTime>endTime");
		}
		
		if(companyName.isEmpty()){
			throw new IllegalArgumentException("Wrong argument startTime>endTime");
		}
		
		if(description.isEmpty()){
			throw new IllegalArgumentException("Wrong argument startTime>endTime");
		}
		
		_companyName=companyName;
		_startTime=startTime.toString();
		_endTime=endTime.toString();
		_description=description;
	}
	
	
	/**
	 * 
	 * @param companyName
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void setCompanyName(String companyName)throws NullArgumentException,IllegalArgumentException{
		if(companyName==null){
			throw new NullArgumentException("Wrong argument companyName==null");
		}
		
		if(companyName.isEmpty()){
			throw new IllegalArgumentException("Wrong argument companyName is empty");
		}
		
		_companyName=companyName;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized String getCompanyName(){
		return _companyName;
	}
	
	
	/**
	 * 
	 * @param companyDescription
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void setCompanyDescription(String companyDescription)throws NullArgumentException,IllegalArgumentException{
		if(companyDescription==null){
			throw new NullArgumentException("Wrong argument companyDescription==null");
		}
		
		if(companyDescription.isEmpty()){
			throw new IllegalArgumentException("Wrong argument companyDescription is empty");
		}
		
		_description=companyDescription;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized String getCompanyDescription(){
		return _description;
	}
	
	
	/**
	 * 
	 * @param startTime
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void setStartTime(String startTime)throws NullArgumentException,IllegalArgumentException{
		if(startTime==null){
			throw new NullArgumentException("Wrong argument startTime==null");
		}
		
		String tempString=new LocalTime(startTime).toString();
		if(startTime.equals(tempString)){
			_startTime=tempString;
		}else{
			throw new IllegalArgumentException("Wrong argument startTime");
		}
	}
	
	
	/**
	 * 
	 * @param endTime
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void setEndTime(String endTime)throws NullArgumentException,IllegalArgumentException{
		if(endTime==null){
			throw new NullArgumentException("Wrong argument startTime==null");
		}
		
		String tempString=new LocalTime(endTime).toString();
		if(endTime.equals(tempString)){
			_endTime=endTime;
		}else{
			throw new IllegalArgumentException("Wrong argument endTime");
		}
	}
	
	/**
	 * 
	 * @param startTime
	 * @throws NullArgumentException
	 */
	public synchronized void setStartTime(LocalTime startTime)throws NullArgumentException{
		if(startTime==null){
			throw new NullArgumentException("Wrong argument startTime==null");
		}
		
		_startTime=startTime.toString();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized String getStartTime(){
		return _startTime;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized LocalTime getStartTimeAsLocalTime(){
		return new LocalTime(_startTime);
	}
	
	
	/**
	 * 
	 * @param endTime
	 * @throws NullArgumentException
	 */
	public synchronized void setEndTime(LocalTime endTime)throws NullArgumentException{
		if(endTime==null){
			throw new NullArgumentException("Wrong argument endTime==null");
		}
		
		_endTime=endTime.toString();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized LocalTime getEndTimeAsLocalTime(){
		return new LocalTime(_endTime);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized String getEndTime(){
		return _endTime;
	}
	
}
