package org.promasi.shell.playmodes.singleplayerscoremode.corebindings;


import org.promasi.core.ISdObject;
import org.promasi.shell.model.actions.IModelAction;


/**
 * 
 * Defines which {@link IModelAction} will be called when a specific core event
 * is fired.
 * 
 * @author eddiefullmetal
 * 
 */
public class EventBinding
{

    /**
     * The key of the {@link ISdObject} that will throw the event.
     */
    private String _sdObjectKey;

    /**
     * The name of the event.
     */
    private String _eventName;

    /**
     * The {@link ActionBinding}.
     */
    private ActionBinding _actionBinding;

    /**
     * Initializes the object.
     * 
     */
    public EventBinding( )
    {

    }

    /**
     * @return the {@link #_sdObjectKey}.
     */
    public String getSdObjectKey ( )
    {
        return _sdObjectKey;
    }

    /**
     * @param sdObjectKey
     *            the {@link #_sdObjectKey} to set.
     */
    public void setSdObjectKey ( String sdObjectKey )
    {
        _sdObjectKey = sdObjectKey;
    }

    /**
     * @return the {@link #_eventName}.
     */
    public String getEventName ( )
    {
        return _eventName;
    }

    /**
     * @param eventName
     *            the {@link #_eventName} to set.
     */
    public void setEventName ( String eventName )
    {
        _eventName = eventName;
    }

    /**
     * @return the {@link #_actionBinding}.
     */
    public ActionBinding getActionBinding ( )
    {
        return _actionBinding;
    }

    /**
     * @param actionBinding
     *            the {@link #_actionBinding} to set.
     */
    public void setActionBinding ( ActionBinding actionBinding )
    {
        _actionBinding = actionBinding;
    }
}
