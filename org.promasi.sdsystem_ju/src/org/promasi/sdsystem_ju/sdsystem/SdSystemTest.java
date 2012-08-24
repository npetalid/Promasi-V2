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
import org.promasi.sdsystem.sdobject.OutputSdObject;
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
		ISdObject outputObject = Mockito.mock(OutputSdObject.class);
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		sdObjects.put("output", outputObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(outputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		@SuppressWarnings("unused")
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Mockito.verify(inputObject).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
		Mockito.verify(stockObject).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
		Mockito.verify(flowObject).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
		Mockito.verify(outputObject).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
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
		ISdObject outputObject = Mockito.mock(OutputSdObject.class);
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		sdObjects.put("output", outputObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(false);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(outputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		@SuppressWarnings("unused")
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Mockito.verify(inputObject).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
	}
	
	@Test
	public void testExecuteStep() throws SdSystemException{
		ISdObject inputObject = Mockito.mock(InputSdObject.class);
		ISdObject stockObject = Mockito.mock(StockSdObject.class);
		ISdObject flowObject = Mockito.mock(FlowSdObject.class);
		ISdObject outputObject = Mockito.mock(OutputSdObject.class);
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		sdObjects.put("output", outputObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(outputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Assert.assertTrue(sdSystem.executeStep());
		Mockito.verify(inputObject, Mockito.times(1)).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
		Mockito.verify(stockObject, Mockito.times(2)).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
		Mockito.verify(flowObject, Mockito.times(2)).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
		Mockito.verify(outputObject, Mockito.times(2)).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
	}
	
	@Test
	public void testGetValue() throws SdSystemException{
		ISdObject inputObject = Mockito.mock(InputSdObject.class);
		ISdObject stockObject = Mockito.mock(StockSdObject.class);
		ISdObject flowObject = Mockito.mock(FlowSdObject.class);
		ISdObject outputObject = Mockito.mock(OutputSdObject.class);
		
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		sdObjects.put("output", outputObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(outputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		
		Mockito.when(inputObject.getValue()).thenReturn(0.5);
		Mockito.when(stockObject.getValue()).thenReturn(0.3);
		Mockito.when(flowObject.getValue()).thenReturn(0.6);
		Mockito.when(outputObject.getValue()).thenReturn(0.7);
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Assert.assertEquals("Get value failed", new Double(0.5), sdSystem.getValue("input"));
		Assert.assertEquals("Get value failed", new Double(0.3), sdSystem.getValue("stock"));
		Assert.assertEquals("Get value failed", new Double(0.6), sdSystem.getValue("flow"));
		Assert.assertEquals("Get value failed", new Double(0.7), sdSystem.getValue("output"));
		Mockito.verify(inputObject).getValue();
		Mockito.verify(stockObject).getValue();
		Mockito.verify(flowObject).getValue();
		Mockito.verify(outputObject).getValue();
	}
	
	@Test
	public void testSetInput() throws SdSystemException{
		InputSdObject inputObject = Mockito.mock(InputSdObject.class);
		ISdObject stockObject = Mockito.mock(StockSdObject.class);
		ISdObject flowObject = Mockito.mock(FlowSdObject.class);
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(inputObject.setValue(0.5)).thenReturn(true);
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Assert.assertTrue( sdSystem.setInput("input", 0.5) );
		Mockito.verify(inputObject).setValue(0.5);
	}
	
	@Test
	public void testSetInputOnNotInputObject() throws SdSystemException{
		InputSdObject inputObject = Mockito.mock(InputSdObject.class);
		ISdObject stockObject = Mockito.mock(StockSdObject.class);
		ISdObject flowObject = Mockito.mock(FlowSdObject.class);
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(inputObject.setValue(0.5)).thenReturn(true);
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Assert.assertFalse( sdSystem.setInput("stock", 0.5) );
		Mockito.verify(inputObject).executeStep(Mockito.anyMapOf(String.class, ISdObject.class));
		Mockito.verifyNoMoreInteractions(inputObject);
	}
	
	@Test
	public void testGetSystemValues() throws SdSystemException{
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
		
		Mockito.when(inputObject.getValue()).thenReturn(0.5);
		Mockito.when(stockObject.getValue()).thenReturn(0.3);
		Mockito.when(flowObject.getValue()).thenReturn(0.6);
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Map<String, Double> values = sdSystem.getSystemValues();
		Mockito.verify(inputObject).getValue();
		Mockito.verify(stockObject).getValue();
		Mockito.verify(flowObject).getValue();
		Assert.assertEquals(new Double(0.5), values.get("input"));
		Assert.assertEquals(new Double(0.3), values.get("stock"));
		Assert.assertEquals(new Double(0.6), values.get("flow"));
	}
	
	@Test
	public void testHasInput() throws SdSystemException{
		ISdObject inputObject = Mockito.mock(InputSdObject.class);
		ISdObject stockObject = Mockito.mock(StockSdObject.class);
		ISdObject flowObject = Mockito.mock(FlowSdObject.class);
		ISdObject outputObject = Mockito.mock(OutputSdObject.class);
		
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		sdObjects.put("output", outputObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(outputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		
		Mockito.when(inputObject.getValue()).thenReturn(0.5);
		Mockito.when(stockObject.getValue()).thenReturn(0.3);
		Mockito.when(flowObject.getValue()).thenReturn(0.6);
		Mockito.when(outputObject.getValue()).thenReturn(0.7);
		ISdSystem sdSystem = new SdSystem(sdObjects);
		Assert.assertTrue(sdSystem.hasInput("input"));
		Assert.assertFalse(sdSystem.hasInput("flow"));
		Assert.assertFalse(sdSystem.hasInput("stock"));
		Assert.assertFalse(sdSystem.hasInput("output"));
	}
	
	@Test
	public void testHasOutput() throws SdSystemException{
		ISdObject inputObject = Mockito.mock(InputSdObject.class);
		ISdObject stockObject = Mockito.mock(StockSdObject.class);
		ISdObject flowObject = Mockito.mock(FlowSdObject.class);
		ISdObject outputObject = Mockito.mock(OutputSdObject.class);
		
		Map< String, ISdObject > sdObjects = new TreeMap<>();
		sdObjects.put("input", inputObject);
		sdObjects.put("stock", stockObject);
		sdObjects.put("flow", flowObject);
		sdObjects.put("output", outputObject);
		
		Mockito.when(inputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(stockObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(flowObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		Mockito.when(outputObject.executeStep(Mockito.anyMapOf(String.class, ISdObject.class))).thenReturn(true);
		
		Mockito.when(inputObject.getValue()).thenReturn(0.5);
		Mockito.when(stockObject.getValue()).thenReturn(0.3);
		Mockito.when(flowObject.getValue()).thenReturn(0.6);
		Mockito.when(outputObject.getValue()).thenReturn(0.7);
		ISdSystem sdSystem = new SdSystem(sdObjects);
	}
}
