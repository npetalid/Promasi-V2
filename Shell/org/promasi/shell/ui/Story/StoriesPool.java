package org.promasi.shell.ui.Story;


import java.beans.IntrospectionException;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.promasi.core.SdModel;
import org.promasi.model.Accountant;
import org.promasi.model.Administrator;
import org.promasi.model.Boss;
import org.promasi.model.Company;
import org.promasi.model.Employee;
import org.promasi.model.EmployeeProperty;
import org.promasi.model.MarketPlace;
import org.promasi.model.Project;
import org.promasi.model.Task;
import org.promasi.shell.playmodes.singleplayerscoremode.DifficultyLevel;
import org.promasi.shell.playmodes.singleplayerscoremode.SinglePlayerScorePlayMode;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.EventBinding;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.ExternalEquationBinding;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.OutputVariableBinding;
import org.xml.sax.SAXException;


/**
 * 
 * Keeps all the available stories.
 * 
 * @see Story
 * @author eddiefullmetal
 * 
 */
public final class StoriesPool
{
    /**
     * The folder that contains all the stories.
     * 
     * @see Story
     */
    private static final String STORIES_FOLDER = SinglePlayerScorePlayMode.RELATIVE_DATA_DIRECTORY + File.separator + "Stories";

    /**
     * The name of the story info file.
     */
    private static final String INFO_NAME = "info.html";

    /**
     * The name of the story XML file.
     */
    private static final String STORY_NAME = "story.xml";

    /**
     * The name of the project XML file inside the prj file.
     */
    private static final String PROJECT_NAME = "project.xml";

    /**
     * The name of the project XML file inside the prj file.
     */
    private static final String CORE_MODEL_BINDINGS_NAME = "coreModelBindings.xml";

    /**
     * The name of the SD model XML file inside the prj file.
     */
    private static final String MODEL_NAME = "sdmodel.xml";

    /**
     * 
     * The name of the XML file that contains all the employees.
     * 
     * @see Employee
     */
    private static final String EMPLOYEES_NAME = "employees.xml";

    /**
     * All the defined stories.
     */
    private static final List<Story> ALL_STORIES = new Vector<Story>( );

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( StoriesPool.class );

    /**
     * Initializes the object.
     */
    private StoriesPool( )
    {

    }

    static
    {
        loadStories( );
    }

    /**
     * @return the {@link #_allStories}.
     */
    public static List<Story> getAllStories ( )
    {
        return ALL_STORIES;
    }

