package org.promasi.ui.promasiui.promasidesktop.singleplayerscoremode.projectFinishedUi;


import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.promasi.core.ISdObject;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.core.SdObjectInfo;
import org.promasi.core.SdSystem;
import org.promasi.model.Clock;
import org.promasi.model.Project;
import org.promasi.shell.Shell;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 *
 * Panel that displays info about certain {@link ISdObject}s of the
 * {@link SdSystem}.
 *
 * @author eddiefullmetal
 *
 */
public class SdModelInfoPanel
        extends JPanel
        implements ListSelectionListener
{

    /**
     * The {@link IStatePersister} of the project.
     */
    private IStatePersister _statePersister;

    /**
     * A list that displays all the {@link ISdObject}s that have an
     * {@link SdObjectInfo}.
     */
    private JList _sdObjectList;

    /**
     * A {@link JTextArea} that displays the description of the
     * {@link SdObjectInfo}.
     */
    private JTextArea _descriptionArea;

    /**
     * A {@link JTextArea} that displays the hint of the {@link SdObjectInfo}.
     */
    private JTextArea _hintArea;

    /**
     * The {@link JPanel} that contains the chart for the selected
     * {@link ISdObject}.
     */
    private JPanel _chartPanelContainer;

    private Shell _shell;

    /**
     * Initializes the object.
     *
     */
    public SdModelInfoPanel(Shell shell )throws NullArgumentException
    {
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
    	_shell=shell;
    	
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( new JScrollPane( getSdObjectList( ) ), new CC( ).spanY( ).minWidth( "100px" ).growY( ) );
        add( new JScrollPane( getChartPanelContainer( ) ), new CC( ).grow( ).spanY( ) );
        add( new JScrollPane( getDescriptionArea( ) ), new CC( ).growY( ).minWidth( "200px" ).maxWidth( "200px" ).alignX( "trailing" ).wrap( ) );
        add( new JScrollPane( getHintArea( ) ), new CC( ).growY( ).minWidth( "200px" ).maxWidth( "200px" ).alignX( "trailing" ).wrap( ) );
    }

    /**
     * @return the {@link #_sdObjectList}.
     */
    public JList getSdObjectList ( )
    {
        if ( _sdObjectList == null )
        {
            _sdObjectList = new JList( );
            _sdObjectList.getSelectionModel( ).addListSelectionListener( this );
        }
        return _sdObjectList;
    }

    /**
     * @return the {@link #_hintArea}.
     */
    public JTextArea getHintArea ( )
    {
        if ( _hintArea == null )
        {
            _hintArea = new JTextArea( );
            _hintArea.setLineWrap( true );
            _hintArea.setWrapStyleWord( true );
            _hintArea.setEditable( false );
            _hintArea.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( SdModelInfoPanel.class, "hintText" ) ) );
        }
        return _hintArea;
    }

    /**
     * @return the {@link #_descriptionArea}.
     */
    public JTextArea getDescriptionArea ( )
    {
        if ( _descriptionArea == null )
        {
            _descriptionArea = new JTextArea( );
            _descriptionArea.setLineWrap( true );
            _descriptionArea.setWrapStyleWord( true );
            _descriptionArea.setEditable( false );
            _descriptionArea.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( SdModelInfoPanel.class, "descriptionText" ) ) );
        }
        return _descriptionArea;
    }

    /**
     * @return the {@link #_chartPanelContainer}.
     */
    public JPanel getChartPanelContainer ( )
    {
        if ( _chartPanelContainer == null )
        {
            _chartPanelContainer = new JPanel( );
            _chartPanelContainer.setLayout( new MigLayout( new LC( ).fill( ) ) );
        }
        return _chartPanelContainer;
    }

    /**
     * @param project
     *            The {@link Project} to display the data for.
     *
     */
    public void setProject ( Project project )
    {
        SdModel model = _shell.getCurrentPlayMode( ).getModelForProject( project );
        _statePersister = _shell.getCurrentPlayMode( ).getPersisterForProject( project );
        _sdObjectList.setModel( new SdObjectListModel( model ) );
    }

    /**
     * Creates and adds the {@link ChartPanel} to the
     * {@link #_chartPanelContainer} using the specified sdObject.
     *
     * @param sdObject
     */
    private void createChartPanel ( ISdObject sdObject )
    {
        XYSeriesCollection collection = new XYSeriesCollection( );

        XYSeries xySeries = new XYSeries( "value" );
        List<Double> times = _statePersister.getTimeSteps( sdObject.getKey( ) );
        for ( Double time : times )
        {
            double step = time;
            double value = _statePersister.getValue( sdObject.getKey( ), time );
            xySeries.add( step, value );
        }

        collection.addSeries( xySeries );
        JFreeChart chart = ChartFactory.createXYLineChart( sdObject.getKey( ), "step(" + Clock.getInstance( ).getFieldType( ) + ")", "value",
                collection, PlotOrientation.VERTICAL, false, false, false );

        _chartPanelContainer.removeAll( );
        _chartPanelContainer.add( new ChartPanel( chart ), new CC( ).grow( ) );
    }

    @Override
    public void valueChanged ( ListSelectionEvent e )
    {
        ISdObject selectedObject = ( (SdObjectListModel) _sdObjectList.getModel( ) ).getSdObjectAt( _sdObjectList.getSelectedIndex( ) );
        _descriptionArea.setText( selectedObject.getInfo( ).getDescription( ) );
        _hintArea.setText( selectedObject.getInfo( ).getHint( ) );
        createChartPanel( selectedObject );
    }
}
