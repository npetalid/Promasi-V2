package org.promasi.model;


import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.transaction.NotSupportedException;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;
import org.promasi.utilities.jodautils.DurationFieldTypeUtils;


/**
 *
 * The clock of the model layer.
 *
 * @author eddiefullmetal
 *
 */
public final class Clock
        implements Runnable
{

    /**
     * The Current date time of the clock.
     */
    private MutableDateTime _currentDateTime;

    /**
     * The {@link DurationFieldType}. The clock will advance according to this
     * type. exp. If the field type is set to
     * {@link DurationFieldType#minutes()} each second will correspond to a
     * minute. By default it is set to {@link DurationFieldType#minutes()}.
     */
    private DurationFieldType _fieldType;

    /**
     * Indicates if the clock must stop.
     */
    private volatile boolean _mustStop;

    /**
     * This thread advances the clock according to the {@link #_fieldType}.
     */
    private Thread _clockThread;

    /**
     * Object used for locking.
     */
    private Object _lockObject;

    /**
     * All the {@link IClockListener}s.
     */
    private List<IClockListener> _listeners;

    /**
     * The speed of the clock. Basically how milliseconds it will wait before it
     * ticks again. By default it is set to 1000.
     */
    private int _speed;

    /**
     * The singleton instance for this class.
     */
    private static Clock INSTANCE;

    /**
     * The name of the {@link #_clockThread}.
     */
    private static final String THREAD_NAME = "Clock-Main";

    /**
     * The default value of the {@link #_speed}.
     */
    private static final int DEFAULT_SPEED = 1000;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Clock.class );

    /**
     * Initializes the object. Sets the {@link #_fieldType} to minutes.
     */
    private Clock( )
    {
        _listeners = new Vector<IClockListener>( );
        _fieldType = DurationFieldType.minutes( );
        _clockThread = new Thread( this, THREAD_NAME );
        _mustStop = false;
        _speed = DEFAULT_SPEED;
        _lockObject = new Object( );
        _currentDateTime = new MutableDateTime( );
    }

    /**
     * @return The {@link #_currentDateTime} in {@link DateTime} format so that
     *         it cannot be modified.
     */
    public DateTime getCurrentDateTime ( )
    {
        return _currentDateTime.toDateTime( );
    }

    /**
     * @param currentDateTime
     *            The {@link #_currentDateTime} to set.
     */
    public void setCurrentDateTime ( MutableDateTime currentDateTime )
    {
        _currentDateTime = currentDateTime;
    }

    /**
     * @return The {@link #_fieldType}.
     */
    public DurationFieldType getFieldType ( )
    {
        return _fieldType;
    }

    /**
     * @param fieldType
     *            The {@link #_fieldType} to set.
     */
    public void setFieldType ( DurationFieldType fieldType )
    {
        _fieldType = fieldType;
    }

    /**
     * @param listener
     *            The {@link IClockListener} to add to the {@link #_listeners}.
     */
    public void addListener ( IClockListener listener )
    {
        synchronized ( _lockObject )
        {
            if ( !_listeners.contains( listener ) )
            {
                _listeners.add( listener );
            }
        }
    }

    /**
     * @param listener
     *            The {@link IClockListener} to remove from the
     *            {@link #_listeners}.
     */
    public void removeListener ( IClockListener listener )
    {
        synchronized ( _lockObject )
        {
            if ( _listeners.contains( listener ) )
            {
                _listeners.remove( listener );
            }
        }
    }

    /**
     * @return the {@link #_listeners}.
     */
    public List<IClockListener> getListeners ( )
    {
        return Collections.unmodifiableList( _listeners );
    }

    @Override
    public void run ( )
    {
        while ( !_mustStop )
        {
            synchronized ( _lockObject )
            {
                MutableDateTime previousDateTime = _currentDateTime.copy( );
                _currentDateTime.add( _fieldType, 1 );
                ticked( previousDateTime );
            }
            // Do not sleep inside the synchronized block.
            try
            {
                Thread.sleep( _speed );
            }
            catch ( InterruptedException e )
            {
                // Just warn no one is going to interrupt this thread.
                LOGGER.warn( "An InterruptedException occured while the clock was waiting.", e );
            }
        }
    }

    /**
     * Starts the clock.
     */
    public void start ( )
    {
        LOGGER.info( "Starting clock..." );
        if ( !_clockThread.isAlive( ) )
        {
            _mustStop = false;
            _clockThread.start( );
        }
    }

    /**
     * @param speed
     *            the {@link #_speed} to set.
     */
    public void setSpeed ( int speed )
    {
        _speed = speed;
    }

    /**
     * Stops the clock.
     */
    public void stop ( )
    {
        LOGGER.info( "Stopping clock..." );
        _mustStop = true;
    }

    /**
     * Forces a day change.
     *
     * @param startTime
     *            The {@link LocalTime} to put when the day changes.
     */
    public void performDayChange ( LocalTime startTime )
    {
        synchronized ( _lockObject )
        {
            if ( LOGGER.isDebugEnabled( ) )
            {
                LOGGER.debug( "Performing day change..." );
            }
            MutableDateTime previousDateTime = _currentDateTime.copy( );
            _currentDateTime.add( DurationFieldType.days( ), 1 );
            _currentDateTime.setTime( startTime.getHourOfDay( ), startTime.getMinuteOfHour( ), startTime.getSecondOfMinute( ), startTime
                    .getMillisOfSecond( ) );
            ticked( previousDateTime );
        }
    }

    /**
     * @return The {@link #INSTANCE}.
     */
    public static Clock getInstance ( )
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new Clock( );
        }
        return INSTANCE;
    }

    /**
     * Notifies all listeners that the day changed.
     */
    private void ticked ( MutableDateTime previousDateTime )
    {
        synchronized ( _lockObject )
        {
            DurationFieldType higherChangedType = _fieldType;
            // Determine the higher DurationFieldType that has changed.
            // If the year is changed then add all DurationFieldTypes under
            // year(month,day,hour,minute,second,millis) etc.
            LocalDate previousDate = previousDateTime.toDateTime( ).toLocalDate( );
            LocalDate currentDate = _currentDateTime.toDateTime( ).toLocalDate( );
            Period datePeriod = Period.fieldDifference( previousDate, currentDate );
            if ( datePeriod.getYears( ) > 0 )
            {
                higherChangedType = DurationFieldType.years( );
            }
            else if ( datePeriod.getMonths( ) > 0 )
            {
                higherChangedType = DurationFieldType.months( );
            }
            else if ( datePeriod.getDays( ) > 0 )
            {
                higherChangedType = DurationFieldType.days( );
            }
            else
            {
                LocalTime previousTime = previousDateTime.toDateTime( ).toLocalTime( );
                LocalTime currentTime = _currentDateTime.toDateTime( ).toLocalTime( );

                Period timePeriod = Period.fieldDifference( previousTime, currentTime );

                if ( timePeriod.getHours( ) > 0 )
                {
                    higherChangedType = DurationFieldType.hours( );
                }
                else if ( timePeriod.getMinutes( ) > 0 )
                {
                    higherChangedType = DurationFieldType.minutes( );
                }
                else if ( timePeriod.getSeconds( ) > 0 )
                {
                    higherChangedType = DurationFieldType.seconds( );
                }
                else if ( timePeriod.getMillis( ) > 0 )
                {
                    higherChangedType = DurationFieldType.millis( );
                }
            }
            // Populate the changed types.
            List<DurationFieldType> changedTypes = new Vector<DurationFieldType>( );
            try
            {
                if ( !DurationFieldTypeUtils.higherThan( higherChangedType, _fieldType ) )
                {
                    higherChangedType = _fieldType;
                }

                changedTypes = DurationFieldTypeUtils.getDurationFieldTypes( higherChangedType );
            }
            catch ( NotSupportedException e )
            {
                LOGGER.warn( "DurationFieldType is not supported returning empty changedTypes.", e );
            }
            // Notify the listeners.
            for ( IClockListener listener : _listeners )
            {
                listener.ticked( changedTypes );
            }
        }
    }
}