    /**
     * Loads all the stories.
     */
    private static void loadStories ( )
    {

        File storiesFolder = new File( STORIES_FOLDER );
        // Process all story folders.
        File[] storyDirectories = storiesFolder.listFiles( (FileFilter) DirectoryFileFilter.DIRECTORY );
        for ( File file : storyDirectories )
        {
            Story story = new Story( );
            story.setName( file.getName( ) );
            LOGGER.info( "Loading story " + story );
            // Load the info file.
            try
            {
                File employeesFile = file.listFiles( (FileFilter) new NameFileFilter( EMPLOYEES_NAME ) )[0];
                if ( !employeesFile.exists( ) )
                {
                    LOGGER.error( "Employees XML file does not exist" );
                    throw new FileNotFoundException( );
                }
                else
                {
                    // Configure the reader.
                    try
                    {
                        Digester digester = new Digester( );
                        digester.addObjectCreate( "ListOfEmployees", Vector.class );
                        digester.addObjectCreate( "ListOfEmployees/Employee", Employee.class );
                        digester.addBeanPropertySetter( "ListOfEmployees/Employee/name", "name" );
                        digester.addBeanPropertySetter( "ListOfEmployees/Employee/lastName", "lastName" );
                        digester.addBeanPropertySetter( "ListOfEmployees/Employee/salary", "salary" );
                        digester.addBeanPropertySetter( "ListOfEmployees/Employee/curriculumVitae", "curriculumVitae" );
                        digester.addObjectCreate( "ListOfEmployees/Employee/property", EmployeeProperty.class );
                        digester.addBeanPropertySetter( "ListOfEmployees/Employee/property", "value" );
                        digester.addSetProperties( "ListOfEmployees/Employee/property", "name", "name" );
                        digester.addSetNext( "ListOfEmployees/Employee/property", "addProperty", EmployeeProperty.class.getName( ) );
                        digester.addSetNext( "ListOfEmployees/Employee", "add", Employee.class.getName( ) );

                        List<Employee> employeesList=(List<Employee>) digester.parse( employeesFile );
                        Map<Integer, Employee> employees=new TreeMap<Integer,Employee>();
                        Integer i=0;
                        for(Employee employee : employeesList)
                        {
                        	employees.put(i, employee);
                        	i=i+1;
                        }
                        
                        story.setMarketPlace(new MarketPlace(employees));
                    }
                    catch ( IOException e )
                    {
                        LOGGER.error( "Could not read file.", e );
                    }
                    catch ( SAXException e )
                    {
                        LOGGER.error( "Error while parsing XML file", e );
                    }
                    catch ( Exception e )
                    {
                        LOGGER.error( "Unexpected exception", e );
                    }
                }
            }
            catch ( ArrayIndexOutOfBoundsException e )
            {
                LOGGER.warn( "Could not find " + INFO_NAME + " in " + file.getName( ), e );
            }
            catch ( FileNotFoundException e )

            {
                LOGGER.warn( "Could not find " + EMPLOYEES_NAME + " in " + file.getName( ), e );
            }
            
            try
            {
            	File storyXmlFile = file.listFiles( (FileFilter) new NameFileFilter( STORY_NAME ) )[0];
               	XMLConfiguration storyConfiguration = new XMLConfiguration( );
               	storyConfiguration.setDelimiterParsingDisabled(true);
               	storyConfiguration.load(storyXmlFile);

              	// Load the company
             	Company company = new Company( storyConfiguration.getString( "Company.Name" ) );
              	company.setStartTime( new LocalTime( storyConfiguration.getString( "Company.StartTime" ) ) );
             	company.setEndTime( new LocalTime( storyConfiguration.getString( "Company.EndTime" ) ) );
             	company.setDescription( storyConfiguration.getString( "Company.Description" ) );
            	story.setCompany( company );

               	// Load the persons
               	Boss boss = new Boss( );
            	boss.setFirstName( storyConfiguration.getString( "Persons.Boss.Name" ) );
              	boss.setLastName( storyConfiguration.getString( "Persons.Boss.LastName" ) );
              	boss.setWorkingCompany( company );
            	story.setBoss( boss );

             	// Load the Administrator
            	Administrator administrator = new Administrator( );
             	administrator.setFirstName( storyConfiguration.getString( "Persons.Administrator.Name" ) );
             	administrator.setLastName( storyConfiguration.getString( "Persons.Administrator.LastName" ) );
               	administrator.setWorkingCompany( company );
              	story.setAdministrator( administrator );

               	// Load the Accountant
              	Accountant accountant = new Accountant( );
             	accountant.setFirstName( storyConfiguration.getString( "Persons.Accountant.Name" ) );
             	accountant.setLastName( storyConfiguration.getString( "Persons.Accountant.LastName" ) );
            	accountant.setWorkingCompany( company );
              	story.setAccountant( accountant );
              	// -----------------------------------
              	
              	// Load game information
              	String infoString=new String(storyConfiguration.getString("StoryInfo"));
              	story.setInfoString(infoString);
              	
              	story.setDifficultyLevel( DifficultyLevel.valueOf( storyConfiguration.getString( "DifficultyLevel" ) ) );
              	story.setStartDate( new LocalDate( storyConfiguration.getString( "StartDate" ) ) );
             	String[] projects = storyConfiguration.getStringArray( "Projects.Project" );
            	for ( String projectFileName : projects )
              	{
                	File prjFile = file.listFiles( (FileFilter) new NameFileFilter( projectFileName + ".prj" ) )[0];
                	loadProjectFile( story, prjFile );
                }
                    
             	if ( story.isValid( ) )
              	{
                	ALL_STORIES.add( story );
              	}
             	else
             	{
                        LOGGER.warn( "Invalid story." );
             	}
           }
           catch ( Exception e )
           {
        	   LOGGER.warn( "Could not load " + file.getName( ), e );
           }
        }
    }

