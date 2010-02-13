package org.promasi.shell.model.actions;


import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.EventBinding;


/**
 * 
 * This interface is called when the core layer throws an event. Implementors
 * must follow the bean specification.
 * 
 * 
 * @see EventBinding
 * @author eddiefullmetal
 * 
 */
public interface IModelAction
{
    /**
     * Runs the action.
     */
    void runAction ( );

    /**
     * @return True if all the params of the action are ok. False otherwise.
     */
    boolean isValid ( );
}
