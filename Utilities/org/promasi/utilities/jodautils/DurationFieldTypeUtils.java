package org.promasi.utilities.jodautils;


import java.util.Hashtable;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.transaction.NotSupportedException;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.joda.time.DurationFieldType;
import org.promasi.utilities.ErrorBuilder;


/**
 * 
 * Various utility methods on the {@link DurationFieldType} class.
 * 
 * @author eddiefullmetal
 * 
 */
public final class DurationFieldTypeUtils
{

    /**
     * All the standard {@link DurationFieldType}s with their order as a number.
     */
    private static SortedMap<Integer, DurationFieldType> ORDERED_DURATION_FIELD_TYPES;

    /**
     * Keeps information on the order number of a {@link DurationFieldType}.
     */
    private static Hashtable<DurationFieldType, Integer> DURATION_FIELD_TYPE_ORDER;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( DurationFieldTypeUtils.class );

    /**
     * Initializes the object.
     */
    private DurationFieldTypeUtils( )
    {

    }

    static
    {
        DURATION_FIELD_TYPE_ORDER = new Hashtable<DurationFieldType, Integer>( );
        DURATION_FIELD_TYPE_ORDER.put( DurationFieldType.years( ), 1 );
        DURATION_FIELD_TYPE_ORDER.put( DurationFieldType.months( ), 2 );
        DURATION_FIELD_TYPE_ORDER.put( DurationFieldType.days( ), 3 );
        DURATION_FIELD_TYPE_ORDER.put( DurationFieldType.hours( ), 4 );
        DURATION_FIELD_TYPE_ORDER.put( DurationFieldType.minutes( ), 5 );
        DURATION_FIELD_TYPE_ORDER.put( DurationFieldType.seconds( ), 6 );
        DURATION_FIELD_TYPE_ORDER.put( DurationFieldType.millis( ), 7 );

        ORDERED_DURATION_FIELD_TYPES = new TreeMap<Integer, DurationFieldType>( );
        ORDERED_DURATION_FIELD_TYPES.put( 1, DurationFieldType.years( ) );
        ORDERED_DURATION_FIELD_TYPES.put( 2, DurationFieldType.months( ) );
        ORDERED_DURATION_FIELD_TYPES.put( 3, DurationFieldType.days( ) );
        ORDERED_DURATION_FIELD_TYPES.put( 4, DurationFieldType.hours( ) );
        ORDERED_DURATION_FIELD_TYPES.put( 5, DurationFieldType.minutes( ) );
        ORDERED_DURATION_FIELD_TYPES.put( 6, DurationFieldType.seconds( ) );
        ORDERED_DURATION_FIELD_TYPES.put( 7, DurationFieldType.millis( ) );
    }

    /**
     * @param fieldType
     * @return The fieldType with all the standard lower
     *         {@link DurationFieldType}s according to their order.
     * @throws NullArgumentException
     * @throws NotSupportedException
     *             If the fieldType is not supported for this operation.
     */
    public static List<DurationFieldType> getDurationFieldTypes ( DurationFieldType fieldType )
            throws NullArgumentException, NotSupportedException
    {
        if ( fieldType == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "getDurationFieldTypes", "fieldType" ) );
            throw new NullArgumentException( "fieldType" );
        }

        if ( !DURATION_FIELD_TYPE_ORDER.containsKey( fieldType ) )
        {
            String message = "DurationFieldType is not supported";
            LOGGER.error( message );
            throw new NotSupportedException( message );
        }

        int index = DURATION_FIELD_TYPE_ORDER.get( fieldType );

        return new Vector<DurationFieldType>( ORDERED_DURATION_FIELD_TYPES.tailMap( index ).values( ) );
    }

    /**
     * Checks if the type is higher than the target.
     * 
     * @param type
     *            The type to check.
     * @param target
     *            The {@link DurationFieldType} to check against.
     * @return True if the type is higher than the target, false otherwise.
     * @throws NotSupportedException
     *             If the type or the targer {@link DurationFieldType} are not
     *             supported for this operation.
     * 
     * @throws NullArgumentException
     */
    public static boolean higherThan ( DurationFieldType type, DurationFieldType target )
            throws NullArgumentException, NotSupportedException
    {
        if ( type == null || target == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "higherThan", "type,target" ) );
            throw new NullArgumentException( "type,target" );
        }

        if ( !DURATION_FIELD_TYPE_ORDER.containsKey( type ) || !DURATION_FIELD_TYPE_ORDER.containsKey( target ) )
        {
            String message = "DurationFieldType is not supported";
            LOGGER.error( message );
            throw new NotSupportedException( message );
        }

        int typeIndex = DURATION_FIELD_TYPE_ORDER.get( type );
        int targetIndex = DURATION_FIELD_TYPE_ORDER.get( target );

        // higher is 1.
        return typeIndex < targetIndex;
    }
}
