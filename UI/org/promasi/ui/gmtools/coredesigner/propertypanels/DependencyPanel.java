package org.promasi.ui.gmtools.coredesigner.propertypanels;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXPanel;
import org.promasi.core.ISdObject;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.ui.gmtools.coredesigner.IModelDesignerListener;
import org.promasi.ui.gmtools.coredesigner.editors.DepedencyEditor;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * A panel that can edit the dependencies of an {@link AbstractSdObject}.
 * 
 * @author eddiefullmetal
 * 
 */
public class DependencyPanel
        extends JXPanel
        implements IModelDesignerListener
{

    /**
     * The {@link SdObjectDecorator} that this properties panel is displaying.
     */
    private SdObjectDecorator _sdObject;

    /**
     * The {@link JList} that contains the dependencies of the
     * {@link #_sdObject}.
     */
    private JList _dependencyList;

    /**
     * A {@link JButton} that when clicked it adds a dependency to the object.
     */
    private JButton _addButton;

    /**
     * A {@link JButton} that when clicked it removes the selected dependency
     * from the object.
     */
    private JButton _removeButton;

    /**
     * Initializes the object.
     */
    public DependencyPanel( )
    {
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes all the ui components.
     */
    protected void initializeComponents ( )
    {
        _dependencyList = new JList( );
        _addButton = new JButton( ResourceManager.getIcon( "add" ) );
        _addButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                DepedencyEditor dependencyEditor = new DepedencyEditor( _sdObject );
                dependencyEditor.setModal( true );
                ScreenUtils.centerInScreen( dependencyEditor );
                dependencyEditor.setVisible( true );
                populateDependencyList( );
            }

        } );
        _removeButton = new JButton( ResourceManager.getIcon( "remove" ) );
        _removeButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                ISdObject sdObject = (ISdObject) _dependencyList.getSelectedValue( );
                if ( sdObject != null )
                {
                    _sdObject.removeDependency( sdObject );
                    populateDependencyList( );
                }
            }

        } );
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _addButton, new CC( ).split( 2 ) );
        add( _removeButton, new CC( ).wrap( ) );
        add( new JScrollPane( _dependencyList ), new CC( ).grow( ) );
    }

    @Override
    public void sdObjectSelected ( SdObjectDecorator sdObject )
    {
        _sdObject = sdObject;
        if ( sdObject == null )
        {
            _dependencyList.setVisible( false );
            _addButton.setVisible( false );
            _removeButton.setVisible( false );
        }
        else
        {
            _dependencyList.setVisible( true );
            _addButton.setVisible( true );
            _removeButton.setVisible( true );
            populateDependencyList( );
        }
    }

    /**
     * Populates the {@link #_dependencyList} from the {@link #_sdObject}.
     */
    private void populateDependencyList ( )
    {
        DefaultListModel model = new DefaultListModel( );
        for ( ISdObject dependencyObject : _sdObject.getDependencies( ) )
        {
            model.addElement( dependencyObject );
        }
        _dependencyList.setModel( model );
    }
}
