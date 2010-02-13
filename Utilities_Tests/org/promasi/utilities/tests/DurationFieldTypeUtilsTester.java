package org.promasi.utilities.tests;


import java.rmi.UnexpectedException;
import java.util.List;

import javax.transaction.NotSupportedException;

import junit.framework.Assert;

import org.apache.commons.lang.NullArgumentException;
import org.joda.time.DurationFieldType;
import org.junit.Before;
import org.junit.Test;
import org.promasi.utilities.TestUtil;
import org.promasi.utilities.jodautils.DurationFieldTypeUtils;


/**
 * 
 * Tests the {@link DurationFieldTypeUtils} class.
 * 
 * @author eddiefullmetal
 * 
 */
public class DurationFieldTypeUtilsTester
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
     * Tests the
     * {@link DurationFieldTypeUtils#higherThan(org.joda.time.DurationFieldType, org.joda.time.DurationFieldType)}
     * method.
     * 
     * @throws UnexpectedException
     * @throws NotSupportedException
     * @throws NullArgumentException
     */
    @Test( expected = NullArgumentException.class )
    public void testHigherThan ( )
            throws UnexpectedException, NullArgumentException, NotSupportedException
    {
        try
        {
            // Check a higher value.
            Assert.assertEquals( true, DurationFieldTypeUtils.higherThan( DurationFieldType.years( ), DurationFieldType.hours( ) ) );
            // Check an equals value.
            Assert.assertEquals( false, DurationFieldTypeUtils.higherThan( DurationFieldType.years( ), DurationFieldType.years( ) ) );
            // Check a lower value.
            Assert.assertEquals( false, DurationFieldTypeUtils.higherThan( DurationFieldType.months( ), DurationFieldType.years( ) ) );
        }
        catch ( NotSupportedException e )
        {
            throw new UnexpectedException( "Unexpected exception was thrown.", e );
        }
        try
        {
            // Check not supported
            Assert.assertEquals( false, DurationFieldTypeUtils.higherThan( DurationFieldType.months( ), DurationFieldType.centuries( ) ) );
        }
        catch ( NotSupportedException e )
        {
            // Success case.
        }
        // Check null value expected exception
        Assert.assertEquals( false, DurationFieldTypeUtils.higherThan( DurationFieldType.months( ), null ) );
    }

    /**
     * Tests the
     * {@link DurationFieldTypeUtils#getDurationFieldTypes(DurationFieldType)}
     * method.
     * 
     * @throws Exception
     */
    @Test( expected = NullArgumentException.class )
    public void testGetDurationFieldTypes ( )
            throws UnexpectedException, NotSupportedException
    {
        List<DurationFieldType> fieldTypes;
        try
        {
            // Test years
            fieldTypes = DurationFieldTypeUtils.getDurationFieldTypes( DurationFieldType.years( ) );
            Assert.assertEquals( 7, fieldTypes.size( ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.years( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.months( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.days( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.hours( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.minutes( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.seconds( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.millis( ) ) );
            // Test months
            fieldTypes = DurationFieldTypeUtils.getDurationFieldTypes( DurationFieldType.months( ) );
            Assert.assertEquals( 6, fieldTypes.size( ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.months( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.days( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.hours( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.minutes( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.seconds( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.millis( ) ) );
            // Test days
            fieldTypes = DurationFieldTypeUtils.getDurationFieldTypes( DurationFieldType.days( ) );
            Assert.assertEquals( 5, fieldTypes.size( ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.days( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.hours( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.minutes( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.seconds( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.millis( ) ) );
            // Test hours
            fieldTypes = DurationFieldTypeUtils.getDurationFieldTypes( DurationFieldType.hours( ) );
            Assert.assertEquals( 4, fieldTypes.size( ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.hours( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.minutes( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.seconds( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.millis( ) ) );
            // Test minutes
            fieldTypes = DurationFieldTypeUtils.getDurationFieldTypes( DurationFieldType.minutes( ) );
            Assert.assertEquals( 3, fieldTypes.size( ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.minutes( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.seconds( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.millis( ) ) );
            // Test seconds
            fieldTypes = DurationFieldTypeUtils.getDurationFieldTypes( DurationFieldType.seconds( ) );
            Assert.assertEquals( 2, fieldTypes.size( ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.seconds( ) ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.millis( ) ) );
            // Test millis
            fieldTypes = DurationFieldTypeUtils.getDurationFieldTypes( DurationFieldType.millis( ) );
            Assert.assertEquals( 1, fieldTypes.size( ) );
            Assert.assertTrue( fieldTypes.contains( DurationFieldType.millis( ) ) );
        }
        catch ( NotSupportedException e )
        {
            throw new UnexpectedException( "Unexpected exception was thrown.", e );
        }
        // Test unsupported
        try
        {
            fieldTypes = DurationFieldTypeUtils.getDurationFieldTypes( DurationFieldType.centuries( ) );
            throw new UnexpectedException( "NotSupportedException wasn't thrown." );
        }
        catch ( NotSupportedException e )
        {
            // Success case.
        }
        // Test null expected exception.
        fieldTypes = DurationFieldTypeUtils.getDurationFieldTypes( null );
    }
}
