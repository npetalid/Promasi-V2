/**
 * 
 */
package org.promasi.ui.menu;

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
import org.promasi.ui.utilities.ScreenUtils;

/**
 * @author m1cRo
 *
 */
public class GameMasterFrame extends JFrame {

	/**
	 * 
	 */
	ProMaSiClient _client;
	
    /**
     * The OK button.
     */
    private JButton _startButton;
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param client
	 * @throws NullArgumentException
	 */
	public GameMasterFrame(ProMaSiClient client)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		setLayout( new MigLayout( new LC( ).fillX( ) ) );
		_startButton=new JButton( "StartGame" );
		add( _startButton, new CC( ).skip( 1 ).alignX( "right" ) );
		_startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_client.sendMessage(new StartGameRequest().toProtocolString());
			}
		});
		
        setSize( ScreenUtils.sizeForPercentage( 0.100d, 0.100d ) );
        ScreenUtils.centerInScreen( this );
		
		_client=client;
	}
}
