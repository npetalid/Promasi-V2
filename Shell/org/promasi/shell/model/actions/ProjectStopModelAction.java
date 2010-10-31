package org.promasi.shell.model.actions;


import java.util.Set;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.promasi.model.ITimerTask;
import org.promasi.model.Timer;
import org.promasi.shell.IPlayModeListener;
import org.promasi.shell.Shell;


/**
 *
 * An {@link IModelAction} that immediately stops the current project. Basically
 * runs the {@link ProjectEndTimerTask} from the {@link Timer} and removes it.
 * This will make the {@link Shell} to fire the
 * {@link IPlayModeListener#projectFinished(org.promasi.model.Project)}
 *
 * @author eddiefullmetal
 *
 */
public class ProjectStopModelAction
        implements IModelAction
{

	private Shell _shell;
    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( ProjectStopModelAction.class );

    public ProjectStopModelAction(Shell shell)throws NullArgumentException
    {
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
    	_shell=shell;
    }

    @Override
    public void runAction ( )
    {
        LOGGER.info( "Firing ProjectStopAction" );
        // Get the ProjectEndTimerTask from the clock
       /* Set<ITimerTask> timerTasks = Timer.getInstance( ).getScheduledTasks( );
        for ( ITimerTask timerTask : timerTasks )
        {
            if ( timerTask instanceof Shell.ProjectEndTimerTask )
            {
                // Check if the project matches the current project. Currently
                // it will.
                Shell.ProjectEndTimerTask endTimerTask = (ProjectEndTimerTask) timerTask;
                if ( endTimerTask.getProject( ).equals( _shell.getCurrentProject( ) ) )
                {
                    endTimerTask.runTimerTask( );
                    Timer.getInstance( ).unScheduleTask( endTimerTask );
                }
            }
        }*/
    }

    @Override
    public boolean isValid ( )
    {
        return true;
    }

}
