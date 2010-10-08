package org.promasi.ui.promasiui.multiplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.layout.CC;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.StartGameRequest;
import org.promasi.shell.ui.playmode.Story;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.ui.promasiui.promasidesktop.story.StorySelectorFrame;

public class MakeGameForm extends JFrame {

	/**
	 * 
	 */
	ProMaSiClient _client;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private JButton _createGameButton;
	
    /**
     * A text area that displays the description of the selected {@link Story}.
     */
    private JEditorPane _descriptionText;
	
    /**
     * A label that displays the name of the selected {@link Story}.
     */
    private JLabel _playModeNameLabel;
    
	/**
	 * 
	 * @param client
	 * @throws NullArgumentException
	 */
	public MakeGameForm(ProMaSiClient client)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		_createGameButton=new JButton( "StartGame" );
		_createGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_client.sendMessage(new StartGameRequest().toProtocolString());
			}
		});
		
		add( _createGameButton, new CC( ).skip( 1 ).alignX( "right" ) );
		
        setTitle( "Make Game" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		_client=client;
	}
}
