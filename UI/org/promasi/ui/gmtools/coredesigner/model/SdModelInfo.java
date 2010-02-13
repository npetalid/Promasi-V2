package org.promasi.ui.gmtools.coredesigner.model;


import java.beans.XMLEncoder;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import org.promasi.core.ISdObject;
import org.promasi.core.SdModel;


/**
 * Contains info for an {@link SdModel}.
 * 
 * @author eddiefullmetal
 * 
 */
public class SdModelInfo
{

    /**
     * The name of the model.
     */
    private String _name;

    /**
     * Holds a temporary list with the objects of the model.
     */
    private List<SdObjectDecorator> _sdObjects;

    /**
     * Initializes the object.
     */
    public SdModelInfo( )
    {
        _sdObjects = new Vector<SdObjectDecorator>( );
    }

    /**
     * Initializes the object.
     * 
     * @param name
     *            The {@link #_name}.
     */
    public SdModelInfo( String name )
    {
        this( );
        _name = name;
    }

    /**
     * Gets the {@link #_name}.
     * 
     * @return The {@link #_name}.
     */
    public String getName ( )
    {
        return _name;
    }

    /**
     * Sets the {@link #_name}.
     * 
     * @param name
     *            The {@link #_name}.
     */
    public void setName ( String name )
    {
        _name = name;
    }

    /**
     * Sets the {@link #_sdObjects}.
     * 
     * @param sdObjects
     *            The {@link #_sdObjects}.
     */
    public void setISdObjects ( List<SdObjectDecorator> sdObjects )
    {
        _sdObjects = sdObjects;
    }

    /**
     * Gets the {@link #_sdObjects}.
     * 
     * @return The {@link #_sdObjects}.
     */
    public List<SdObjectDecorator> getISdObjects ( )
    {
        return _sdObjects;
    }

    /**
     * Adds an {@link ISdObject} to the {@link #_sdObjects}.
     * 
     * @param sdObject
     *            The {@link ISdObject} to add.
     */
    public void addISdObject ( SdObjectDecorator sdObject )
    {
        if ( !_sdObjects.contains( sdObject ) )
        {
            _sdObjects.add( sdObject );
        }
    }

    /**
     * Removes the sdObject from the {@link #_sdObjects}.
     * 
     * @param sdObject
     *            The {@link SdObjectDecorator} to remove.
     */
    public void removeISdObject ( SdObjectDecorator sdObject )
    {
        if ( _sdObjects.contains( sdObject ) )
        {
            _sdObjects.remove( sdObject );
        }
    }

    /**
     * Saves the model to the stream using {@link XMLEncoder}.
     * 
     * @param stream
     *            The stream to save the model.
     */
    public void save ( OutputStream stream )
    {
        // Create a dummy SdModel to write
        SdModel model = new SdModel( );
        List<ISdObject> objects = new Vector<ISdObject>( );
        for ( SdObjectDecorator decorator : _sdObjects )
        {
            objects.add( decorator.getActualSdObject( ) );
        }
        model.setSdObjects( objects );
        model.initialize( );
        XMLEncoder encoder = new XMLEncoder( stream );
        encoder.writeObject( model );
        encoder.close( );
    }
}
