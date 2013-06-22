package org.promasi.game.singleplayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.promasi.game.GameException;
import org.promasi.game.GameModel;
import org.promasi.game.IGameFactory;
import org.promasi.game.company.Company;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.MarketPlaceModel;
import org.promasi.game.model.generated.ProjectModel;
import org.promasi.game.project.Project;
import org.promasi.utilities.file.RootDirectory;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class SinglePlayerGameFolder
{
	/**
	 * Logger
	 */
	private static final ILogger CONST_LOGGER = LoggerFactory.getInstance(SinglePlayerGameFolder.class);
	
	/**
	 * 
	 */
	public static final String CONST_SINGLEPLAYER_GAMES_FOLDER="SinglePlayer";
	
	/**
	 * 
	 */
	public static final String CONST_PROJECTS_DIR_NAME="Projects";
	
	/**
	 * 
	 */
	public static final String CONST_COMPANY_FILE_NAME="Company";
	
	/**
	 * 
	 */
	public static final String CONST_MARKETPLACE_FILE_NAME="MarketPlace";
	
	/**
	 * 
	 */
	public static final String CONST_GAME_DESCRIPTION_FILE_NAME="GameInfo";
	
	/**
	 * 
	 */
	private GameModel _game;
	
	private IGameFactory _factory;
	
	/**
	 * 
	 * @param gameFolderPath
	 * @param playerName
	 * @throws GameException
	 */
	public SinglePlayerGameFolder(IGameFactory factory, final String gameFolderPath, String playerName)throws GameException{
		CONST_LOGGER.info("Start reading game from : " + gameFolderPath);
		if(gameFolderPath==null){
			throw new GameException("Wrong argument gameFolderPath==null");
		}
		
		if( factory == null ){
			throw new GameException("Wrong argument factory==null");
		}
		
		_factory = factory;
		String separator;
		try {
			separator = RootDirectory.getInstance().getSeparator();
		} catch (IOException e) {
			throw new GameException(e.toString());
		}
		
		File gameFile=new File(gameFolderPath);
		if(!gameFile.isDirectory()){
			throw new IllegalArgumentException("Wrong argument gameFolder is not an Directory");
		}
		
		String tokens[]=gameFolderPath.split(Pattern.quote(separator));
		if(tokens.length==0){
			throw new IllegalArgumentException("Wrong gameFolderPath");
		}
		
		String gameName=tokens[tokens.length-1];
		
		Company company=null;
		MarketPlace marketPlace=null;
		Queue<Project> projects=null;
		String gameInfo=null;
		
		String projectFiles[]= gameFile.list();
		
		try{
			for(int i=0;i<projectFiles.length;i++){
				if( projectFiles[i].toLowerCase().equals(CONST_COMPANY_FILE_NAME.toLowerCase()) ){
					company=makeCompany(gameFolderPath+separator+projectFiles[i]);
				}else if( projectFiles[i].toLowerCase().equals(CONST_MARKETPLACE_FILE_NAME.toLowerCase()) ){
					marketPlace=makeMarketPlace(gameFolderPath+separator+projectFiles[i]);
				}else if( projectFiles[i].toLowerCase().equals(CONST_GAME_DESCRIPTION_FILE_NAME.toLowerCase()) ){
					gameInfo=makeGameDescription(gameFolderPath+separator+projectFiles[i]);
				}else if( projectFiles[i].toLowerCase().equals(CONST_PROJECTS_DIR_NAME.toLowerCase()) ){
					projects=makeProjects(gameFolderPath+separator+projectFiles[i]);
				}
			}
		}catch(JAXBException e){
			throw new GameException("Wrong gameFolderPath");
		}catch(IllegalArgumentException e){
			throw new GameException("Wrong gameFolderPath");
		}catch(FileNotFoundException e){
			throw new GameException("Wrong gameFolderPath");
		} catch (IOException e) {
			throw new GameException("Wrong gameFolderPath");
		}

		
		if(company==null){
			CONST_LOGGER.warn("unable to read company");
			throw new GameException("Wrong gameFolderPath");
		}
		
		if(marketPlace==null){
			CONST_LOGGER.warn("unable to read marketplace");
			throw new GameException("Wrong gameFolderPath");
		}
		
		if(projects==null || projects.size()==0){
			CONST_LOGGER.warn("unable to read projects");
			throw new GameException("Wrong gameFolderPath");
		}
		
		if(gameInfo==null){
			CONST_LOGGER.warn("unable to read gameinfo");
			throw new GameException("Wrong gameFolderPath");
		}
		
		try {
			_game=new GameModel(gameName, gameInfo, marketPlace, company, projects);
		} catch (GameException e) {
			CONST_LOGGER.warn("unable to make game");
			throw new GameException("Wrong gameFolderPath");
		}
		
		company.setOwner(playerName);
	}

	/**
	 * 
	 * @return
	 */
	public GameModel readGame(){
		return _game;
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException 
	 * @throws SerializationException 
	 * @throws JAXBException 
	 */
	private Company makeCompany(final String filePath) throws FileNotFoundException, JAXBException{
		File companyFile=new File(filePath);
		FileInputStream fileInputStream=new FileInputStream(companyFile);
		
		JAXBContext jc = JAXBContext.newInstance( CompanyModel.class );
	    Unmarshaller unmarshaller = jc.createUnmarshaller();
	    Object object = unmarshaller.unmarshal( fileInputStream );

		if(object instanceof CompanyModel){
			CompanyModel sCompany=(CompanyModel)object;
			Company company=_factory.createCompany(sCompany);
			return company;
		}
		
		throw new FileNotFoundException("Company file not found");
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException 
	 * @throws SerializationException 
	 * @throws JAXBException 
	 */
	private MarketPlace makeMarketPlace(final String filePath) throws FileNotFoundException, JAXBException{
		File marketPlaceFile=new File(filePath);
		FileInputStream fileInputStream=new FileInputStream(marketPlaceFile);

		JAXBContext jc = JAXBContext.newInstance( MarketPlaceModel.class );
	    Unmarshaller unmarshaller = jc.createUnmarshaller();
	    Object object = unmarshaller.unmarshal( fileInputStream );
	    
		if(object instanceof MarketPlaceModel){
			MarketPlaceModel sMarketPlace=(MarketPlaceModel)object;
			MarketPlace marketPlace=_factory.createMarketPlace( sMarketPlace );
			return marketPlace;
		}
		
		throw new FileNotFoundException("Company file not found");
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	private String makeGameDescription(final String filePath) throws IOException{
		File file=new File(filePath);
		if(!file.exists() || file.isDirectory()){
			throw new FileNotFoundException("File not found");
		}
		
		String gameInfo="";
		FileReader fReader=new FileReader(file);
		BufferedReader bufferedReader=new BufferedReader(fReader);
		String line=null;

	    do{
	    	line=bufferedReader.readLine();
	    	if(line!=null){
	    		gameInfo=gameInfo+line;
	    	}
	    }while(line!=null);
	 
	    bufferedReader.close();
		return gameInfo;
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	private Queue<Project> makeProjects(final String filePath) throws IOException{
		Queue<Project> projects=new LinkedList<Project>();
		
		File file=new File(filePath);
		if(!file.isDirectory()){
			throw new FileNotFoundException("File not found");
		}
		
		String files[]=file.list();
		if(files.length==0){
			throw new FileNotFoundException("File not found");
		}
		
		for(int i=0;i<files.length;i++){
			try{
				File projectFile=new File(filePath+RootDirectory.getInstance().getSeparator()+files[i]);
				FileInputStream fileInputStream=new FileInputStream(projectFile);
				JAXBContext jc = JAXBContext.newInstance( ProjectModel.class );
			    Unmarshaller unmarshaller = jc.createUnmarshaller();
			    Object object = unmarshaller.unmarshal( fileInputStream );
				if(object instanceof ProjectModel){
					ProjectModel sProject=(ProjectModel)object;
					Project project;
					project = _factory.createProject(sProject);
					projects.add(project);
				}
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch( Exception e ){
				e.printStackTrace();
			}
		}
		
		return projects;
	}
}
