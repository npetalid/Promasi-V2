package org.promasi.ui.promasiui.promasidesktop.programs.planner;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import org.joda.time.LocalDate;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * 
 * The UI for a {@link GanttTaskSchedule}.
 * 
 * @author eddiefullmetal
 * 
 */
public class GanttTaskScheduleUi
{

    /**
     * The {@link GanttTaskSchedule} that this {@link GanttTaskScheduleUi}
     * represents.
     */
    private GanttTaskSchedule _taskSchedule;

    /**
     * The location of the object.
     */
    private Point _location;

    /**
     * The size of the object.
     */
    private Dimension _size;

    /**
     * Initializes the object.
     * 
     * @param taskSchedule
     *            The {@link #_taskSchedule}.
     */
    public GanttTaskScheduleUi( GanttTaskSchedule taskSchedule )
    {
        _taskSchedule = taskSchedule;
    }

    public void paint ( Graphics2D g2d )
    {
        g2d.setColor( ResourceManager.getColor( GanttTaskScheduleUi.class, "color" ) );
        g2d.fillRect( _location.x, _location.y, _size.width, _size.height );
        g2d.setColor( Color.BLACK );
        g2d.drawLine( getLocation( ).x, getLocation( ).y, getLocation( ).x + getSize( ).width, getLocation( ).y );
        g2d.drawLine( getLocation( ).x, getLocation( ).y + getSize( ).height, getLocation( ).x + getSize( ).width, getLocation( ).y
                + getSize( ).height );
        g2d.drawLine( getLocation( ).x, getLocation( ).y, getLocation( ).x, getLocation( ).y + getSize( ).height );
        g2d.drawLine( getLocation( ).x + getSize( ).width, getLocation( ).y, getLocation( ).x + getSize( ).width, getLocation( ).y
                + getSize( ).height );
    }

    /**
     * @return the {@link #_location}.
     */
    public Point getLocation ( )
    {
        return _location;
    }

    /**
     * @param location
     *            the {@link #_location} to set.
     */
    public void setLocation ( Point location )
    {
        _location = location;
    }

    /**
     * @return the {@link #_size}.
     */
    public Dimension getSize ( )
    {
        return _size;
    }

    /**
     * @param size
     *            the {@link #_size} to set.
     */
    public void setSize ( Dimension size )
    {
        _size = size;
    }

    /**
     * @return the {@link #_taskSchedule}.
     */
    public GanttTaskSchedule getTaskSchedule ( )
    {
        return _taskSchedule;
    }

    /**
     * @param taskSchedule
     *            the {@link #_taskSchedule} to set.
     */
    public void setTaskSchedule ( GanttTaskSchedule taskSchedule )
    {
        _taskSchedule = taskSchedule;
    }

    /**
     * @return The start date of the {@link GanttTaskSchedule}.
     */
    public LocalDate getStartDate ( )
    {
        return _taskSchedule.getStartDate( );
    }

    /**
     * @return The end date of the {@link GanttTaskSchedule}.
     */
    public LocalDate getEndDate ( )
    {
        return _taskSchedule.getEndDate( );
    }
}
