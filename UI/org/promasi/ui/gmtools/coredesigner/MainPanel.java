package org.promasi.ui.gmtools.coredesigner;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;
import org.promasi.ui.gmtools.coredesigner.actions.LoadSdModelAction;
import org.promasi.ui.gmtools.coredesigner.actions.NewSdModelAction;
import org.promasi.ui.gmtools.coredesigner.controllers.IShellListener;
import org.promasi.ui.gmtools.coredesigner.controllers.Shell;
import org.promasi.ui.gmtools.coredesigner.gef.ModelDesignerPanel;
import org.promasi.ui.gmtools.coredesigner.model.SdModelInfo;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.ui.gmtools.coredesigner.propertypanels.MainPropertiesPanel;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;
import org.promasi.utilities.ui.FileDrop;

import com.jidesoft.swing.JideTabbedPane;


/**
 * 
 * The panel that holds all the components.
 * 
 * @author eddiefullmetal
 * 
 */
public class MainPanel
        extends JXPanel
        implements IShellListener
{

    /**
     * The main toolbar that contains the basic controlls.
     */
    private JToolBar _mainToolbar;

    /**
     * Button that creates a new model.
     */
    private JXButton _newButton;

    /**
     * Button that saves the current model.
     */
    private JXButton _saveButton;

    /**
     * Button that loads a model.
     */
    private JXButton _loadButton;

    /**
     * Button that exits the application.
     */
    private JXButton _exitButton;

    /**
     * This tabbed pane keeps all open editors.
     */
    private JideTabbedPane _tabbedPane;

    /**
     * The {@link ToolboxPanel}.
     */
    private ToolboxPanel _toolboxPanel;

    /**
     * The {@link MainPropertiesPanel}.
     */
    private MainPropertiesPanel _propertiesPanel;

    /**
     * The {@link JSplitPane} for the {@link #_toolboxPanel} and the
     * {@link #_propertiesPanel}.
     */
    private JSplitPane _splitPane;

    /**
     * Each {@link AbstractModelDesigner} which {@link SdModelInfo} it edits.
     */
    private Map<SdModelInfo, AbstractModelDesigner> _designers;

    /**
     * Initializes the object and builds all needed ui components.
     */
    public MainPanel( )
    {
        // Register as an IShellListener.
        _designers = new Hashtable<SdModelInfo, AbstractModelDesigner>( );
        Shell.addListener( this );
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes all the ui components.
     */
    private void initializeComponents ( )
    {
        buildMainToolbar( );
        buildSplitPane( );
        buildTabPane( );
    }

    /**
     * Places the ui components to the panel.
     */
    private void initializeLayout ( )
    {
        MigLayout layout = new MigLayout( new LC( ).fill( ) );
        setLayout( layout );
        add( _mainToolbar, new CC( ).dockNorth( ) );
        add( _splitPane, new CC( ).minWidth( "20%" ).growY( ) );
        add( _tabbedPane, new CC( ).grow( ) );
    }

    /**
     * Creates the {@link #_mainToolbar}.
     */
    private void buildMainToolbar ( )
    {
        if ( _mainToolbar == null )
        {
            buildNewButton( );
            buildSaveButton( );
            buildLoadButton( );
            buildExitButton( );
            _mainToolbar = new JToolBar( );
            _mainToolbar.setFloatable( false );
            _mainToolbar.add( _newButton );
            _mainToolbar.add( _saveButton );
            _mainToolbar.add( _loadButton );
            _mainToolbar.add( Box.createHorizontalGlue( ) );
            _mainToolbar.add( _exitButton );
        }
    }

    /**
     * Creates the {@link #_newButton}.
     */
    private void buildNewButton ( )
    {
        if ( _newButton == null )
        {
            _newButton = new JXButton( );
            _newButton.setAction( new NewSdModelAction( ) );
            _newButton.setText( ResourceManager.getString( MainPanel.class, "newText" ) );
            _newButton.setIcon( ResourceManager.getIcon( "new" ) );
            _newButton.setHorizontalTextPosition( SwingConstants.CENTER );
            _newButton.setVerticalTextPosition( SwingConstants.BOTTOM );
        }
    }

    /**
     * Creates the {@link #_loadButton}.
     */
    private void buildLoadButton ( )
    {
        if ( _loadButton == null )
        {
            _loadButton = new JXButton( );
            _loadButton.setAction( new LoadSdModelAction( ) );
            _loadButton.setText( ResourceManager.getString( MainPanel.class, "loadText" ) );
            _loadButton.setIcon( ResourceManager.getIcon( "load" ) );
            _loadButton.setHorizontalTextPosition( SwingConstants.CENTER );
            _loadButton.setVerticalTextPosition( SwingConstants.BOTTOM );
            FileDrop drop = new FileDrop( _loadButton, BorderFactory.createLineBorder( Color.RED ), new FileDrop.Listener( )
            {

                @Override
                public void filesDropped ( File[] files )
                {
                    for ( File file : files )
                    {
                        Shell.loadModel( file );
                    }
                }

            } );
        }
    }

    /**
     * Creates the {@link #_exitButton}.
     */
    private void buildExitButton ( )
    {
        if ( _exitButton == null )
        {
            _exitButton = new JXButton( );
            _exitButton.addActionListener( new ActionListener( )
            {

                @Override
                public void actionPerformed ( ActionEvent e )
                {
                    System.exit( 0 );
                }
            } );
            _exitButton.setText( ResourceManager.getString( MainPanel.class, "exitText" ) );
            _exitButton.setIcon( ResourceManager.getIcon( "exit" ) );
            _exitButton.setHorizontalTextPosition( SwingConstants.CENTER );
            _exitButton.setVerticalTextPosition( SwingConstants.BOTTOM );
        }
    }

    /**
     * Creates the {@link #_saveButton}.
     */
    private void buildSaveButton ( )
    {
        if ( _saveButton == null )
        {
            _saveButton = new JXButton( );
            _saveButton.addActionListener( new ActionListener( )
            {

                @Override
                public void actionPerformed ( ActionEvent e )
                {
                    AbstractModelDesigner designer = getCurrentDesigner( );
                    if ( designer != null )
                    {
                        SdModelInfo info = getCurrentDesigner( ).getModelInfo( );
                        JFileChooser chooser = new JFileChooser( );
                        chooser.showSaveDialog( null );
                        if ( chooser.getSelectedFile( ) != null )
                        {
                            try
                            {
                                info.save( new FileOutputStream( chooser.getSelectedFile( ) ) );
                            }
                            catch ( FileNotFoundException e1 )
                            {
                            }
                        }
                    }
                }

            } );
            _saveButton.setText( ResourceManager.getString( MainPanel.class, "saveText" ) );
            _saveButton.setIcon( ResourceManager.getIcon( "save" ) );
            _saveButton.setHorizontalTextPosition( SwingConstants.CENTER );
            _saveButton.setVerticalTextPosition( SwingConstants.BOTTOM );
        }
    }

    /**
     * Creates the {@link #_tabbedPane}.
     */
    private void buildTabPane ( )
    {
        if ( _tabbedPane == null )
        {
            _tabbedPane = new JideTabbedPane( );
            _tabbedPane.setBoldActiveTab( true );
        }
    }

    /**
     * Creates the {@link #_toolboxPanel}.
     */
    private void buildToolboxPanel ( )
    {
        if ( _toolboxPanel == null )
        {
            _toolboxPanel = new ToolboxPanel( );
        }
    }

    /**
     * Creates the {@link #_splitPane}.
     */
    private void buildSplitPane ( )
    {
        if ( _splitPane == null )
        {
            buildToolboxPanel( );
            _propertiesPanel = new MainPropertiesPanel( );
            _splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
            _splitPane.setTopComponent( _toolboxPanel );
            _splitPane.setBottomComponent( _propertiesPanel );
        }
    }

    /**
     * Gets the current {@link ModelDesignerPanel}.
     * 
     * @return The current {@link ModelDesignerPanel} or null if there is no
     *         editor.
     */
    public AbstractModelDesigner getCurrentDesigner ( )
    {
        JScrollPane pane = ( (JScrollPane) _tabbedPane.getSelectedComponent( ) );
        if ( pane != null )
        {
            return (AbstractModelDesigner) pane.getViewport( ).getView( );
        }
        else
        {
            return null;
        }
    }

    @Override
    public void modelCreated ( SdModelInfo sdModelInfo )
    {
        AbstractModelDesigner designerPanel = new ModelDesignerPanel( sdModelInfo );
        designerPanel.addListener( _propertiesPanel );
        _designers.put( sdModelInfo, designerPanel );
        _tabbedPane.addTab( sdModelInfo.getName( ), ResourceManager.getIcon( "model" ), new JScrollPane( designerPanel ) );
    }

    @Override
    public void objectRemoved ( SdModelInfo modelInfo, SdObjectDecorator obj )
    {
        AbstractModelDesigner designer = _designers.get( modelInfo );
        designer.removeSdObject( obj );
    }
}