    /**
     * Loads the {@link Project} the {@link SdModel} from a .prj file and adds
     * them to the story.
     * 
     * @param story
     *            The {@link Story} to load the data to.
     * @param prjFile
     *            The {@link File} to load the {@link Project} from.
     * @throws ZipException
     * @throws IOException
     * @throws IntrospectionException
     * @throws SAXException
     */
    private static void loadProjectFile ( Story story, File prjFile ) throws ZipException, IOException, IntrospectionException, SAXException
    {
        LOGGER.info( "Loading project from file " + prjFile.getName( ) );

        ZipFile zipFile = new ZipFile( prjFile );
        // Get all needed files.
        ZipEntry projectXml = zipFile.getEntry( PROJECT_NAME );
        ZipEntry modelXml = zipFile.getEntry( MODEL_NAME );
        ZipEntry coreModelBindingsXml = zipFile.getEntry( CORE_MODEL_BINDINGS_NAME );
        // Validate files.
        if ( projectXml == null || modelXml == null || coreModelBindingsXml == null )
        {
            LOGGER.error( "No xml for project,model or Core-Model bindings found." );
            throw new FileNotFoundException( "No xml for project,model or Core-Model bindings found" );
        }

        // Parse project.
        // Configure the BeanReader.
        BeanReader projectReader = new BeanReader( );
        projectReader.registerBeanClass( Project.class );
        projectReader.addRule( "Project/relativeStartDate", new DateAddRule( story.getStartDate( ).toLocalDateTime(
                story.getCompany( ).getStartTimeAsLocalTime( ) ), true ) );
        projectReader.addRule( "Project/relativeEndDate", new DateAddRule(
                story.getStartDate( ).toLocalDateTime( story.getCompany( ).getEndTimeAsLocalTime( ) ), false ) );
        projectReader.registerBeanClass( "Project/tasks/Task", Task.class );
        projectReader.addSetNext( "Project/tasks/Task", "addTask", "org.promasi.model.Task" );
        Project project = (Project) projectReader.parse( zipFile.getInputStream( projectXml ) );
        story.addProject( project );

        // Parse model
        XMLDecoder decoder = new XMLDecoder( zipFile.getInputStream( modelXml ) );
        SdModel model = (SdModel) decoder.readObject( );
        story.addProjectModelRelation( project, model );

        // Parse the output variable bindings
        BeanReader outputVariableBindingsReader = new BeanReader( );
        outputVariableBindingsReader.registerBeanClass( "CoreModelBindings/OutputVariables", Vector.class );
        outputVariableBindingsReader.registerBeanClass( "CoreModelBindings/OutputVariables/OutputVariable", OutputVariableBinding.class );
        outputVariableBindingsReader.addSetNext( "CoreModelBindings/OutputVariables/OutputVariable", "add", OutputVariableBinding.class.getName( ) );
        List<OutputVariableBinding> variableBindings = (List<OutputVariableBinding>) outputVariableBindingsReader.parse( zipFile
                .getInputStream( coreModelBindingsXml ) );
        story.setOutputVariableBindings( project, variableBindings );

        // Parse the external equation bindings
        BeanReader externalEquationBindingsReader = new BeanReader( );
        externalEquationBindingsReader.registerBeanClass( "CoreModelBindings/ExternalEquations", Vector.class );
        externalEquationBindingsReader.registerBeanClass( "CoreModelBindings/ExternalEquations/ExternalEquation", ExternalEquationBinding.class );
        externalEquationBindingsReader.addSetNext( "CoreModelBindings/ExternalEquations/ExternalEquation", "add", ExternalEquationBinding.class
                .getName( ) );
        List<ExternalEquationBinding> externalBindings = (List<ExternalEquationBinding>) externalEquationBindingsReader.parse( zipFile
                .getInputStream( coreModelBindingsXml ) );
        story.setExternalEquationBindings( project, externalBindings );

        // Parse all the event bindings.
        BeanReader eventBindingsReader = new BeanReader( );
        eventBindingsReader.registerBeanClass( "CoreModelBindings/Events", Vector.class );
        eventBindingsReader.registerBeanClass( "CoreModelBindings/Events/Event", EventBinding.class );
        eventBindingsReader.addSetNext( "CoreModelBindings/Events/Event", "add", EventBinding.class.getName( ) );
        eventBindingsReader.addCallMethod( "CoreModelBindings/Events/Event/actionBinding/parameter", "addParameter", 2 );
        eventBindingsReader.addCallParam( "CoreModelBindings/Events/Event/actionBinding/parameter", 0, "name" );
        eventBindingsReader.addCallParam( "CoreModelBindings/Events/Event/actionBinding/parameter", 1 );

        List<EventBinding> eventBindings = (List<EventBinding>) eventBindingsReader.parse( zipFile.getInputStream( coreModelBindingsXml ) );
        story.setEventBindings( project, eventBindings );

    }

    /**
     * 
     * A {@link Rule} that takes the element body converts it to integer , adds
     * it to the {@link #_date} and assigns it to the project.
     * 
     * @author eddiefullmetal
     * 
     */
    private static final class DateAddRule
            extends Rule
    {

        /**
         * The starting date that the body will be added.
         */
        private LocalDateTime _date;

        /**
         * Flag indicating if the startDate should be set. If false the endDate
         * will be set.
         */
        private boolean _shouldSetStartDate;

        /**
         * Initializes the object.
         * 
         * @param startingDate
         *            The {@link #_date}.
         */
        public DateAddRule( LocalDateTime startingDate, boolean shouldSetStartDate )
        {
            _date = startingDate;
            _shouldSetStartDate = shouldSetStartDate;
        }

        @Override
        public void body ( String namespace, String name, String text )
                throws Exception
        {
            Object obj = getDigester( ).peek( );
            if ( obj instanceof Project )
            {
                Project project = (Project) obj;
                int daysToAdd = Integer.parseInt( text );
                DateTime dateTime = _date.plusDays( daysToAdd ).toDateTime( );
                if ( _shouldSetStartDate )
                {
                    project.setStartDate( dateTime );
                }
                else
                {
                    project.setEndDate( dateTime );
                }
            }
        }

    }
}
