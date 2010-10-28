package org.promasi.ui.promasiui.multiplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.CreateNewGameRequest;
import org.promasi.utilities.ui.ScreenUtils;

public class CreateGameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String _storyId;
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private JTextField _gameIdField;
	
	/**
	 * 
	 */
	private JButton _createGameButton;
	
	/**
	 * 
	 */
	private JLabel _label=new JLabel("Game name");
	
	/**
	 * 
	 * @param client
	 * @param storyId
	 * @throws NullArgumentException
	 */
	public CreateGameFrame(ProMaSiClient client,String storyId)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		if(storyId==null)
		{
			throw new NullArgumentException("Wrong argument storyId");
		}
		
		_gameIdField=new JTextField();
		_createGameButton=new JButton("Create");
		
		setTitle("Create Game");
		setLayout( new MigLayout( new LC( ).fillX( ) ) );
		add( _label, new CC( ).growX( ).wrap( ) );
		add( _gameIdField, new CC( ).growX( ).wrap( ) );
		add( _createGameButton, new CC( ).growX( ).wrap( ) );
		
		setSize( 300, 200 );
		_createGameButton.addActionListener(new ActionListener( )
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
            	createGame();
            }
        } );
        
		ScreenUtils.centerInScreen( this );
		_client=client;
		_storyId=new String(storyId);
	}
	
	/**
	 * 
	 */
	private void createGame()
	{
		if(_gameIdField.getText().isEmpty())
		{
			
		}
		else
		{
			CreateNewGameRequest request=new CreateNewGameRequest(_storyId,_gameIdField.getText());
			_client.sendMessage(request.toProtocolString());
			setVisible(false);
		}
	}
}
