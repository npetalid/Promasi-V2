package org.promasi.model;


import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.ReadablePartial;


/**
 * 
 * Class for scheduling an {@link ITimerTask} for a specific time of the
 * {@link Clock}.
 * 
 * 
 * @author eddiefullmetal
 * 
 */
public class Timer
        implements IClockListener
{

    /**
     * Singleton implementation.
     */
    private static Timer INSTANCE;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Timer.class );

    /**
     * All the registered {@link ITimerTask}s of the timer.
     */
    private Map<ITimerTask, ReadablePartial> _scheduledTasks;

    /**
     * Object used for locking.
     */
    private Object _lockObject;

    /**
     * Initializes the object.
     */
    protected Timer( )
    {
        Clock.getInstance( ).addListener( this );
        _scheduledTasks = new Hashtable<ITimerTask, ReadablePartial>( );
        _lockObject = new Object( );
    }

    /**
     * 
     * The method schedules an {@link ITimerTask} for a specific date and/or
     * time if null arguments are passed the methods does not execute.
     * 
     * @param timerTask
     *            The {@link ITimerTask} to schedule for the specified time
     * @param time
     *            The time to schedule the task( {@link LocalDateTime},
     *            {@link LocalDate}, {@link LocalTime}). If the
     *            {@link ReadablePartial} supports time only then the task will
     *            be executed for each date at the specified time. If the
     *            {@link ReadablePartial} supports date only then the task will
     *            be executed when the day is changed if the
     *            {@link ReadablePartial} supports both then it will be executed
     *            at the specified date and time.
     * 
     */
    public void scheduleTask ( ITimerTask timerTask, ReadablePartial time )
    {
        if ( timerTask != null && time != null )
        {
            synchronized ( _lockObject )
            {
                LOGGER.info( "Scheduling task " + timerTask + " at " + time );
                _scheduledTasks.put( timerTask, time );
            }
        }
        else
        {
            LOGGER.warn( "Null arguments passed at scheduleTask ignoring call..." );
        }
    }

    /**
     * @param timerTask
     *            The {@link ITimerTask} to remove from the
     *            {@link #_scheduledTasks}.
     */
    public void unScheduleTask ( ITimerTask timerTask )
    {
        synchronized ( _lockObject )
        {
            _scheduledTasks.remove( timerTask );
        }
    }

    /**
     * Singleton implementation.
     */
    public static Timer getInstance ( )
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new Timer( );
        }
        return INSTANCE;
    }

    /**
     * @return the all the keys( {@link ITimerTask}s ) of the
     *         {@link #_scheduledTasks}.
     */
    public Set<ITimerTask> getScheduledTasks ( )
    {
        return Collections.unmodifiableSet( _scheduledTasks.keySet( ) );
    }

    @Override
    public void ticked ( List<DurationFieldType> changedTypes )
    {
        synchronized ( _lockObject )
        {
            Hashtable<ITimerTask, ReadablePartial> copiedScheduledTasks = new Hashtable<ITimerTask, ReadablePartial>( _scheduledTasks );
            for ( ITimerTask timerTask : copiedScheduledTasks.keySet( ) )
            {
                ReadablePartial time = copiedScheduledTasks.get( timerTask );
                // Check if the time supports date.
                boolean supportsDate = time.isSupported( DateTimeFieldType.dayOfMonth( ) );
                // Check if the time supports time.
                boolean supportsTime = time.isSupported( DateTimeFieldType.hourOfDay( ) );
                DateTime currentTime = Clock.getInstance( ).getCurrentDateTime( );
                if ( supportsDate && supportsTime )
                {
                    int day = time.get( DateTimeFieldType.dayOfMonth( ) );
                    int month = time.get( DateTimeFieldType.monthOfYear( ) );
                    int year = time.get( DateTimeFieldType.year( ) );
                    int hour = time.get( DateTimeFieldType.hourOfDay( ) );
                    int minute = time.get( DateTimeFieldType.minuteOfHour( ) );
                    int second = time.get( DateTimeFieldType.secondOfMinute( ) );

                    LocalDateTime taskDateTime = new LocalDateTime( year, month, day, hour, minute, second, 0 );
                    if ( taskDateTime.isEqual( currentTime.toLocalDateTime( ) ) || taskDateTime.isBefore( currentTime.toLocalDateTime( ) ) )
                    {
                        LOGGER.info( "Running task :" + timerTask );
                        timerTask.runTimerTask( );
                        _scheduledTasks.remove( timerTask );
                    }
                }
                else if ( supportsDate )
                {
                    int day = time.get( DateTimeFieldType.dayOfMonth( ) );
                    int month = time.get( DateTimeFieldType.monthOfYear( ) );
                    int year = time.get( DateTimeFieldType.year( ) );

                    LocalDate taskDateTime = new LocalDate( year, month, day );
                    if ( taskDateTime.isEqual( currentTime.toLocalDateTime( ) ) || taskDateTime.isBefore( currentTime.toLocalDateTime( ) ) )
                    {
                        LOGGER.info( "Running task :" + timerTask );
                        timerTask.runTimerTask( );
                        _scheduledTasks.remove( timerTask );
                    }
                }
                else if ( supportsTime )
                {
                    int hour = time.get( DateTimeFieldType.hourOfDay( ) );
                    int minute = time.get( DateTimeFieldType.minuteOfHour( ) );
                    int second = time.get( DateTimeFieldType.secondOfMinute( ) );

                    LocalTime taskDateTime = new LocalTime( hour, minute, second );
                    if ( taskDateTime.isEqual( currentTime.toLocalDateTime( ) ) || taskDateTime.isBefore( currentTime.toLocalDateTime( ) ) )
                    {
                        LOGGER.info( "Running task :" + timerTask );
                        timerTask.runTimerTask( );
                    }
                }
            }
        }
    }
}
