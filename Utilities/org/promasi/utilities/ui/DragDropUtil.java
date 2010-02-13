package org.promasi.utilities.ui;


import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.io.IOException;

import javax.swing.JComponent;


/**
 * Utility for making a {@link JComponent} draggable.
 * 
 * @author eddiefullmetal
 * 
 */
public final class DragDropUtil
{

    /**
     * The {@link DataFlavor} that will be transfered.
     */
    public static DataFlavor TRANSFERED_FLAVOR;

    static
    {
        try
        {
            TRANSFERED_FLAVOR = new DataFlavor( DataFlavor.javaJVMLocalObjectMimeType + ";class=java.lang.Object" );
        }
        catch ( ClassNotFoundException e )
        {
            // Not going to happen since the DataFlavor is a java object.
        }
    }

    /**
     * Initializes the object.
     */
    private DragDropUtil( )
    {

    }

    /**
     * Makes a {@link JComponent} draggable.
     * 
     * @param component
     *            The {@link JComponent} to make draggable.
     * 
     * @param transferObject
     *            The object to be transfered.
     */
    public static void makeDraggable ( JComponent component, Object transferObject )
    {
        DragSource dragSource = DragSource.getDefaultDragSource( );
        dragSource.createDefaultDragGestureRecognizer( component, DnDConstants.ACTION_COPY_OR_MOVE, new DefaultDragGestureListener( transferObject ) );
    }

    /**
     * Starts dragging a {@link JComponent}.
     * 
     * @author eddiefullmetal
     * 
     */
    private static class DefaultDragGestureListener
            implements DragGestureListener
    {

        /**
         * The object that will be transfered with the {@link Transferable}.
         */
        private Object _tranferableObject;

        /**
         * 
         * Initializes the object.
         * 
         * @param tranferableObject
         *            The {@link #_tranferableObject}.
         * 
         */
        public DefaultDragGestureListener( Object tranferableObject )
        {
            _tranferableObject = tranferableObject;
        }

        @Override
        public void dragGestureRecognized ( DragGestureEvent dge )
        {
            dge.startDrag( new Cursor( Cursor.MOVE_CURSOR ), new Transferable( )
            {

                @Override
                public boolean isDataFlavorSupported ( DataFlavor flavor )
                {
                    return true;
                }

                @Override
                public DataFlavor[] getTransferDataFlavors ( )
                {
                    return new DataFlavor[] { TRANSFERED_FLAVOR };
                }

                @Override
                public Object getTransferData ( DataFlavor flavor )
                        throws UnsupportedFlavorException, IOException
                {
                    return _tranferableObject;
                }
            } );
        }
    }

}
