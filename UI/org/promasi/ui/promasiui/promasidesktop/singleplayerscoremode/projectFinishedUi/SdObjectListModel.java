package org.promasi.ui.promasiui.promasidesktop.singleplayerscoremode.projectFinishedUi;


import java.util.List;
import java.util.Vector;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.promasi.core.ISdObject;
import org.promasi.core.SdModel;
import org.promasi.core.SdObjectInfo;


/**
 * 
 * A {@link ListModel} that displays all the {@link ISdObject}s of a model that
 * have an {@link SdObjectInfo}.
 * 
 * @author eddiefullmetal
 * 
 */
public class SdObjectListModel
        implements ListModel
{

    /**
     * A {@link List} with all the {@link ISdObject}s to display.
     */
    private List<ISdObject> _sdObjectList;

    /**
     * Initializes the object.
     * 
     */
    public SdObjectListModel( SdModel model )
    {
        _sdObjectList = new Vector<ISdObject>( );
        for ( ISdObject sdObject : model.getSdObjects( ) )
        {
            if ( sdObject.getInfo( ) != null )
            {
                _sdObjectList.add( sdObject );
            }
        }
    }

    @Override
    public void addListDataListener ( ListDataListener l )
    {
        // Currently not supported
    }

    @Override
    public Object getElementAt ( int index )
    {
        return _sdObjectList.get( index ).getKey( );
    }

    /**
     * @param index
     * @return The {@link ISdObject} at the specified index.
     */
    public ISdObject getSdObjectAt ( int index )
    {
        return _sdObjectList.get( index );
    }

    @Override
    public int getSize ( )
    {
        return _sdObjectList.size( );
    }

    @Override
    public void removeListDataListener ( ListDataListener l )
    {
        // Currently not supported
    }

}
