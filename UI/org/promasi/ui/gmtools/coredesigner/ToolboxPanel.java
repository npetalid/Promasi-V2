package org.promasi.ui.gmtools.coredesigner;


import javax.swing.BorderFactory;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.promasi.core.sdobjects.FlowSdObject;
import org.promasi.core.sdobjects.OutputSdObject;
import org.promasi.core.sdobjects.StockSdObject;
import org.promasi.core.sdobjects.VariableSdObject;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;
import org.promasi.utilities.ui.DragDropUtil;


/**
 * {@link JXPanel} that contains all the needed tools for the creation of a
 * model.
 * 
 * @author eddiefullmetal
 * 
 */
public class ToolboxPanel
        extends JXPanel
{

    /**
     * A label that displays the components message.
     */
    private JXLabel _componentsLabel;

    /**
     * Button for creating {@link StockSdObject}s.
     */
    private JXButton _stockButton;

    /**
     * Button for creating {@link FlowSdObject}s.
     */
    private JXButton _flowButton;

    /**
     * Button for creating {@link VariableSdObject}s.
     */
    private JXButton _variableButton;

    /**
     * Button for creating {@link OutputSdObject}s.
     */
    private JXButton _outputButton;

    /**
     * Initializes the object.
     */
    public ToolboxPanel( )
    {
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes all the ui components.
     */
    private void initializeComponents ( )
    {
        buildComponentsLabel( );
        buildFlowButton( );
        buildOutputButton( );
        buildStockButton( );
        buildVariableButton( );
    }

    /**
     * Places the ui components to the panel.
     */
    private void initializeLayout ( )
    {
        setBorder( BorderFactory.createEtchedBorder( ) );
        MigLayout layout = new MigLayout( new LC( ).fill( ) );
        setLayout( layout );
        add( _componentsLabel, new CC( ).dockNorth( ) );
        add( _stockButton, new CC( ).wrap( ).growX( ) );
        add( _flowButton, new CC( ).wrap( ).growX( ) );
        add( _variableButton, new CC( ).wrap( ).growX( ) );
        add( _outputButton, new CC( ).growX( ) );
    }

    /**
     * Creates the {@link #_componentsLabel}.
     */
    private void buildComponentsLabel ( )
    {
        _componentsLabel = new JXLabel( );
        _componentsLabel.setText( ResourceManager.getString( ToolboxPanel.class, "componentsText" ) );
        _componentsLabel.setIcon( ResourceManager.getIcon( "components" ) );
        _componentsLabel.setBorder( BorderFactory.createEtchedBorder( ) );
    }

    /**
     * Creates the {@link #_stockButton}.
     */
    private void buildStockButton ( )
    {
        _stockButton = new JXButton( ResourceManager.getString( ToolboxPanel.class, "stockText" ) );
        DragDropUtil.makeDraggable( _stockButton, StockSdObject.class.getName( ) );
    }

    /**
     * Creates the {@link #_flowButton}.
     */
    private void buildFlowButton ( )
    {
        _flowButton = new JXButton( ResourceManager.getString( ToolboxPanel.class, "flowText" ) );
        DragDropUtil.makeDraggable( _flowButton, FlowSdObject.class.getName( ) );
    }

    /**
     * Creates the {@link #_variableButton}.
     */
    private void buildVariableButton ( )
    {
        _variableButton = new JXButton( ResourceManager.getString( ToolboxPanel.class, "variableText" ) );
        DragDropUtil.makeDraggable( _variableButton, VariableSdObject.class.getName( ) );
    }

    /**
     * Creates the {@link #_outputButton}.
     */
    private void buildOutputButton ( )
    {
        _outputButton = new JXButton( ResourceManager.getString( ToolboxPanel.class, "outputText" ) );
        DragDropUtil.makeDraggable( _outputButton, OutputSdObject.class.getName( ) );
    }
}
