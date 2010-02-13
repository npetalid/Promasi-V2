package org.promasi.core.tests;


import java.util.TreeMap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.promasi.core.equations.ConstantEquation;
import org.promasi.core.equations.LookupEquation;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.core.sdobjects.VariableSdObject;
import org.promasi.utilities.TestUtil;


/**
 * Tests the {@link LookupEquation} class.
 * 
 * @author eddiefullmetal
 * 
 */
public class LookupEquationTester
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
     * Tests a single graph like (0,0)(1,1)(2,2)(3,3).
     */
    @Test
    public void testSimpleGraph ( )
    {
        // Create the dependent object.
        final double valueOfX = 2.5d;
        AbstractSdObject x = new VariableSdObject( "x" );
        x.setEquation( new ConstantEquation( valueOfX ) );
        x.calculateValue( );

        // Create the graph
        final int lengthOfGraph = 3;
        TreeMap<Double, Double> xyPoints = new TreeMap<Double, Double>( );
        for ( int i = 0; i <= lengthOfGraph; i++ )
        {
            xyPoints.put( (double) i, (double) i );
        }

        final double expectedResult = 2.5d;
        AbstractSdObject variableSdObject = new VariableSdObject( "main" );
        variableSdObject.setEquation( new LookupEquation( xyPoints, x ) );
        variableSdObject.calculateValue( );
        Assert.assertEquals( expectedResult, variableSdObject.getValue( ) );
    }

    /**
     * Tests how the {@link LookupEquation} will react if empty points are
     * given.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testEmptyPoints ( )
    {
        // Create the dependent object.
        final double valueOfX = 2.5d;
        AbstractSdObject x = new VariableSdObject( "x" );
        x.setEquation( new ConstantEquation( valueOfX ) );
        x.calculateValue( );

        // Create the graph
        TreeMap<Double, Double> xyPoints = new TreeMap<Double, Double>( );

        final double expectedResult = 2.5d;
        AbstractSdObject variableSdObject = new VariableSdObject( "main" );
        variableSdObject.setEquation( new LookupEquation( xyPoints, x ) );
        variableSdObject.calculateValue( );
        Assert.assertEquals( expectedResult, variableSdObject.getValue( ) );
    }

}
