package org.promasi.core.tests;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.promasi.core.equations.CalculatedEquation;
import org.promasi.core.equations.ConstantEquation;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.core.sdobjects.VariableSdObject;
import org.promasi.utilities.TestUtil;


/**
 * Tests the {@link CalculatedEquation} class.
 * 
 * @author eddiefullmetal
 * 
 */
public class CalculatedEquationTester
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
     * Tests if basic function x*y works.
     */
    @Test
    public void testBasicFunction ( )
    {
        // Test the x*y function.
        final double valueOfX = 5.0d;
        final double valueOfY = 4.0d;
        final double expectedResult = 20.0d;

        AbstractSdObject x = new VariableSdObject( "x" );
        AbstractSdObject y = new VariableSdObject( "y" );
        x.setEquation( new ConstantEquation( valueOfX ) );
        y.setEquation( new ConstantEquation( valueOfY ) );
        x.calculateValue( );
        y.calculateValue( );

        AbstractSdObject variableObject = new VariableSdObject( "main" );
        variableObject.addDependency( x );
        variableObject.addDependency( y );
        variableObject.setEquation( new CalculatedEquation( variableObject, "x*y" ) );

        variableObject.calculateValue( );
        Assert.assertEquals( expectedResult, variableObject.getValue( ) );
    }

    /**
     * Tests if the function x*y, with the x not being a dependency, fails( the
     * {@link AbstractSdObject} will have a 0 value).
     */
    @Test
    public void testMissingDependency ( )
    {
        // Test the x*y function.
        final double valueOfY = 4.0d;
        AbstractSdObject y = new VariableSdObject( "y" );
        y.setEquation( new ConstantEquation( valueOfY ) );
        y.calculateValue( );

        AbstractSdObject variableObject = new VariableSdObject( "main" );
        variableObject.addDependency( y );
        variableObject.setEquation( new CalculatedEquation( variableObject, "x*y" ) );

        variableObject.calculateValue( );

        Assert.assertEquals( 0.0d, variableObject.getValue( ) );
    }

    /**
     * Tests {@link CalculatedEquation#calculateValue()} fails if an invalid
     * equation is given( the {@link AbstractSdObject} will have a 0 value).
     */
    @Test
    public void testInvalidEquationString ( )
    {
        // Test the x*y function.
        final double valueOfY = 4.0d;
        AbstractSdObject y = new VariableSdObject( "y" );
        y.setEquation( new ConstantEquation( valueOfY ) );
        y.calculateValue( );

        AbstractSdObject variableObject = new VariableSdObject( "main" );
        variableObject.addDependency( y );
        variableObject.setEquation( new CalculatedEquation( variableObject, "**..!!@@y" ) );

        variableObject.calculateValue( );
        Assert.assertEquals( 0.0d, variableObject.getValue( ) );
    }

    /**
     * Tests if the {@link CalculatedEquation} can calculated equations like x+2
     * when x is the context of the equation.
     */
    @Test
    public void testContextInEquation ( )
    {
        AbstractSdObject x = new VariableSdObject( "x" );
        x.setEquation( new CalculatedEquation( x, "x+2" ) );

        final int numberOfSteps = 5;
        final Double[] expectedValuesOfX = new Double[] { 2.0, 4.0, 6.0, 8.0, 10.0 };
        for ( int i = 0; i < numberOfSteps; i++ )
        {
            x.calculateValue( );
            Assert.assertEquals( expectedValuesOfX[i], x.getValue( ) );
        }
    }
}
