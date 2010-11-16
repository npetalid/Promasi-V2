package org.promasi.ui.promasiui.promasidesktop;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.ui.utilities.ScreenUtils;

/**
 *
 * The {@link ILoginUi} for the {@link SinglePlayerScorePlayMode}.
 *
 * @author eddiefullmetal
 *
 */
public class LoginUi extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The name of the project manager.
     */
    private JTextField _nameField;

    /**
     * The last name of the project manager.
     */
    private JTextField _lastNameField;
    
    /**
     * 
     */
    private JPasswordField _passwordField;

    /**
     * The OK button.
     */
    private JButton _okButton;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( LoginUi.class );

    /**
     * Initializes the object.
     */
    public LoginUi()throws NullArgumentException,IllegalArgumentException
    {

    	
        setTitle( ResourceManager.getString( LoginUi.class, "title" ) );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( ScreenUtils.sizeForPercentage( 0.15d, 0.15d ) );
        ScreenUtils.centerInScreen( this );
        
        _nameField = new JTextField( );
        _lastNameField = new JTextField( );
        _okButton = new JButton( "Login" );
        
        setLayout( new MigLayout( new LC( ).fillX( ) ) );
        add( new JLabel( "Name" ), new CC( ) );
        add( _nameField, new CC( ).growX( ).wrap( ) );
        add( new JLabel( "Last Name" ), new CC( ) );
        add( _lastNameField, new CC( ).growX( ).wrap( ) );
        
        if(_passwordField!=null){
        	add( new JLabel("Password"), new CC() );
        	add( _passwordField, new CC().growX().wrap() );
        }
        
        add( _okButton, new CC( ).skip( 1 ).alignX( "right" ) );
        
        _okButton.addActionListener( new ActionListener( )
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                login( );
            }
        } );
    }

    
    private void login ( )
    {
        String name = _nameField.getText( );
        String lastName = _lastNameField.getText( );
        String password=null;
        if(_passwordField!=null){
        	password= new String( _passwordField.getPassword() );
        }
        

        if ( StringUtils.isBlank( name ) || StringUtils.isBlank( lastName ) )
        {
            JOptionPane.showMessageDialog( this, ResourceManager.getString( LoginUi.class, "invalidInput", "text" ),
                    ResourceManager.getString( LoginUi.class, "invalidInput", "title" ), JOptionPane.ERROR_MESSAGE );
        }
        else
        {
            LOGGER.info( "Logging for " + name + " " + lastName );
            // Create the company and assign it.
            setVisible( false );
            dispose( );
        }
    }

    public void showUi ( )
    {
        // Make sure that the setVisible is called inside the event queue.
        EventQueue.invokeLater( new Runnable( )
        {
            @Override
            public void run ( )
            {
                setVisible( true );
            }

        } );
    }

}
