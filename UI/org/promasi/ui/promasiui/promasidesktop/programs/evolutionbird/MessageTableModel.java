/**
 *
 */
package org.promasi.ui.promasiui.promasidesktop.programs.evolutionbird;


import java.util.List;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.promasi.model.Message;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * @author eddiefullmetal
 * 
 */
public class MessageTableModel
        implements TableModel
{

    /**
     * The headers for all columns.
     */
    private String[] _columnHeaders;

    /**
     * All the {@link Message}s of this model.
     */
    private List<Message> _messages;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( MessageTableModel.class );

    /**
     * Initializes the object.
     */
    public MessageTableModel( List<Message> messages )
    {
        _columnHeaders = new String[] { ResourceManager.getString( MessageTableModel.class, "title" ),
                ResourceManager.getString( MessageTableModel.class, "sender" ), ResourceManager.getString( MessageTableModel.class, "dateSend" ) };
        if ( messages != null )
        {
            _messages = messages;
        }
        else
        {
            LOGGER.warn( "Empty messages list passed" );
            _messages = new Vector<Message>( );
        }
    }

    @Override
    public void addTableModelListener ( TableModelListener l )
    {
        // Disabled.
    }

    @Override
    public Class<?> getColumnClass ( int columnIndex )
    {
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
        return _messages.size( );
    }

    @Override
    public Object getValueAt ( int rowIndex, int columnIndex )
    {
        Message message = _messages.get( rowIndex );
        if ( columnIndex == 0 )
        {
            return message.getTitle( );
        }
        else if ( columnIndex == 1 )
        {
            return message.getSender( ).toString( );
        }
        else if ( columnIndex == 2 )
        {
            return ResourceManager.formatDateAndTime( message.getDateSent( ) );
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
        // Disabled
    }

    @Override
    public void setValueAt ( Object value, int rowIndex, int columnIndex )
    {
        // Disabled
    }

    /**
     * @param row
     *            The row number.
     * @return The {@link Message} corresponding to the row.
     */
    public Message getMessage ( int row )
    {
        if ( row >= 0 )
        {
            return _messages.get( row );
        }
        else
        {
            return null;
        }
    }

    /**
     * @return the {@link #_messages}.
     */
    public List<Message> getMessages ( )
    {
        return _messages;
    }

}
