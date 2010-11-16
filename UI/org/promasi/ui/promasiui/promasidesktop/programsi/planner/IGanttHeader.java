package org.promasi.ui.promasiui.promasidesktop.programsi.planner;


import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.joda.time.LocalDate;


/**
 * 
 * Interface for the header of the {@link GanttEditor}.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IGanttHeader
{

    /**
     * 
     * Draws the header.
     * 
     * @param g2d
     */
    void paint ( Graphics2D g2d );

    /**
     * @return The width of each column.
     */
    int getColumnWidth ( );

    /**
     * @return The height of the header.
     */
    int getHeight ( );

    /**
     * @return The total width of the header.
     */
    int getWidth ( );

    /**
     * Sets the start date.
     * 
     * @param startDate
     */
    void setStartDate ( LocalDate startDate );

    /**
     * Sets the end date.
     * 
     * @param endDate
     */
    void setEndDate ( LocalDate endDate );

    /**
     * @return The total columns of the header.
     */
    int getTotalColumns ( );

    /**
     * @param font
     *            The font of the header.
     */
    void setFont ( Font font );

    /**
     * @param level
     *            The zoom level of the header.
     */
    void setZoomLevel ( int level );

    /**
     * @return The minimum value for the zoom level.
     */
    int getMinZoomLevel ( );

    /**
     * @return The maximum value for the zoom level.
     */
    int getMaxZoomLevel ( );

    /**
     * @return The normal zoom level.
     */
    int getNormalZoomLevel ( );

    /**
     * @return The current zoom level.
     */
    int getCurrentZoomLevel ( );

    /**
     * Calculates the bounds of an object depending on their start\end date.
     * 
     * @param startDate
     * @param endDate
     * @return A {@link Rectangle} representing the bounds.
     */
    Rectangle calculateBounds ( LocalDate startDate, LocalDate endDate );
}
