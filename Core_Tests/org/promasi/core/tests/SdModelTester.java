package org.promasi.core.tests;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import junit.framework.Assert;

import org.apache.commons.lang.NullArgumentException;
import org.junit.Before;
import org.junit.Test;
import org.promasi.core.Event;
import org.promasi.core.ISdObject;
import org.promasi.core.SdModel;
import org.promasi.core.SdObjectType;
import org.promasi.core.equations.CalculatedEquation;
import org.promasi.core.equations.ConstantEquation;
import org.promasi.core.equations.ExternalEquation;
import org.promasi.core.equations.LookupEquation;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.core.sdobjects.FlowSdObject;
import org.promasi.core.sdobjects.OutputSdObject;
import org.promasi.core.sdobjects.VariableSdObject;
import org.promasi.utilities.TestUtil;


/**
 * Tests the {@link SdModel} class.
 * 
 * @author eddiefullmetal
 * 
 */
public class SdModelTester
{

    /**
     * Setup the logging.
     */
    @Before
    public void setUp ( )
    {
        TestUtil.initializeLogging( );
    }

    /**
     * Tests if the {@link SdModel#getAffectedSdObjects(ISdObject)} works
     * successfully.
     */
    @Test
    public void testAffectedSdObjects ( )
    {
        Vector<ISdObject> sdObjects = new Vector<ISdObject>( );
        final int objectsToGenerate = 100;
        // Create a hundred VariableSdObjects. And add the 2 previous objects as
        // a dependency, so the object var10 has a dependency on var9 and var8
        // so the var8 must affect the var10 and the var9.
        for ( int i = 0; i < objectsToGenerate; i++ )
        {
            VariableSdObject variable = new VariableSdObject( "var" + i );
            if ( i > 1 )
            {
                variable.addDependency( sdObjects.get( i - 1 ) );
                variable.addDependency( sdObjects.get( i - 2 ) );
            }
            sdObjects.add( variable );
        }

        // Create the SdModel
        SdModel model = new SdModel( );
        model.setSdObjects( sdObjects );
        model.initialize( );

        // Test the var8 the var8 must affect the var9 and var10
        ISdObject object = model.getSdObject( "var8" );
        ISdObject var9 = model.getSdObject( "var9" );
        ISdObject var10 = model.getSdObject( "var10" );
        List<ISdObject> affectedSdObjects = model.getAffectedSdObjects( object );
        Assert.assertEquals( 2, affectedSdObjects.size( ) );
        Assert.assertEquals( true, affectedSdObjects.contains( var9 ) );
        Assert.assertEquals( true, affectedSdObjects.contains( var10 ) );

        // Test the var9 the var9 must affect the var10 and var11
        object = model.getSdObject( "var9" );
        ISdObject var11 = model.getSdObject( "var11" );
        affectedSdObjects = model.getAffectedSdObjects( object );
        Assert.assertEquals( 2, affectedSdObjects.size( ) );
        Assert.assertEquals( true, affectedSdObjects.contains( var10 ) );
        Assert.assertEquals( true, affectedSdObjects.contains( var11 ) );
    }

    /**
     * Test if the {@link SdModel} will successfully initialize if a null or
     * empty {@link List} is given.
     * 
     * @throws IllegalStateException
     *             In case the {@link SdModel#setSdObjects(List)} doesn't throw
     *             a {@link NullArgumentException} when a null {@link List} is
     *             passed.
     */
    @Test
    public void testNullOrEmptySdObjectList ( )
            throws IllegalStateException
    {
        // Test with null list.
        SdModel model = new SdModel( );
        List<ISdObject> sdObjects = null;
        try
        {
            model.setSdObjects( sdObjects );
            throw new IllegalStateException( "NullArgumentException was not thrown" );
        }
        catch ( NullArgumentException e )
        {
            // Continue normally this exception must be thrown.
        }

        // Test with empty list.
        model = new SdModel( );
        sdObjects = new Vector<ISdObject>( );
        model.setSdObjects( sdObjects );
        Assert.assertEquals( 0, model.getSize( ) );
    }

