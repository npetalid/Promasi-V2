package org.promasi.core.tests;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.promasi.communication.Communicator;
import org.promasi.communication.IMessageReceiver;
import org.promasi.core.ISdObject;
import org.promasi.core.equations.ConstantEquation;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.core.sdobjects.OutputSdObject;
import org.promasi.utilities.TestUtil;


/**
 * Tests the {@link OutputSdObject} class.
 * 
 * @author eddiefullmetal
 * 
 */
public class OutputSdObjectTester
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
     * Tests if the {@link OutputSdObject} works successfully.
     */
    @Test
    public void testSuccess ( )
    {
        // Create the OutputSdObject.
        final Double valueOfX = 5.0;
        AbstractSdObject x = new OutputSdObject( "x" );
        x.setEquation( new ConstantEquation( valueOfX ) );
        // Register the main IMessageReceiver
        TestReceiver receiver = new TestReceiver( );
        Communicator.getInstance( ).setMainReceiver( receiver );

        x.calculateValue( );

        Assert.assertEquals( valueOfX, receiver.getValue( ) );
        Assert.assertEquals( "x", receiver.getSdObjectKey( ) );
    }

    /**
     * 
     * This receiver is used to test if the {@link OutputSdObject} has send its
     * value.
     * 
     * @author eddiefullmetal
     * 
     */
    private class TestReceiver
            implements IMessageReceiver
    {

        /**
         * The key of {@link ISdObject} that last sent a valueSent message.
         */
        private String _sdObjectKey;

        /**
         * The value of {@link ISdObject} that last sent a valueSent message.
         */
        private Double _value;

        @Override
        public void eventRaised ( String sdObjectKey, String eventName )
        {
        }

        @Override
        public Double valueRequested ( String sdObjectKey )
        {
            return null;
        }

        @Override
        public void valueSent ( String sdObjectKey, Double value )
        {
            _sdObjectKey = sdObjectKey;
            _value = value;
        }

        /**
         * Gets the {@link #_sdObjectKey}.
         * 
         * @return The {@link #_sdObjectKey}.
         */
        public String getSdObjectKey ( )
        {
            return _sdObjectKey;
        }

        /**
         * Gets the {@link #_value}.
         * 
         * @return The {@link #_value}.
         */
        public Double getValue ( )
        {
            return _value;
        }

    }
}
