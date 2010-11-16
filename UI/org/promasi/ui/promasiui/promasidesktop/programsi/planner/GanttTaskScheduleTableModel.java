package org.promasi.ui.promasiui.promasidesktop.programsi.planner;


import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * 
 * A {@link TableModel} that contains {@link GanttTaskSchedule}s.
 * 
 * @author eddiefullmetal
 * 
 */
public class GanttTaskScheduleTableModel
        implements TableModel
{

    /**
     * The headers of all columns.
     */
    private String[] _columnHeaders;

    /**
     * The data for this model.
     */
    private List<GanttTaskSchedule> _data;

    /**
     * All the {@link TableModelListener}s.
     */
    private List<TableModelListener> _listeners;

    /**
     * Flag that indicates if the model is read only.
     */
    private boolean _readOnly;

    /**
     * Initializes the object.
     * 
     * @param data
     *            The {@link #_data}.
     */
    public GanttTaskScheduleTableModel( List<GanttTaskSchedule> data )
    {
        _readOnly = false;
        _listeners = new Vector<TableModelListener>( );
        _columnHeaders = new String[] { ResourceManager.getString( GanttTaskScheduleTableModel.class, "nameHeader" ),
                ResourceManager.getString( GanttTaskScheduleTableModel.class, "workHeader" ) };
        _data = data;
    }

    @Override
    public void addTableModelListener ( TableModelListener l )
    {
        if ( !_listeners.contains( l ) )
        {
            _listeners.add( l );
        }
    }

    @Override
    public Class<?> getColumnClass ( int columnIndex )
    {
        if ( columnIndex == 1 )
        {
            return Integer.class;
        }
        else
        {
            return String.class;
        }
    }

    @Override
    public int getColumnCount ( )
    {
        return _columnHeaders.length;
    }

    @Override
    public String getColumnName ( int columnIndex )
    {
        return _columnHeaders[columnIndex];
    }

    @Override
    public int getRowCount ( )
    {
        return _data.size( );
    }

    /**
     * @param readOnly
     *            the {@link #_readOnly} to set.
     */
    public void setReadOnly ( boolean readOnly )
    {
        _readOnly = readOnly;
    }

    /**
     * @return the {@link #_readOnly}.
     */
    public boolean isReadOnly ( )
    {
        return _readOnly;
    }

    @Override
    public Object getValueAt ( int rowIndex, int columnIndex )
    {
        GanttTaskSchedule taskSchedule = _data.get( rowIndex );
        if ( columnIndex == 0 )
        {
            return taskSchedule.getName( );
        }
        else if ( columnIndex == 1 )
        {
            return taskSchedule.getWork( );
        }

        return StringUtils.EMPTY;
    }

    @Override
    public boolean isCellEditable ( int rowIndex, int columnIndex )
    {
        // only the work field is editable.
        if ( columnIndex == 1 && !_readOnly )
        {
            return true;
        }

        return false;
    }

    @Override
    public void removeTableModelListener ( TableModelListener l )
    {
        if ( _listeners.contains( l ) )
        {
            _listeners.remove( l );
        }
    }

    /**
     * @param rowIndex
     * @return The {@link GanttTaskSchedule} at the specified rowIndex.
     */
    public GanttTaskSchedule getTaskScheduleAt ( int rowIndex )
    {
        return _data.get( rowIndex );
    }

    @Override
    public void setValueAt ( Object value, int rowIndex, int columnIndex )
    {
        GanttTaskSchedule taskSchedule = _data.get( rowIndex );
        if ( columnIndex == 1 )
        {
            try
            {
                taskSchedule.setWork( (Integer) value );
            }
            catch ( IllegalArgumentException e )
            {
                JOptionPane.showMessageDialog( null, ResourceManager.getString( GanttTaskScheduleTableModel.class, "invalidWorkValue", "text" ),
                        ResourceManager.getString( GanttTaskScheduleTableModel.class, "invalidWorkValue", "title" ), JOptionPane.WARNING_MESSAGE );
            }
        }
        for ( TableModelListener listener : _listeners )
        {
            listener.tableChanged( new TableModelEvent( this, rowIndex ) );
        }
    }

}
