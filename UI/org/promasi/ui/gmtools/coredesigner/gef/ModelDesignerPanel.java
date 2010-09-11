package org.promasi.ui.gmtools.coredesigner.gef;


import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;
import java.util.Map;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.promasi.core.ISdObject;
import org.promasi.ui.gmtools.coredesigner.AbstractModelDesigner;
import org.promasi.ui.gmtools.coredesigner.IModelDesignerListener;
import org.promasi.ui.gmtools.coredesigner.model.SdModelInfo;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.event.GraphSelectionEvent;
import org.tigris.gef.event.GraphSelectionListener;
import org.tigris.gef.graph.presentation.DefaultGraphModel;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.presentation.ArrowHeadTriangle;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigEdgePoly;
import org.tigris.gef.presentation.FigNode;


/**
 * Panel that is used for designing a model.
 * 
 * @author eddiefullmetal
 * 
 */
public class ModelDesignerPanel
        extends AbstractModelDesigner
        implements GraphSelectionListener, PropertyChangeListener
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The {@link Editor} to use.
     */
    private Editor _editor;

    /**
     * The {@link JGraph} to use.
     */
    private JGraph _graph;

    /**
     * A map that keeps the relation between an {@link SdObjectDecorator} and a
     * {@link Fig}.
     */
    private Map<ISdObject, FigNode> _objectFigs;

    /**
     * A map that keeps the connection between the {@link SdObjectDecorator} and
     * its dependencies.
     */
    private Map<ISdObject, Map<ISdObject, Fig>> _objectDependencyFigs;

    private static String DESIGNER_X_PROPERTY = "designerX";
    private static String DESIGNER_Y_PROPERTY = "designerY";

    /**
     * Initializes the object.
     * 
     * @param modelInfo
     *            The {@link #_modelInfo}.
     */
    public ModelDesignerPanel( SdModelInfo modelInfo )
    {
        super( modelInfo );
        Globals.setShowFigTips( false );
        _objectFigs = new Hashtable<ISdObject, FigNode>( );
        _objectDependencyFigs = new Hashtable<ISdObject, Map<ISdObject, Fig>>( );
        initializeComponents( );
        initializeLayout( );
        for ( SdObjectDecorator sdObjectDecorator : modelInfo.getISdObjects( ) )
        {
            ISdObject sdObject = sdObjectDecorator.getActualSdObject( );
            Integer x = (Integer) sdObject.getProperty( DESIGNER_X_PROPERTY );
            Integer y = (Integer) sdObject.getProperty( DESIGNER_Y_PROPERTY );
            if ( x == null )
            {
                x = 0;
            }
            if ( y == null )
            {
                y = 0;
            }
            createSdObject( sdObjectDecorator, new Point( x, y ) );
        }
        for ( SdObjectDecorator sdObjectDecorator : modelInfo.getISdObjects( ) )
        {
            propertyChange( new PropertyChangeEvent( sdObjectDecorator, SdObjectDecorator.DEPENDENCIES_PROPERTY, null, sdObjectDecorator
                    .getDependencies( ) ) );
        }
    }

    /**
     * Initializes all the ui components.
     */
    private void initializeComponents ( )
    {
        setDropTarget( new DropTarget( this, this ) );
        buildJGraph( );
    }

    /**
     * Places the ui components to the panel.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _graph, new CC( ).grow( ) );
    }

    /**
     * Creates the {@link #_editor}.
     */
    private void buildEditor ( )
    {
        if ( _editor == null )
        {
            _editor = new Editor( new DefaultGraphModel( ) );
            _editor.getSelectionManager( ).addGraphSelectionListener( this );
        }
    }

    /**
     * Creates the {@link #_graph}.
     */
    private void buildJGraph ( )
    {
        if ( _graph == null )
        {
            buildEditor( );
            _graph = new JGraph( _editor );
        }
    }

    @Override
    protected void createSdObject ( SdObjectDecorator createdSdObject, Point droppedLocation )
    {
        // Create the ui representation of the ISdObject.
        AbstractSdObjectFig fig = SdObjectFigFactory.getSdObjectPanel( createdSdObject );
        if ( fig != null )
        {
            fig.setLocation( droppedLocation.x + _graph.getViewPosition( ).x, droppedLocation.y + _graph.getViewPosition( ).y );
            fig.setSize( 200, 100 );
            fig.addPropertyChangeListener( this );
            _editor.add( fig );
            _objectFigs.put( createdSdObject.getActualSdObject( ), fig );
            createdSdObject.addPropertyChangeListener( this );
        }
    }

    @Override
    public void removeSdObject ( SdObjectDecorator sdObject )
    {
        Fig fig = _objectFigs.get( sdObject.getActualSdObject( ) );
        _editor.remove( fig );
        _objectDependencyFigs.remove( sdObject.getActualSdObject( ) );
    }

    @Override
    public void selectionChanged ( GraphSelectionEvent gse )
    {
        SdObjectDecorator sdObject = null;
        if ( gse.getSelections( ).size( ) == 1 )
        {
            Object obj = gse.getSelections( ).get( 0 );
            if ( obj instanceof AbstractSdObjectFig )
            {
                sdObject = ( (AbstractSdObjectFig) obj ).getSdObject( );
            }

        }
        for ( IModelDesignerListener listener : getListeners( ) )
        {
            listener.sdObjectSelected( sdObject );
        }
    }

    @Override
    public void propertyChange ( PropertyChangeEvent evt )
    {
        if ( evt.getSource( ) instanceof SdObjectDecorator )
        {
            if ( evt.getPropertyName( ).equals( SdObjectDecorator.DEPENDENCIES_PROPERTY ) )
            {
                ISdObject sdObject = ( (SdObjectDecorator) evt.getSource( ) ).getActualSdObject( );
                Map<ISdObject, Fig> dependencyConnections = _objectDependencyFigs.get( sdObject );
                if ( dependencyConnections == null )
                {
                    dependencyConnections = new Hashtable<ISdObject, Fig>( );
                    _objectDependencyFigs.put( sdObject, dependencyConnections );
                }
                // Add all connection figs for all the dependencies
                for ( ISdObject dependencyObject : sdObject.getDependencies( ) )
                {
                    FigEdgePoly connectionFig = (FigEdgePoly) dependencyConnections.get( dependencyObject );
                    if ( connectionFig == null )
                    {
                        connectionFig = new FigEdgePoly( );
                        FigNode sourceFigNode = _objectFigs.get( dependencyObject );
                        FigNode destFigNode = _objectFigs.get( sdObject );
                        connectionFig.setSourceFigNode( sourceFigNode );
                        connectionFig.setSourcePortFig( (Fig) sourceFigNode.getOwner( ) );
                        connectionFig.setDestFigNode( destFigNode );
                        connectionFig.setDestPortFig( (Fig) destFigNode.getOwner( ) );
                        connectionFig.setDestArrowHead( new ArrowHeadTriangle( ) );
                        connectionFig.setBetweenNearestPoints( true );
                        connectionFig.computeRouteImpl( );
                        _editor.add( connectionFig );
                        dependencyConnections.put( dependencyObject, connectionFig );
                    }
                }
                // Remove figs that are not a dependency
                for ( ISdObject dependencyObject : dependencyConnections.keySet( ) )
                {
                    if ( !sdObject.getDependencies( ).contains( dependencyObject ) )
                    {
                        Fig connectionFig = dependencyConnections.get( dependencyObject );
                        connectionFig.removeFromDiagram( );
                        dependencyConnections.remove( dependencyObject );
                    }
                }
            }
        }
        else if ( evt.getSource( ) instanceof AbstractSdObjectFig )
        {
            AbstractSdObjectFig fig = (AbstractSdObjectFig) evt.getSource( );
            ISdObject sdObject = fig.getSdObject( ).getActualSdObject( );
            sdObject.addProperty( DESIGNER_X_PROPERTY, fig.getX( ) );
            sdObject.addProperty( DESIGNER_Y_PROPERTY, fig.getY( ) );
        }

    }
}
