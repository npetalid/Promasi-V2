package org.promasi.ui.promasiui.promasidesktop.programs.planner;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.joda.time.LocalDate;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * 
 * Default gantt header. Displays Days.
 * 
 * @author eddiefullmetal
 * 
 */
public class DefaultGanttHeader
        implements IGanttHeader
{

    /**
     * The date that the gantt will start.
     */
    private LocalDate _startDate;

    /**
     * The date that the gantt will end.
     */
    private LocalDate _endDate;

    /**
     * The font of the header.
     */
    private Font _font;

    /**
     * The Zoom level of the header.
     */
    private int _zoomLevel;

    /**
     * The Color of the header.
     */
    private Color _headerColor;

    /**
     * The height of the header.
     */
    private static final int HEIGHT = 20;

    /**
     * The minimum zoom level display the date in format dd/MM/yyyy(described in
     * resources).
     */
    private static final int ZOOM_LEVEL_DETAILED_DATE = 0;

    /**
     * The max zoom level displays the weeks.
     */
    private static final int ZOOM_LEVEL_WEEK = 2;

    /**
     * The normal zoom level displays the day only.
     */
    private static final int ZOOM_LEVEL_DAY_ONLY = 1;

    /**
     * Initializes the object.
     */
    public DefaultGanttHeader( )
    {
        _zoomLevel = ZOOM_LEVEL_WEEK;
        _headerColor = Color.decode( "#e4e3e2" );
    }

    @Override
    public void paint ( Graphics2D g2d )
    {
        if ( _font != null )
        {
            g2d.setFont( _font );
        }
        if ( _zoomLevel == ZOOM_LEVEL_DAY_ONLY )
        {
            drawNormalZoom( g2d );
        }
        else if ( _zoomLevel == ZOOM_LEVEL_DETAILED_DATE )
        {
            drawMinimumZoomLevel( g2d );
        }
        else if ( _zoomLevel == ZOOM_LEVEL_WEEK )
        {
            drawMaximumZoomLevel( g2d );
        }
    }

    /**
     * Draws the normal zoom level.
     */
    protected void drawNormalZoom ( Graphics2D g2d )
    {
        int currentWidth = getWidth( );
        g2d.drawLine( 0, getHeight( ), currentWidth, getHeight( ) );
        g2d.drawLine( currentWidth, 0, currentWidth, getHeight( ) );
        int totalDays = getTotalColumns( );
        // Paint header rectangles.
        for ( int i = 0; i < totalDays; i++ )
        {
            g2d.setColor( _headerColor );
            int lineX = i * getColumnWidth( );
            g2d.fillRect( lineX, 0, getColumnWidth( ), HEIGHT );
            g2d.setColor( Color.BLACK );
            g2d.drawLine( lineX, 0, lineX, HEIGHT );
            String dayString = String.valueOf( _startDate.plusDays( i ).getDayOfMonth( ) );
            FontMetrics fontMetrics = g2d.getFontMetrics( );
            int stringWidth = fontMetrics.stringWidth( dayString );
            g2d.drawString( dayString, lineX + ( ( getColumnWidth( ) - stringWidth ) / 2 ), ( HEIGHT + fontMetrics.getHeight( ) ) / 2 );
        }
    }

    /**
     * Draws the minimum zoom level.
     */
    protected void drawMinimumZoomLevel ( Graphics2D g2d )
    {
        int currentWidth = getWidth( );
        g2d.drawLine( 0, getHeight( ), currentWidth, getHeight( ) );
        g2d.drawLine( currentWidth, 0, currentWidth, getHeight( ) );
        int totalDays = getTotalColumns( );
        // Paint header rectangles.
        for ( int i = 0; i < totalDays; i++ )
        {
            g2d.setColor( _headerColor );
            int lineX = i * getColumnWidth( );
            g2d.fillRect( lineX, 0, getColumnWidth( ), HEIGHT );
            g2d.setColor( Color.BLACK );
            g2d.drawLine( lineX, 0, lineX, HEIGHT );
            String dayString = String.valueOf( ResourceManager.formatDateOnly( _startDate.plusDays( i ) ) );
            FontMetrics fontMetrics = g2d.getFontMetrics( );
            int stringWidth = fontMetrics.stringWidth( dayString );
            g2d.drawString( dayString, lineX + ( ( getColumnWidth( ) - stringWidth ) / 2 ), ( HEIGHT + fontMetrics.getHeight( ) ) / 2 );
        }
    }

    /**
     * Draws the maximum zoom level.
     */
    protected void drawMaximumZoomLevel ( Graphics2D g2d )
    {
        int currentWidth = getWidth( );
        g2d.drawLine( 0, getHeight( ), currentWidth, getHeight( ) );
        g2d.drawLine( currentWidth, 0, currentWidth, getHeight( ) );
        int totalWeeks = getTotalColumns( );
        // Paint header rectangles.
        for ( int i = 0; i < totalWeeks + 1; i++ )
        {
            g2d.setColor( _headerColor );
            int lineX = i * getColumnWidth( );
            g2d.fillRect( lineX, 0, getColumnWidth( ), HEIGHT );
            g2d.setColor( Color.BLACK );
            g2d.drawLine( lineX, 0, lineX, HEIGHT );
            LocalDate currentDate = _startDate.plusWeeks( i );
            String dayString = String.valueOf( "Week " + currentDate.getWeekOfWeekyear( ) + ", " + currentDate.getYear( ) );
            FontMetrics fontMetrics = g2d.getFontMetrics( );
            int stringWidth = fontMetrics.stringWidth( dayString );
            g2d.drawString( dayString, lineX + ( ( getColumnWidth( ) - stringWidth ) / 2 ), ( HEIGHT + fontMetrics.getHeight( ) ) / 2 );
        }
    }

    @Override
    public Rectangle calculateBounds ( LocalDate startDate, LocalDate endDate )
    {
        int totalDays = ( endDate.getDayOfYear( ) - startDate.getDayOfYear( ) ) + 1;
        int daysBefore = ( startDate.getDayOfYear( ) - _startDate.getDayOfYear( ) );
        if ( _zoomLevel == ZOOM_LEVEL_DAY_ONLY || _zoomLevel == ZOOM_LEVEL_DETAILED_DATE )
        {
            return new Rectangle( daysBefore * getColumnWidth( ), 0, totalDays * getColumnWidth( ), 0 );
        }
        else
        {
            int columnWidth = getColumnWidth( ) / 7;
            return new Rectangle( daysBefore * columnWidth, 0, totalDays * columnWidth, 0 );
        }
    }

    @Override
    public int getWidth ( )
    {
        return getColumnWidth( ) * getTotalColumns( );
    }

    /**
     * @return The total days.
     */
    @Override
    public int getTotalColumns ( )
    {
        int totalDays = ( _endDate.getDayOfYear( ) - _startDate.getDayOfYear( ) ) + 1;
        if ( _zoomLevel != ZOOM_LEVEL_WEEK )
        {
            return totalDays;
        }
        else
        {
            return totalDays / 7;
        }
    }

    @Override
    public int getColumnWidth ( )
    {
        if ( _zoomLevel == ZOOM_LEVEL_DAY_ONLY )
        {
            return 30;
        }
        else if ( _zoomLevel == ZOOM_LEVEL_DETAILED_DATE )
        {
            return 80;
        }
        else if ( _zoomLevel == ZOOM_LEVEL_WEEK )
        {
            return 120;
        }
        return 0;
    }

    @Override
    public int getHeight ( )
    {
        return HEIGHT;
    }

    @Override
    public void setStartDate ( LocalDate startDate )
    {
        _startDate = startDate;
    }

    @Override
    public void setEndDate ( LocalDate endDate )
    {
        _endDate = endDate;
    }

    @Override
    public void setFont ( Font font )
    {
        _font = font;
    }

    @Override
    public void setZoomLevel ( int level )
    {
        _zoomLevel = level;
    }

    @Override
    public int getMaxZoomLevel ( )
    {
        return ZOOM_LEVEL_WEEK;
    }

    @Override
    public int getMinZoomLevel ( )
    {
        return ZOOM_LEVEL_DETAILED_DATE;
    }

    @Override
    public int getNormalZoomLevel ( )
    {
        return ZOOM_LEVEL_DAY_ONLY;
    }

    @Override
    public int getCurrentZoomLevel ( )
    {
        return _zoomLevel;
    }
}
