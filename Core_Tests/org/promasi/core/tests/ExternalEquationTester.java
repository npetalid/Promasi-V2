package org.promasi.core.tests;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.promasi.communication.Communicator;
import org.promasi.communication.IMessageReceiver;
import org.promasi.core.ISdObject;
import org.promasi.core.equations.ExternalEquation;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.core.sdobjects.VariableSdObject;
import org.promasi.utilities.TestUtil;


/**
 * Tests the {@link ExternalEquation} class.
 * 
 * @author eddiefullmetal
 * 
 */
public class ExternalEquationTester
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
     * Tests if the {@link ExternalEquation} works with correct setup.
     */
    @Test
    public void testSuccess ( )
    {
        final Double valueOfX = 5.5;
        // Register the main IMessageReceiver.
        Communicator.getInstance( ).setMainReceiver( new IMessageReceiver( )
        {

            @Override
            public void valueSent ( String sdObjectKey, Double value )
            {
            }

            @Override
            public Double valueRequested ( String sdObjectKey )
            {
                return valueOfX;
            }

            @Override
            public void eventRaised ( String sdObjectKey, String eventName )
            {
            }
        } );

        AbstractSdObject x = new VariableSdObject( "x" );
        x.setEquation( new ExternalEquation( x ) );
        x.calculateValue( );
        Assert.assertEquals( valueOfX, x.getValue( ) );
    }

    /**
     * Tests if an {@link ISdObject} with {@link ExternalEquation} calculates
     * its value succesfully in case the main {@link IMessageReceiver} is not
     * registered(it's value should be 0).
     */
    @Test
    public void testNoMainReceiver ( )
    {
        Communicator.getInstance( ).setMainReceiver( null );
        AbstractSdObject x = new VariableSdObject( "x" );
        x.setEquation( new ExternalEquation( x ) );
        x.calculateValue( );
        Assert.assertEquals( new Double( 0.0 ), x.getValue( ) );
    }
}
