package org.promasi.core;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.promasi.utilities.ErrorBuilder;


/**
 * The SdModel consists of many {@link ISdObject}s that describe the model. It
 * organizes those objects and provides various methods to handle them. The
 * class follows the javabeans specification.
 *
 * @author eddiefullmetal
 *
 */
public class SdModel
        implements Serializable
{
    /**
     * A {@link Hashtable} that contains all the {@link ISdObject}s of the
     * model. The key of the {@link Hashtable} is the {@link ISdObject#getKey()}
     * value.
     */
    private Hashtable<String, ISdObject> _keySdObjects;

    /**
     * A {@link Hashtable} that contains all the {@link ISdObject}s of the model
     * Separated by their {@link SdObjectType}.
     */
    private Hashtable<SdObjectType, List<ISdObject>> _categories;

    /**
     * A {@link Hashtable} that defines which {@link ISdObject}s an
     * {@link ISdObject} affects. This way with the
     * {@link ISdObject#getDependencies()} the system knows which
     * {@link ISdObject}s a specific {@link ISdObject} needs in order to
     * calculate its value and which {@link ISdObject}s a specific
     * {@link ISdObject} affects if its value is changed.
     */
    private Hashtable<ISdObject, List<ISdObject>> _affectedSdObjects;

    /**
     * All the {@link ISdObject}s of the model.
     */
    private List<ISdObject> _sdObjects;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( SdModel.class );

    /**
     * Initializes the object.
     */
    public SdModel( )
    {
        _keySdObjects = new Hashtable<String, ISdObject>( );
        _categories = new Hashtable<SdObjectType, List<ISdObject>>( );
        _affectedSdObjects = new Hashtable<ISdObject, List<ISdObject>>( );
        _sdObjects = new Vector<ISdObject>( );
    }

    /**
     *
     * Registers all the given {@link ISdObject}s.
     *
     * @param sdObjects
     *            The {@link ISdObject}s to register.
     *
     * @throws NullArgumentException
     *             In case the sdobjects is null.
     */
    public void setSdObjects ( List<ISdObject> sdObjects )
            throws NullArgumentException
    {
        // Check arguments
        if ( sdObjects == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "setSdObjects", "sdObjects" ) );
            throw new NullArgumentException( "sdObjects" );
        }

        _sdObjects = sdObjects;
    }

    /**
     * Initializes the model using the {@link #_sdObjects} list.
     */
    public void initialize ( )
    {
        // Organize the objects.
        LOGGER.info( "Initializing the SdModel with " + _sdObjects.size( ) + " ISdObjects" );

        for ( ISdObject sdObject : _sdObjects )
        {
            addSdObject( sdObject );
        }
    }

    /**
     * Registers a single {@link ISdObject} to the model.
     *
     * @param sdObject
     *            The {@link ISdObject} to register.
     */
    private void addSdObject ( ISdObject sdObject )
    {
        // Check arguments
        if ( sdObject == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "addSdObject", "sdObject" ) );
            throw new NullArgumentException( "sdObject" );
        }

        LOGGER.info( "Registering ISdObject: " + sdObject.getKey( ) );

        _keySdObjects.put( sdObject.getKey( ), sdObject );

        // Register the object to the category list. If a corresponding list
        // does not exist create it.
        List<ISdObject> categoryList = _categories.get( sdObject.getType( ) );
        if ( categoryList == null )
        {
            categoryList = new Vector<ISdObject>( );
            _categories.put( sdObject.getType( ), categoryList );
        }
        categoryList.add( sdObject );

        // The dependencies of the current sdObject affect it.
        for ( ISdObject dependencyObject : sdObject.getDependencies( ) )
        {
            List<ISdObject> affectedSdObjects = _affectedSdObjects.get( dependencyObject );
            if ( affectedSdObjects == null )
            {
                affectedSdObjects = new Vector<ISdObject>( );
                _affectedSdObjects.put( dependencyObject, affectedSdObjects );
            }
            affectedSdObjects.add( sdObject );
            _affectedSdObjects.put( dependencyObject, affectedSdObjects );
        }
    }

    /**
     * Gets an {@link ISdObject} from the model with a given key.
     *
     * @param key
     *            The key of the {@link ISdObject} to return.
     * @return The {@link ISdObject} corresponding to the given key or null if
     *         an {@link ISdObject} with the specified key does not exists.
     *
     * @throws IllegalArgumentException
     *             In case the key is null or empty
     */
    public ISdObject getSdObject ( String key )
            throws IllegalArgumentException
    {
        // Check arguments
        if ( key == null || key.isEmpty( ) )
        {
            String message = ErrorBuilder.generateNullOrEmptyArgumentError( "getSdObject", "key" );
            LOGGER.error( message );
            throw new IllegalArgumentException( message );
        }
        return _keySdObjects.get( key );
    }

    /**
     * Gets all the {@link ISdObject}s that have the specified
     * {@link SdObjectType}.
     *
     * @param type
     *            The {@link SdObjectType}.
     * @return All the {@link ISdObject}s for the specified type or an empty
     *         {@link List} if no {@link ISdObject} with the specified type
     *         exists.
     */
    public List<ISdObject> getSdObjectsByType ( SdObjectType type )
    {
        List<ISdObject> categorySdObjects = _categories.get( type );
        if ( categorySdObjects == null )
        {
            categorySdObjects = new Vector<ISdObject>( );
        }
        return categorySdObjects;
    }

    /**
     * Gets all the {@link ISdObject}s that the sdObject affects.
     *
     * @param sdObject
     *            The {@link ISdObject} to learn which {@link ISdObject}s it
     *            affects.
     * @return The {@link ISdObject}s that the sdObject affects.
     */
    public List<ISdObject> getAffectedSdObjects ( ISdObject sdObject )
    {
        List<ISdObject> affectedSdObjects = _affectedSdObjects.get( sdObject );
        if ( affectedSdObjects == null )
        {
            affectedSdObjects = new Vector<ISdObject>( );
        }
        return affectedSdObjects;
    }

    /**
     * @return The number of {@link ISdObject}s that this model contains.
     */
    public int getSize ( )
    {
        return _keySdObjects.size( );
    }

    /**
     * @return All the {@link ISdObject}s of the model.
     */
    public List<ISdObject> getSdObjects ( )
    {
        return _sdObjects;
    }

    /**
     *
     * @return The output values for current SdModel
     */
    public HashMap<String,Double> getOutputs()
    {
    	HashMap<String,Double> result=new HashMap<String,Double>();
    	if(!_categories.contains(SdObjectType.Output))
    	{
    		return result;
    	}
    	List<ISdObject> sdObjects=_categories.get(SdObjectType.Output);
    	for(int i=0;i<sdObjects.size();i++)
    	{
    		ISdObject object=sdObjects.get(i);
    		if(object!=null)
    		{
    			result.put(object.getKey(), object.getValue());
    		}
    	}
    	return result;
    }
}
