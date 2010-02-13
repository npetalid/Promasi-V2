package org.promasi.core;


/**
 * 
 * Class that provides info about an {@link ISdObject}.
 * 
 * @author eddiefullmetal
 * 
 */
public class SdObjectInfo
{
    /**
     * The description of an {@link ISdObject}.
     */
    private String _description;

    /**
     * Hint about what affects this object and how you can get better scores.
     */
    private String _hint;

    /**
     * Initializes the object.
     * 
     */
    public SdObjectInfo( )
    {

    }

    /**
     * Initializes the object.
     * 
     * @param description
     * @param hint
     */
    public SdObjectInfo( String description, String hint )
    {
        super( );
        _description = description;
        _hint = hint;
    }

    /**
     * @return the {@link #_description}.
     */
    public String getDescription ( )
    {
        return _description;
    }

    /**
     * @param description
     *            the {@link #_description} to set.
     */
    public void setDescription ( String description )
    {
        _description = description;
    }

    /**
     * @return the {@link #_hint}.
     */
    public String getHint ( )
    {
        return _hint;
    }

    /**
     * @param hint
     *            the {@link #_hint} to set.
     */
    public void setHint ( String hint )
    {
        _hint = hint;
    }

}
