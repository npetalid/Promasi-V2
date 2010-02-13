package org.promasi.ui.gmtools.coredesigner.equationbuilders;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.promasi.core.EquationType;
import org.promasi.core.IEquation;
import org.promasi.core.ISdObject;
import org.promasi.core.equations.LookupEquation;
import org.promasi.ui.gmtools.coredesigner.IEquationBuilder;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;


/**
 * 
 * Builder for the {@link LookupEquation}.
 * 
 * @author eddiefullmetal
 * 
 */
public class LookupEquationBuilder
        extends JXPanel
        implements IEquationBuilder
{

    /**
     * The {@link SdObjectDecorator} to get the dependencies from.
     */
    private SdObjectDecorator _sdObject;

    /**
     * The {@link ChartPanel} that holds the lookup graph.
     */
    private ChartPanel _chartPanel;

    /**
     * The data for the graph.
     */
    private XYSeries _xySeries;

    /**
     * A {@link JList} that displays the dependencies of the {@link #_sdObject}.
     */
    private JList _dependenciesList;

    /**
     * A {@link JButton} that when clicked adds the selected point in the
     * {@link #_chartPanel} to the chart.
     */
    private JButton _addPointButton;

    /**
     * A {@link JButton} that when clicked clears all the points in the
     * {@link #_xySeries}.
     */
    private JButton _clearAllButton;

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link #_sdObject}.
     */
    public LookupEquationBuilder( SdObjectDecorator sdObject )
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
        _dependenciesList = new JList( _sdObject.getActualSdObject( ).getDependencies( ).toArray( ) );
        buildGraph( );
        buildAddPointButton( );
        buildClearAllButton( );
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );

        JScrollPane dependenciesScrollPane = new JScrollPane( _dependenciesList );
        dependenciesScrollPane.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( LookupEquationBuilder.class,
                "dependenciesListTitle" ) ) );
        add( dependenciesScrollPane, new CC( ).grow( ).spanY( ) );

        JToolBar buttonToolBar = new JToolBar( );
        buttonToolBar.setFloatable( false );
        buttonToolBar.add( _addPointButton );
        buttonToolBar.add( _clearAllButton );
        add( buttonToolBar, new CC( ).growX( ).wrap( ) );

        add( _chartPanel, new CC( ).grow( ).skip( 1 ) );

    }

    /**
     * Creates the {@link #_chartPanel} and the {@link #_xySeries}.
     */
    private void buildGraph ( )
    {
        _xySeries = new XYSeries( "Input" );
        XYSeriesCollection collection = new XYSeriesCollection( _xySeries );
        JFreeChart chart = ChartFactory.createXYLineChart( StringUtils.EMPTY, "Input", "Output", collection, PlotOrientation.VERTICAL, false, false,
                false );

        chart.getXYPlot( ).setRangeCrosshairVisible( true );
        chart.getXYPlot( ).setDomainCrosshairVisible( true );

        // This is required because if data exists in the xyseries the crosshair
        // will not move unless we click to a point that is on the xyseries.
        chart.getXYPlot( ).setRangeCrosshairLockedOnData( false );
        chart.getXYPlot( ).setDomainCrosshairLockedOnData( false );

        // Do this so that the chart won't focus on the data area only. If the
        // bounds are not set and we add a new point the chart will focus on
        // that point.
        NumberAxis axisBounds = new NumberAxis( );
        axisBounds.setUpperBound( -10 );
        axisBounds.setLowerBound( 10 );
        chart.getXYPlot( ).setRangeAxis( axisBounds );
        chart.getXYPlot( ).setDomainAxis( axisBounds );

        _chartPanel = new ChartPanel( chart );
        _chartPanel.addMouseListener( new MouseAdapter( )
        {

            @Override
            public void mouseClicked ( MouseEvent e )
            {
                _chartPanel.getChart( ).handleClick( e.getX( ), e.getY( ), _chartPanel.getChartRenderingInfo( ) );
            }

        } );
    }

    /**
     * Creates the {@link #_addPointButton}.
     */
    private void buildAddPointButton ( )
    {
        _addPointButton = new JButton( ResourceManager.getString( LookupEquationBuilder.class, "addPointText" ) );
        _addPointButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                double xValue = _chartPanel.getChart( ).getXYPlot( ).getDomainCrosshairValue( );
                double yValue = _chartPanel.getChart( ).getXYPlot( ).getRangeCrosshairValue( );

                _xySeries.add( xValue, yValue );
            }

        } );
    }

    /**
     * Creates the {@link #_clearAllButton}.
     */
    private void buildClearAllButton ( )
    {
        _clearAllButton = new JButton( ResourceManager.getString( LookupEquationBuilder.class, "clearAllText" ) );
        _clearAllButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                _xySeries.clear( );
            }

        } );

    }

    @Override
    public IEquation buildEquation ( )
    {
        if ( _dependenciesList.getSelectedValue( ) != null )
        {
            TreeMap<Double, Double> valueMap = new TreeMap<Double, Double>( );

            List<XYDataItem> dataItems = _xySeries.getItems( );
            for ( XYDataItem dataItem : dataItems )
            {
                valueMap.put( dataItem.getXValue( ), dataItem.getYValue( ) );
            }

            return new LookupEquation( valueMap, (ISdObject) _dependenciesList.getSelectedValue( ) );
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setCurrentEquation ( IEquation equation )
    {
        if ( equation.getType( ).equals( EquationType.Lookup ) )
        {
            LookupEquation lookupEquation = (LookupEquation) equation;
            _dependenciesList.setSelectedValue( lookupEquation.getDependentObject( ), true );

            double[] xValues = lookupEquation.getXValues( );
            double[] yValues = lookupEquation.getYValues( );

            for ( int i = 0; i < xValues.length; i++ )
            {
                _xySeries.add( xValues[i], yValues[i] );
            }
        }
    }
}