    /**
     * Test if an {@link SdModel} is correctly serialized and deserialized using
     * the {@link XMLEncoder}-{@link XMLDecoder}.
     * 
     * @throws FileNotFoundException
     *             In case the created xml file is not found.
     */
    @Test
    public void testXmlSerialization ( )
            throws FileNotFoundException
    {
        final double valueOfY = 4.0d;

        List<ISdObject> sdObjects = new Vector<ISdObject>( );

        // Add a variable with a lookup equation.
        AbstractSdObject x = new VariableSdObject( "x" );
        // Create a graph like (0,0)(1,1)(2,2)(3,3).
        final int lengthOfGraph = 3;
        TreeMap<Double, Double> xyPoints = new TreeMap<Double, Double>( );
        for ( int i = 0; i < lengthOfGraph; i++ )
        {
            xyPoints.put( (double) i, (double) i );
        }
        x.setEquation( new LookupEquation( xyPoints, x ) );
        sdObjects.add( x );

        // Add a variable with a constant equation.
        AbstractSdObject y = new FlowSdObject( "y" );
        y.setEquation( new ConstantEquation( valueOfY ) );
        sdObjects.add( y );

        // Add a variable with an external equation and an event.
        AbstractSdObject z = new VariableSdObject( "z" );
        z.setEquation( new ExternalEquation( z ) );
        z.addEvent( new Event( "raised", new CalculatedEquation( z, "if(z==5.0,1,0)" ), z ) );
        sdObjects.add( z );

        // Add an outputSdObject.
        AbstractSdObject k = new OutputSdObject( "k" );
        k.setEquation( new ExternalEquation( k ) );
        sdObjects.add( k );

        // Add a variable with a calculatedEquation and 2 dependencies.
        AbstractSdObject variableObject = new VariableSdObject( "main" );
        variableObject.addDependency( x );
        variableObject.addDependency( y );
        variableObject.addDependency( z );
        variableObject.addDependency( k );
        variableObject.setEquation( new CalculatedEquation( variableObject, "x*y-z+k" ) );
        sdObjects.add( variableObject );

        // Assign the objects to the model and initialize it.
        SdModel sourceModel = new SdModel( );
        sourceModel.setSdObjects( sdObjects );
        sourceModel.initialize( );

        // Serialize the model
        File xmlFile = new File( "SdModel.xml" );
        XMLEncoder encoder = new XMLEncoder( new FileOutputStream( xmlFile ) );
        encoder.writeObject( sourceModel );
        encoder.close( );

        // Deserialize the model
        XMLDecoder decoder = new XMLDecoder( new FileInputStream( xmlFile ) );
        SdModel newModel = (SdModel) decoder.readObject( );
        decoder.close( );
        xmlFile.delete( );
        // The sdObjects are already set from the XMLDecoder just initialize the
        // model.
        newModel.initialize( );

        final int expectedSdObjects = 5;
        final int numberOfVariables = 3;
        final int numberOfFlows = 1;
        final int numberOfOutputs = 1;

        // Make sure that the correct number of objects was loaded.
        Assert.assertEquals( expectedSdObjects, newModel.getSdObjects( ).size( ) );

        // Make sure that the model initialized the _keySdObjects correctly.
        Assert.assertTrue( newModel.getSdObject( "x" ) != null );
        Assert.assertTrue( newModel.getSdObject( "y" ) != null );
        Assert.assertTrue( newModel.getSdObject( "z" ) != null );
        Assert.assertTrue( newModel.getSdObject( "k" ) != null );
        Assert.assertTrue( newModel.getSdObject( "main" ) != null );

        // Make sure that the model initialized the _categories correctly.
        Assert.assertEquals( numberOfVariables, newModel.getSdObjectsByType( SdObjectType.Variable ).size( ) );
        Assert.assertEquals( numberOfFlows, newModel.getSdObjectsByType( SdObjectType.Flow ).size( ) );
        Assert.assertEquals( numberOfOutputs, newModel.getSdObjectsByType( SdObjectType.Output ).size( ) );

        // Make sure that the dependencies where parsed successfully.
        AbstractSdObject newX = (AbstractSdObject) newModel.getSdObject( "x" );
        AbstractSdObject newY = (AbstractSdObject) newModel.getSdObject( "y" );
        AbstractSdObject newZ = (AbstractSdObject) newModel.getSdObject( "z" );
        AbstractSdObject newK = (AbstractSdObject) newModel.getSdObject( "k" );
        AbstractSdObject newMain = (AbstractSdObject) newModel.getSdObject( "main" );

        Assert.assertTrue( newMain.getDependencies( ).contains( newX ) );
        Assert.assertTrue( newMain.getDependencies( ).contains( newY ) );
        Assert.assertTrue( newMain.getDependencies( ).contains( newZ ) );
        Assert.assertTrue( newMain.getDependencies( ).contains( newK ) );

        // Make sure that all the equation where parsed successfully
        // Check the ConstantEquation
        ConstantEquation newConstantEquation = (ConstantEquation) newY.getEquation( );
        Assert.assertEquals( valueOfY, newConstantEquation.getValue( ) );
        // Check the calculatedEquation
        CalculatedEquation newCalculatedEquation = (CalculatedEquation) newMain.getEquation( );
        Assert.assertEquals( "x*y-z+k", newCalculatedEquation.getEquationString( ) );
        Assert.assertEquals( newMain, newCalculatedEquation.getContext( ) );
        // Check the lookupEquation
        LookupEquation newLookupEquation = (LookupEquation) newX.getEquation( );
        Assert.assertEquals( lengthOfGraph, newLookupEquation.getXValues( ).length );
        Assert.assertEquals( lengthOfGraph, newLookupEquation.getXValues( ).length );
        Assert.assertEquals( newX, newLookupEquation.getDependentObject( ) );
        // Make sure that the x[1] value matches with the y[1] value since the
        // graph was like (0,0)(1,1)(2,2)(3,3).
        for ( int i = 0; i < lengthOfGraph; i++ )
        {
            Assert.assertEquals( newLookupEquation.getXValues( )[i], newLookupEquation.getYValues( )[i] );
            Assert.assertEquals( newLookupEquation.getXValues( )[i], (double) i );
        }
        // Check the external equations
        ExternalEquation newZExternalEquation = (ExternalEquation) newZ.getEquation( );
        Assert.assertEquals( newZ, newZExternalEquation.getContext( ) );

        ExternalEquation newKExternalEquation = (ExternalEquation) newK.getEquation( );
        Assert.assertEquals( newK, newKExternalEquation.getContext( ) );

        // Check the event.
        final int numberOfEvents = 1;
        Assert.assertEquals( numberOfEvents, newZ.getEvents( ).size( ) );
        Event newZEvent = newZ.getEvents( ).get( 0 );
        Assert.assertEquals( "raised", newZEvent.getName( ) );
        Assert.assertEquals( newZ, newZEvent.getContext( ) );
        CalculatedEquation eventZEquation = (CalculatedEquation) newZEvent.getEquation( );
        Assert.assertEquals( "if(z==5.0,1,0)", eventZEquation.getEquationString( ) );
        Assert.assertEquals( newZ, eventZEquation.getContext( ) );
    }
}
