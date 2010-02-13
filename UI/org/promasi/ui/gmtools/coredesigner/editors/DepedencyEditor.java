package org.promasi.ui.gmtools.coredesigner.editors;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.promasi.core.ISdObject;
import org.promasi.ui.gmtools.coredesigner.controllers.Shell;
import org.promasi.ui.gmtools.coredesigner.model.SdModelInfo;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;


/**
 * Editor for the dependencies of an {@link SdObjectDecorator}.
 * 
 * @author eddiefullmetal
 * 
 */
public class DepedencyEditor
        extends JDialog
{

    /**
     * The {@link SdObjectDecorator} to edit the dependencies.
     */
    private SdObjectDecorator _sdObject;

    /**
     * A {@link JList} that displays all the available {@link ISdObject}s of the
     * {@link SdModelInfo}.
     */
    private JList _availableObjectsList;

    /**
     * Adds the selected object as a dependency to the {@link #_sdObject}.
     */
    private JButton _addButton;

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link #_sdObject}.
     */
    public DepedencyEditor( SdObjectDecorator sdObject )
    {
        _sdObject = sdObject;
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes all the ui components.
     */
    protected void initializeComponents ( )
    {
        setTitle( ResourceManager.getString( DepedencyEditor.class, "title" ) );
        setSize( 400, 600 );
        buildAvailableObjectsList( );
        buildAddButton( );
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( new JScrollPane( _availableObjectsList ), new CC( ).grow( ).wrap( ) );
        add( _addButton, new CC( ) );
    }

    /**
     * Creates the {@link #_availableObjectsList}.
     */
    private void buildAvailableObjectsList ( )
    {
        SdModelInfo currentModelInfo = Shell.getModelFromObject( _sdObject );
        if ( currentModelInfo != null )
        {
            List<ISdObject> copyOfModelList = new Vector<ISdObject>( );
            for ( SdObjectDecorator sdObject : currentModelInfo.getISdObjects( ) )
            {
                copyOfModelList.add( sdObject.getActualSdObject( ) );
            }
            copyOfModelList.remove( _sdObject.getActualSdObject( ) );
            copyOfModelList.removeAll( _sdObject.getDependencies( ) );
            _availableObjectsList = new JList( copyOfModelList.toArray( ) );
        }
    }

    /**
     * Creates the {@link #_addButton}.
     */
    private void buildAddButton ( )
    {
        _addButton = new JButton( ResourceManager.getString( DepedencyEditor.class, "addButtonText" ) );
        _addButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                ISdObject selectedObject = (ISdObject) _availableObjectsList.getSelectedValue( );
                if ( selectedObject != null )
                {
                    _sdObject.addDependency( selectedObject );
                    setVisible( false );
                }
            }

        } );
    }
}
