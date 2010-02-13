package org.promasi.ui.gmtools.coredesigner.propertypanels;


import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXPanel;
import org.promasi.ui.gmtools.coredesigner.IModelDesignerListener;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;

import com.jidesoft.swing.JideTabbedPane;


/**
 * 
 * The main container for all the property panels.
 * 
 * @author eddiefullmetal
 * 
 */
public class MainPropertiesPanel
        extends JXPanel
        implements IModelDesignerListener
{

    /**
     * The main bar.
     */
    private JideTabbedPane _mainBar;

    /**
     * The {@link PropertyPanel} that displays all the properties of an
     * {@link SdObjectDecorator}.
     */
    private PropertyPanel _propertyPanel;

    /**
     * The {@link DependencyPanel} that displays all the dependencies of an
     * {@link SdObjectDecorator}.
     */
    private DependencyPanel _dependencyPanel;

    /**
     * The {@link EventPanel} that displays all the events of an
     * {@link SdObjectDecorator}.
     */
    private EventPanel _eventPanel;

    /**
     * Initializes the object.
     */
    public MainPropertiesPanel( )
    {
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes all the ui components.
     */
    protected void initializeComponents ( )
    {
        _propertyPanel = new PropertyPanel( );
        _dependencyPanel = new DependencyPanel( );
        _eventPanel = new EventPanel( );

        _mainBar = new JideTabbedPane( );
        _mainBar.addTab( ResourceManager.getString( MainPropertiesPanel.class, "propertiesText" ), _propertyPanel );
        _mainBar.addTab( ResourceManager.getString( MainPropertiesPanel.class, "dependenciesText" ), _dependencyPanel );
        _mainBar.addTab( ResourceManager.getString( MainPropertiesPanel.class, "eventsText" ), _eventPanel );

        sdObjectSelected( null );
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _mainBar, new CC( ).grow( ) );
    }

    @Override
    public void sdObjectSelected ( SdObjectDecorator sdObject )
    {
        _propertyPanel.sdObjectSelected( sdObject );
        _dependencyPanel.sdObjectSelected( sdObject );
        _eventPanel.sdObjectSelected( sdObject );
    }

}
