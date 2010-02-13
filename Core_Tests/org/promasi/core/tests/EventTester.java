package org.promasi.core.tests;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.promasi.communication.Communicator;
import org.promasi.communication.IMessageReceiver;
import org.promasi.core.Event;
import org.promasi.core.ISdObject;
import org.promasi.core.equations.CalculatedEquation;
import org.promasi.core.equations.ConstantEquation;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.core.sdobjects.VariableSdObject;
import org.promasi.utilities.TestUtil;


/**
 * Tests the {@link Event} class.
 * 
 * @author eddiefullmetal
 * 
 */
public class EventTester
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
     * Tests if the event is raised successfully.
     */
    @Test
    public void testSuccessfull ( )
    {
        TestReceiver receiver = new TestReceiver( );
        Communicator.getInstance( ).setMainReceiver( receiver );

        final Double valueOfX = 5.0;
        AbstractSdObject x = new VariableSdObject( "x" );
        x.setEquation( new ConstantEquation( valueOfX ) );
        x.addEvent( new Event( "eventName", new CalculatedEquation( x, "if(x==5,1,0)" ), x ) );
        x.calculateValue( );

        Assert.assertEquals( "x", receiver.getSdObjectKey( ) );
        Assert.assertEquals( "eventName", receiver.getEventName( ) );
    }

    /**
     * This receiver is used to test if an {@link Event} is successfully raised.
     * 
     * @author eddiefullmetal
     * 
     */
    private class TestReceiver
            implements IMessageReceiver
    {

        /**
         * The name of the {@link Event} that was last raised.
         */
        private String _eventName;

        /**
         * The key of the {@link ISdObject} that last raised the event.
         */
        private String _sdObjectKey;

        @Override
        public void eventRaised ( String sdObjectKey, String eventName )
        {
            _eventName = eventName;
            _sdObjectKey = sdObjectKey;
        }

        @Override
        public Double valueRequested ( String sdObjectKey )
        {
            throw new IllegalStateException( "Method should not be called" );
        }

        @Override
        public void valueSent ( String sdObjectKey, Double value )
        {
            throw new IllegalStateException( "Method should not be called" );
        }

        /**
         * Gets the {@link #_eventName}.
         * 
         * @return The {@link #_eventName}.
         */
        public String getEventName ( )
        {
            return _eventName;
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
    }
}
