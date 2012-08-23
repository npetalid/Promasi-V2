/**
 * 
 */
package org.promasi.sdsystem_ju.sdsystem;

import java.util.Map;
import java.util.TreeMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.promasi.sdsystem.ISdSystem;
import org.promasi.sdsystem.SdSystem;
import org.promasi.sdsystem.SdSystemException;
import org.promasi.sdsystem.sdobject.FlowSdObject;
import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.sdsystem.sdobject.InputSdObject;
import org.promasi.sdsystem.sdobject.StockSdObject;

/**
 * @author alekstheod
 *
 */
public class SdSystemTest{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructor() throws SdSystemException{
		ISdObject inputObject = Mockito.mock(InputSdObject.class);
		ISdObject stockObject = Mockito.mock(StockSdObject.class);
		ISdObject flowObject = Mockito.mock(FlowSdObject.class);
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		@SuppressWarnings("unused")
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Mockito.verify(inputObject).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
	}
	
	@Test(expected = SdSystemException.class)
	public void testConstructorWithNullAsArgument()throws SdSystemException{
		@SuppressWarnings("unused")
		ISdSystem sdSystem = new SdSystem(null);
	}
	
	@Test(expected = SdSystemException.class)
	public void testConstructorWhenOneObjectReturnsFalse() throws SdSystemException{
		ISdObject inputObject = Mockito.mock(InputSdObject.class);
		ISdObject stockObject = Mockito.mock(StockSdObject.class);
		ISdObject flowObject = Mockito.mock(FlowSdObject.class);
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(false);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		@SuppressWarnings("unused")
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Mockito.verify(inputObject).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
	}
	
	@Test
	public void testExecuteStep() throws SdSystemException{
		ISdObject inputObject = Mockito.mock(InputSdObject.class);
		ISdObject stockObject = Mockito.mock(StockSdObject.class);
		ISdObject flowObject = Mockito.mock(FlowSdObject.class);
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Assert.assertTrue(sdSystem.executeStep());
		Mockito.verify(inputObject, Mockito.times(1)).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
		Mockito.verify(stockObject, Mockito.times(2)).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
		Mockito.verify(flowObject, Mockito.times(2)).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
	}
}
