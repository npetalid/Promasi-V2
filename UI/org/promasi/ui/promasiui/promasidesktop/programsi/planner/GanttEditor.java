package org.promasi.ui.promasiui.promasidesktop.programsi.planner;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import org.joda.time.LocalDate;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * 
 * 
 * 
 * @author eddiefullmetal
 * 
 */
public class GanttEditor
        extends JPanel
        implements MouseMotionListener
{

    /**
     * The height of each row.
     */
    private static final int ROW_HEIGHT = 20;

    /**
     * The {@link Color} for the row separators.
     */
    private Color _lineSeparatorColor;

    /**
     * The header of the editor.
     */
    private IGanttHeader _header;

    /**
     * The date that the gantt will start.
     */
    private LocalDate _startDate;

    /**
     * The date that the gantt will end.
     */
    private LocalDate _endDate;

    /**
     * Flag determining if the row separators will be drawn.
     */
    private boolean _drawRowSeparators;

    /**
     * Flag determining if the column separators will be drawn.
     */
    private boolean _drawColumnSeparators;

    /**
     * Contains all the {@link GanttTaskScheduleUi}.
     */
    private final List<GanttTaskScheduleUi> _taskSchedules;

    /**
     * Initializes the object.
     */
    public GanttEditor( )
    {
        // Set default values.
        _drawColumnSeparators = true;
        _drawRowSeparators = true;
        _lineSeparatorColor = new Color(0.0f,0.0f,0.0f,0.0f);
        _header = new DefaultGanttHeader( );
        setBackground( Color.WHITE );
        _taskSchedules = new Vector<GanttTaskScheduleUi>( );
        // -----------------
        addMouseMotionListener( this );
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the components.
     */
    private void initializeComponents ( )
    {

    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( null );
    }

    @Override
    public void paint ( Graphics g )
    {
        super.paint( g );
        Graphics2D g2d = (Graphics2D) g;
        _header.paint( g2d );
        if ( _drawRowSeparators )
        {
            drawRowSeperators( g2d );
        }
        if ( _drawColumnSeparators )
        {
            drawColumnSeparators( g2d );
        }
        drawGanttTaskSchedules( g2d );
    }

    /**
     * 
     * Draws vertical column separators.
     * 
     * @param g2d
     *            The {@link Graphics2D} to draw.
     */
    protected void drawColumnSeparators ( Graphics2D g2d )
    {
        g2d.setColor( _lineSeparatorColor );
        int totalColumns = _header.getTotalColumns( );
        for ( int i = 1; i <= totalColumns; i++ )
        {
            int x = i * _header.getColumnWidth( );
            g2d.drawLine( x, _header.getHeight( ), x, getHeight( ) );
        }
    }

    /**
     * 
     * Draws horizontal line separators for each row.
     * 
     * @param g2d
     *            The {@link Graphics2D} to draw.
     */
    protected void drawRowSeperators ( Graphics2D g2d )
    {
        g2d.setColor( _lineSeparatorColor );
        int visibleRows = getHeight( ) / ROW_HEIGHT;
        for ( int i = 1; i <= visibleRows; i++ )
        {
            int rowY = i * ROW_HEIGHT + _header.getHeight( );
            g2d.drawLine( 0, rowY, _header.getWidth( ), rowY );
        }
    }

    /**
     * Draws the {@link #_taskSchedules}.
     * 
     * @param g2d
     *            The {@link Graphics2D} to draw.
     */
    protected void drawGanttTaskSchedules ( Graphics2D g2d )
    {
        for ( int i = 0; i < _taskSchedules.size( ); i++ )
        {
            GanttTaskScheduleUi taskSchedule = _taskSchedules.get( i );
            Rectangle bounds = _header.calculateBounds( taskSchedule.getStartDate( ), taskSchedule.getEndDate( ) );
            bounds.height = ROW_HEIGHT - 5;
            bounds.y = ( i * ROW_HEIGHT + _header.getHeight( ) ) + 5;
            taskSchedule.setSize( bounds.getSize( ) );
            taskSchedule.setLocation( bounds.getLocation( ) );
            taskSchedule.paint( g2d );
        }
    }

    /**
     * @return the {@link #_lineSeparatorColor}.
     */
    public Color getLineSeparatorColor ( )
    {
        return _lineSeparatorColor;
    }

    /**
     * @param lineSeparatorColor
     *            the {@link #_lineSeparatorColor} to set.
     */
    public void setLineSeparatorColor ( Color lineSeparatorColor )
    {
        _lineSeparatorColor = lineSeparatorColor;
    }

    /**
     * @return the {@link #_header}.
     */
    public IGanttHeader getHeader ( )
    {
        return _header;
    }

    /**
     * @param header
     *            the {@link #_header} to set.
     */
    public void setHeader ( IGanttHeader header )
    {
        _header = header;
    }

    /**
     * @return the {@link #_startDate}.
     */
    public LocalDate getStartDate ( )
    {
        return _startDate;
    }

    /**
     * Also sets the start date of the {@link IGanttHeader}.
     * 
     * @param startDate
     *            the {@link #_startDate} to set.
     */
    public void setStartDate ( LocalDate startDate )
    {
        _startDate = startDate;
        _header.setStartDate( startDate );
    }

    /**
     * 
     * Also sets the end date of the {@link IGanttHeader}.
     * 
     * @return the {@link #_endDate}.
     */
    public LocalDate getEndDate ( )
    {
        return _endDate;
    }

    /**
     * @param endDate
     *            the {@link #_endDate} to set.
     */
    public void setEndDate ( LocalDate endDate )
    {
        _endDate = endDate;
        _header.setEndDate( endDate );
    }

    @Override
    public Dimension getPreferredSize ( )
    {
        return new Dimension( _header.getWidth( ) + 20, ( _taskSchedules.size( ) * ROW_HEIGHT ) + _header.getHeight( ) + 20 );
    }

    /**
     * @return the {@link #_drawRowSeparators}.
     */
    public boolean isDrawRowSeparators ( )
    {
        return _drawRowSeparators;
    }

    /**
     * @param drawRowSeparators
     *            the {@link #_drawRowSeparators} to set.
     */
    public void setDrawRowSeparators ( boolean drawRowSeparators )
    {
        _drawRowSeparators = drawRowSeparators;
    }

    /**
     * @return the {@link #_drawColumnSeparators}.
     */
    public boolean isDrawColumnSeparators ( )
    {
        return _drawColumnSeparators;
    }

    /**
     * @param drawColumnSeparators
     *            the {@link #_drawColumnSeparators} to set.
     */
    public void setDrawColumnSeparators ( boolean drawColumnSeparators )
    {
        _drawColumnSeparators = drawColumnSeparators;
    }

    /**
     * Changes the zoom level.
     * 
     * @param zoomLevel
     */
    public void changeZoomLevel ( int zoomLevel )
    {
        _header.setZoomLevel( zoomLevel );
        invalidate( );
    }

    /**
     * @return The current zoom level of the editor.
     */
    public int getCurrentZoomLevel ( )
    {
        return _header.getCurrentZoomLevel( );
    }

    /**
     * @return True if the editor can zoom in.
     */
    public boolean canZoomIn ( )
    {
        return _header.getCurrentZoomLevel( ) > _header.getMinZoomLevel( );
    }

    /**
     * @return True if the editor can zoom out.
     */
    public boolean canZoomOut ( )
    {
        return _header.getCurrentZoomLevel( ) < _header.getMaxZoomLevel( );
    }

    /**
     * @param taskScheduleUi
     *            The {@link GanttTaskScheduleUi} to add to the
     *            {@link #_taskSchedules}.
     */
    public void addTaskScheduleUi ( GanttTaskScheduleUi taskScheduleUi )
    {
        if ( !_taskSchedules.contains( taskScheduleUi ) )
        {
            _taskSchedules.add( taskScheduleUi );
            repaint( );
        }
    }

    /**
     * @param taskScheduleUi
     *            The {@link GanttTaskScheduleUi} to remove from the
     *            {@link #_taskSchedules}.
     */
    public void removeTaskScheduleUi ( GanttTaskScheduleUi taskScheduleUi )
    {
        if ( _taskSchedules.contains( taskScheduleUi ) )
        {
            _taskSchedules.remove( taskScheduleUi );
            repaint( );
        }
    }

    /**
     * Removes the {@link GanttTaskScheduleUi} that corresponds to the specified
     * taskSchedule.
     * 
     * @param taskSchedule
     */
    public void removeTaskSchedule ( GanttTaskSchedule taskSchedule )
    {
        Vector<GanttTaskScheduleUi> copyOfSchedules = new Vector<GanttTaskScheduleUi>( _taskSchedules );
        for ( GanttTaskScheduleUi taskScheduleUi : copyOfSchedules )
        {
            if ( taskScheduleUi.getTaskSchedule( ).equals( taskSchedule ) )
            {
                _taskSchedules.remove( taskScheduleUi );
            }
        }
    }

    /**
     * Clears all {@link GanttTaskSchedule} from the editor.
     */
    public void clear ( )
    {
        _taskSchedules.clear( );
    }

    /**
     * @return The row height.
     */
    public int getRowHeight ( )
    {
        return ROW_HEIGHT;
    }

    /**
     * @return The height of the {@link IGanttHeader}.
     */
    public int getHeaderHeight ( )
    {
        return _header.getHeight( );
    }

    @Override
    public void mouseDragged ( MouseEvent e )
    {

    }

    @Override
    public void mouseMoved ( MouseEvent e )
    {

    }
}
