package org.promasi.ui.gmtools.coredesigner.controllers;


import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

import org.promasi.core.ISdObject;
import org.promasi.core.SdModel;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.ui.gmtools.coredesigner.model.SdModelInfo;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;


/**
 * 
 * Class provides methods for controlling all the ui actions.
 * 
 * @author eddiefullmetal
 * 
 */
public final class Shell
{

    /**
     * Contains all the created models.
     */
    private static List<SdModelInfo> MODEL_POOL;

    /**
     * All the registered {@link IShellListener}s.
     */
    private static List<IShellListener> LISTENERS;

    /**
     * Initializes the object.
     */
    private Shell( )
    {

    }

    static
    {
        MODEL_POOL = new Vector<SdModelInfo>( );
        LISTENERS = new Vector<IShellListener>( );
    }

    /**
     * Creates a new model with the given name.
     * 
     * @param name
     *            The name of the model.
     */
    public static void createNewModel ( String name )
    {
        if ( name != null && !name.isEmpty( ) )
        {
            SdModelInfo modelInfo = new SdModelInfo( name );
            MODEL_POOL.add( modelInfo );
            // Notify all listeners.
            for ( IShellListener listener : LISTENERS )
            {
                listener.modelCreated( modelInfo );
            }
        }
    }

    /**
     * Loads an {@link SdModel} from the given file.
     * 
     * @param file
     */
    public static void loadModel ( File file )
    {
        if ( file != null && file.exists( ) )
        {
            try
            {
                XMLDecoder decoder = new XMLDecoder( new FileInputStream( file ) );
                SdModel model = (SdModel) decoder.readObject( );
                SdModelInfo info = new SdModelInfo( "Loaded Model" );
                for ( ISdObject sdObject : model.getSdObjects( ) )
                {
                    if ( sdObject instanceof AbstractSdObject )
                    {
                        SdObjectDecorator sdObjectDecorator = new SdObjectDecorator( (AbstractSdObject) sdObject );
                        info.addISdObject( sdObjectDecorator );
                    }
                }
                MODEL_POOL.add( info );
                // Notify all listeners.
                for ( IShellListener listener : LISTENERS )
                {
                    listener.modelCreated( info );
                }
            }
            catch ( FileNotFoundException e )
            {
                e.printStackTrace( );
            }
        }
    }

    /**
     * Removes the object from the {@link SdModelInfo} and notifies all
     * {@link IShellListener}s.
     * 
     * @param obj
     */
    public static void deleteObject ( SdObjectDecorator obj )
    {
        SdModelInfo info = getModelFromObject( obj );
        info.removeISdObject( obj );
        for ( IShellListener listener : LISTENERS )
        {
            listener.objectRemoved( info, obj );
        }
    }

    /**
     * Returns the {@link SdModelInfo} that contains the sdObject.
     * 
     * @param sdObject
     *            The {@link SdObjectDecorator} to search for the
     *            {@link SdModelInfo}.
     * @return The {@link SdModelInfo} or null if the {@link SdModelInfo} is not
     *         found.
     */
    public static SdModelInfo getModelFromObject ( SdObjectDecorator sdObject )
    {
        for ( SdModelInfo sdModelInfo : MODEL_POOL )
        {
            if ( sdModelInfo.getISdObjects( ).contains( sdObject ) )
            {
                return sdModelInfo;
            }
        }
        return null;
    }

    /**
     * Adds an {@link IShellListener} to the {@link #LISTENERS}.
     * 
     * @param listener
     *            The {@link IShellListener} to add.
     */
    public static void addListener ( IShellListener listener )
    {
        if ( !LISTENERS.contains( listener ) )
        {
            LISTENERS.add( listener );
        }
    }

}
