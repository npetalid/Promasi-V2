package org.promasi.ui.promasiui.multiplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.StartGameRequest;
import org.promasi.utilities.ui.ScreenUtils;

public class WaitingForPlayersFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private JButton _startButton;
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	public WaitingForPlayersFrame(ProMaSiClient client)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		final double sizePercentage = 0.4d;
        setSize( ScreenUtils.sizeForPercentage( sizePercentage, sizePercentage ) );
        ScreenUtils.centerInScreen( this );
		setTitle("Waiting for other players");
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        _startButton=new JButton("Start");
        
        setLayout( new MigLayout( new LC( ).fillX( ) ) );
        add( _startButton, new CC( ).skip( 1 ).alignX( "right" ) );
        
        _startButton.addActionListener( new ActionListener( )
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
               _client.sendMessage(new StartGameRequest().toProtocolString());
               setVisible(false);
            }
        } );
        
        _client=client;
	}
	
}