package org.promasi.ui.gmtools.coredesigner;


import java.awt.Color;
import java.awt.Point;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;

import org.jdesktop.swingx.JXPanel;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.core.sdobjects.FlowSdObject;
import org.promasi.core.sdobjects.OutputSdObject;
import org.promasi.core.sdobjects.StockSdObject;
import org.promasi.core.sdobjects.VariableSdObject;
import org.promasi.ui.gmtools.coredesigner.model.SdModelInfo;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.utilities.ui.DragDropUtil;


/**
 * 
 * Represents a model designer.
 * 
 * @author eddiefullmetal
 * 
 */
public abstract class AbstractModelDesigner
        extends JXPanel
        implements DropTargetListener
{

    /**
     * The info of the model that is referring to this designer.
     */
    private SdModelInfo _modelInfo;

    /**
     * A list with the {@link IModelDesignerListener}.
     */
    private List<IModelDesignerListener> _listeners;

    /**
     * Initializes the object.
     * 
     * @param modelInfo
     *            The {@link #_modelInfo}.
     */
    protected AbstractModelDesigner( SdModelInfo modelInfo )
    {
        _modelInfo = modelInfo;
        _listeners = new Vector<IModelDesignerListener>( );
    }

    /**
     * Adds an {@link IModelDesignerListener} to the {@link #_listeners}.
     * 
     * @param listener
     *            The {@link IModelDesignerListener} to add.
     */
    public void addListener ( IModelDesignerListener listener )
    {
        if ( !_listeners.contains( listener ) )
        {
            _listeners.add( listener );
        }
    }

    /**
     * Removes an {@link IModelDesignerListener} from the {@link #_listeners}.
     * 
     * @param listener
     *            The {@link IModelDesignerListener} to remove.
     */
    public void removeListener ( IModelDesignerListener listener )
    {
        if ( _listeners.contains( listener ) )
        {
            _listeners.remove( listener );
        }
    }

    /**
     * Gets the {@link #_listeners}.
     * 
     * @return The {@link #_listeners}.
     */
    protected List<IModelDesignerListener> getListeners ( )
    {
        return _listeners;
    }

    @Override
    public void dragEnter ( DropTargetDragEvent dtde )
    {
        setBorder( BorderFactory.createLineBorder( Color.RED ) );
    }

    @Override
    public void dragExit ( DropTargetEvent dte )
    {
        setBorder( BorderFactory.createEmptyBorder( ) );
    }

    @Override
    public void dragOver ( DropTargetDragEvent dtde )
    {
    }

    @Override
    public void drop ( DropTargetDropEvent dtde )
    {
        Object obj;
        try
        {
            obj = dtde.getTransferable( ).getTransferData( DragDropUtil.TRANSFERED_FLAVOR );

            if ( obj instanceof String )
            {
                // Create the appropriate object based on the transferable.
                SdObjectDecorator sdObject = null;
                if ( obj.equals( StockSdObject.class.getName( ) ) )
                {
                    sdObject = new SdObjectDecorator( new StockSdObject( ) );
                }
                else if ( obj.equals( VariableSdObject.class.getName( ) ) )
                {
                    sdObject = new SdObjectDecorator( new VariableSdObject( ) );
                }
                else if ( obj.equals( FlowSdObject.class.getName( ) ) )
                {
                    sdObject = new SdObjectDecorator( new FlowSdObject( ) );
                }
                else if ( obj.equals( OutputSdObject.class.getName( ) ) )
                {
                    sdObject = new SdObjectDecorator( new OutputSdObject( ) );
                }
                if ( sdObject != null )
                {
                    _modelInfo.addISdObject( sdObject );
                    createSdObject( sdObject, dtde.getLocation( ) );
                }
            }
            else
            {
                // If its not a known type reject the drop;
                dtde.rejectDrop( );
            }
        }
        catch ( UnsupportedFlavorException e )
        {
        }
        catch ( IOException e )
        {
        }
        finally
        {
            setBorder( BorderFactory.createEmptyBorder( ) );
        }
    }

    @Override
    public void dropActionChanged ( DropTargetDragEvent dtde )
    {
    }

    /**
     * Gets the {@link #_modelInfo}.
     * 
     * @return The {@link #_modelInfo}.
     */
    public SdModelInfo getModelInfo ( )
    {
        return _modelInfo;
    }

    /**
     * 
     * Called when a new {@link AbstractSdObject} needs to be created.
     * 
     * @param createdSdObject
     *            The created {@link AbstractSdObject}.
     * @param droppedLocation
     *            The location that the object was dropped.
     */
    protected abstract void createSdObject ( SdObjectDecorator createdSdObject, Point droppedLocation );

    /**
     * Called when an {@link SdObjectDecorator} is removed.
     * 
     * @param sdObject
     *            The {@link SdObjectDecorator} that was removed.
     */
    public abstract void removeSdObject ( SdObjectDecorator sdObject );
}
