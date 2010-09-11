package org.promasi.core.sdobjects;


import org.apache.log4j.Logger;
import org.promasi.core.SdObjectType;


/**
 * Implementation of the {@link AbstractSdObject} for the
 * {@link SdObjectType#Stock}.
 * 
 * @author eddiefullmetal
 * 
 */
public class StockSdObject
        extends AbstractSdObject
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The previous value of the Stock object.
     */
    private Double _previousValue;

    /**
     * Default logger for this class.
     */
    private Logger LOGGER = Logger.getLogger( StockSdObject.class );

    /**
     * Initializes the object.
     * 
     * @param key
     *            The key of the {@link AbstractSdObject}
     */
    public StockSdObject( String key )
    {
        super( key, SdObjectType.Stock );
        setValue( 0.0d );
    }

    /**
     * Initializes the object.
     */
    public StockSdObject( )
    {
        setType( SdObjectType.Stock );
        setValue( 0.0d );
    }

    /**
     * Initializes the object.
     * 
     * @param key
     *            The key of the {@link AbstractSdObject}
     * @param initialValue
     *            The initial value of the stock.
     */
    public StockSdObject( String key, Double initialValue )
    {
        this( key );
        if ( initialValue != null )
        {
            setValue( initialValue );
        }
    }

    /**
     * Keeps the previous value and calculates its new value by getting the new
     * value and adding it to the previous value.
     */
    @Override
    public void calculateValue ( )
    {
        _previousValue = getValue( );
        super.calculateValue( );
        setValue( _previousValue + getValue( ) );
        LOGGER.debug( "Settings stock value to :" + getValue( ) );
    }

    /**
     * @param value
     *            The {@link #_previousValue} to set.
     */
    public void setPreviousValue ( Double value )
    {
        _previousValue = value;
    }

    /**
     * @return The {@link #_previousValue}.
     */
    public Double getPreviousValue ( )
    {
        return _previousValue;
    }

}
