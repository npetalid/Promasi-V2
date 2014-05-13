package org.promasi.coredesigner.ui.dialogs;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;
/**
 * 
 * @author antoxron
 *
 */
public class LookupEquationDialog extends ApplicationWindow implements SelectionListener {
	
	
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Button _addPointButton;
	private Button _clearButton;
	private TreeMap<Double,Double> _xyPoints;
	private XYSeries _xySeries;
	private ChartComposite _chartComposite;
	private boolean _saveSettings = false;

	
	public LookupEquationDialog(TreeMap<Double,Double> xyPoints) {
		super(null);
		setShellStyle(SWT.SYSTEM_MODAL | SWT.TITLE);
		_xyPoints = xyPoints;
		if (_xyPoints == null) {
			_xyPoints = new TreeMap<Double,Double>();
			
			
		}
	}

	public boolean saveSettings() {
		return _saveSettings;
	}
	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final ScrolledForm scrldfrmLookupEquation = formToolkit.createScrolledForm(container);
		formToolkit.paintBordersFor(scrldfrmLookupEquation);
		scrldfrmLookupEquation.setText("Lookup Equation");
		scrldfrmLookupEquation.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(scrldfrmLookupEquation.getBody(), SWT.NONE);
		sashForm.setOrientation(SWT.VERTICAL);
		formToolkit.adapt(sashForm);
		formToolkit.paintBordersFor(sashForm);
		
		Composite topContainer = new Composite(sashForm, SWT.NONE);
		formToolkit.adapt(topContainer);
		formToolkit.paintBordersFor(topContainer);
		topContainer.setLayout(new GridLayout(2, false));
		
		_addPointButton = new Button(topContainer, SWT.NONE);
		formToolkit.adapt(_addPointButton, true, true);
		_addPointButton.setText("Add Point");
		_addPointButton.addSelectionListener(this);
		
		_clearButton = new Button(topContainer, SWT.NONE);
		formToolkit.adapt(_clearButton, true, true);
		_clearButton.setText("Clear");
		_clearButton.addSelectionListener(this);
		
		Composite bottomContainer = new Composite(sashForm, SWT.NONE);
		formToolkit.adapt(bottomContainer);
		formToolkit.paintBordersFor(bottomContainer);
		bottomContainer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		//////////////////////
		// for graph
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
		
        _chartComposite = new ChartComposite(bottomContainer, SWT.NONE, chart,true);
        _chartComposite.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) 
			{
				
			}
			@Override
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) 
			{
				_chartComposite.getChart( ).handleClick( e.x, e.y, _chartComposite.getChartRenderingInfo( ) );
			}
			@Override
			public void mouseUp(org.eclipse.swt.events.MouseEvent e)
			{				
				
			}
			
		});
        
        
		///////////////////////
		

        scrldfrmLookupEquation.getToolBarManager().add(new Action("Save") {  
            public void run() {  
            
            		_saveSettings  =true;
            		scrldfrmLookupEquation.getShell().close();
            		
            }  
        });  
	    
        scrldfrmLookupEquation.getToolBarManager().add(new Action("Cancel") {  
            public void run() {  
            	scrldfrmLookupEquation.getShell().close();
            		
            }  
        });  
        scrldfrmLookupEquation.updateToolBar(); 
		sashForm.setWeights(new int[] {39, 189});
		
		loadXYPoints();
		return container;
	}

	
	private void loadXYPoints() {
		if (_xyPoints != null) {
			setXYPoints(_xyPoints);
		}
		
	}
	
	private void setXYPoints(TreeMap<Double,Double> xyPoints) {

		  Set<Double> keys = xyPoints.keySet();
		  Iterator<Double> it = keys.iterator();

		  while (it.hasNext()) {
			 double xPoint = Double.valueOf(it.next().toString()) ;
			 double yPoint = xyPoints.get(xPoint);
			 _xySeries.add(xPoint, yPoint);
		 }
		
		
	}
	public TreeMap<Double,Double> getXYPoints() {
		return _xyPoints;
	}
	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Equation Dialog");
		
		Rectangle displayBounds = newShell.getDisplay().getBounds();
		int nMinWidth = 450;
	    int nMinHeight = 300;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
	    newShell.setBounds(nLeft, nTop, nMinWidth, nMinHeight);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		
		Object source = e.getSource();
		if (source.equals(_addPointButton)) {
			 double xValue = _chartComposite.getChart( ).getXYPlot( ).getDomainCrosshairValue( );
	          double yValue = _chartComposite.getChart( ).getXYPlot( ).getRangeCrosshairValue( );
	          _xySeries.add( xValue, yValue );
	          
	          _xyPoints.put(xValue, yValue);	
		}
		else if (source.equals(_clearButton)) {
			 _xySeries.clear( );
	          _xyPoints.clear();
		}
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
