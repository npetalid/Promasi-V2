package org.promasi.ui.promasiui.promasidesktop.programs.planner;


import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.promasi.model.EmployeeTeamData;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * @author eddiefullmetal
 * 
 */
public class EmployeeTeamDataTableModel
        implements TableModel
{

    /**
     * The headers of all columns.
     */
    private String[] _columnHeaders;

    /**
     * The data for this model.
     */
    private List<EmployeeTeamData> _data;

    /**
     * Initializes the object.
     * 
     * @param data
     *            The {@link #_data}.
     */
    public EmployeeTeamDataTableModel( List<EmployeeTeamData> data )
    {
        _columnHeaders = new String[] { ResourceManager.getString( EmployeeTeamDataTableModel.class, "nameHeader" ),
                ResourceManager.getString( EmployeeTeamDataTableModel.class, "salaryHeader" ),
                ResourceManager.getString( EmployeeTeamDataTableModel.class, "workHeader" ) };
        _data = data;
    }

    @Override
    public void addTableModelListener ( TableModelListener l )
    {
    }

    @Override
    public Class<?> getColumnClass ( int columnIndex )
    {
        if ( columnIndex == 0 )
        {
            return String.class;
        }
        else if ( columnIndex == 1 )
        {
            return Double.class;
        }
        else if ( columnIndex == 2 )
        {
            return Integer.class;
        }
        return String.class;
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

    @Override
    public Object getValueAt ( int rowIndex, int columnIndex )
    {
        EmployeeTeamData teamData = _data.get( rowIndex );
        if ( columnIndex == 0 )
        {
            return teamData.getEmployee( ).getLastName( ) + " - " + teamData.getEmployee( ).getFirstName( );
        }
        else if ( columnIndex == 1 )
        {
            return teamData.getEmployee( ).getSalary( );
        }
        else if ( columnIndex == 2 )
        {
            return teamData.getHoursPerDay( );
        }

        return StringUtils.EMPTY;
    }

    @Override
    public boolean isCellEditable ( int rowIndex, int columnIndex )
    {
        return false;
    }

    @Override
    public void removeTableModelListener ( TableModelListener l )
    {
    }

    /**
     * @param rowIndex
     * @return The {@link EmployeeTeamData} at the specified rowIndex.
     */
    public EmployeeTeamData getTaskEmployeeTeamDataAt ( int rowIndex )
    {
        return _data.get( rowIndex );
    }

    @Override
    public void setValueAt ( Object value, int rowIndex, int columnIndex )
    {
        // Cannot edit this model.
    }

}
