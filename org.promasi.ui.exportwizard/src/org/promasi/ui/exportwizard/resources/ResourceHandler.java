package org.promasi.ui.exportwizard.resources;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.digester3.Digester;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * 
 * @author antoxron
 *
 */
public class ResourceHandler {
	
	private static ResourceHandler _instance = null;
	
	public static ResourceHandler getInstance() {
		
		if (_instance == null) {
			_instance = new ResourceHandler();
		}
		return _instance;
	}
	
	public static final String COMPANIES = "files/companies.xml";
	public static final String EMPLOYEES = "files/employees.xml";
	public static final String GAME_STORIES = "files/gamestories.xml";

	
	public GameStories getGameStories() {
		
		GameStories results = null;
		
		Bundle bundle = Platform.getBundle("org.promasi.ui.exportwizard");
        URL fileURL = bundle.getEntry(GAME_STORIES);
        File file = null;
        
        
        try {
            file = new File(FileLocator.resolve(fileURL).toURI());
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
	         Digester digester = new Digester();
	       
	         
	         digester.setValidating( false );

	         digester.addObjectCreate( "gameStories", GameStories.class );

	         digester.addObjectCreate( "gameStories/gameStory", GameStory.class );
	         digester.addBeanPropertySetter( "gameStories/gameStory/description", "description" );
	        
	         digester.addSetNext( "gameStories/gameStory", "addGameStory" );

	         if (file != null) {
	        	 results = (GameStories)digester.parse( file );
	         }
	        
	      } 
		  catch( Exception exc ) {
	         exc.printStackTrace();
	      }
		
		return results;
		
	}
	
	
	
	
	public Employees getEmployees() {
		
		Employees results = null;
		
		Bundle bundle = Platform.getBundle("org.promasi.ui.exportwizard");
        URL fileURL = bundle.getEntry(EMPLOYEES);
        File file = null;
        
        
        try {
            file = new File(FileLocator.resolve(fileURL).toURI());
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
	         Digester digester = new Digester();
	       
	         
	         digester.setValidating( false );

	         digester.addObjectCreate( "employees", Employees.class );

	         digester.addObjectCreate( "employees/employee", EmployeeModel.class );
	         digester.addBeanPropertySetter( "employees/employee/id", "id" );
	         digester.addBeanPropertySetter( "employees/employee/name", "name" );
	         
	         
	         digester.addBeanPropertySetter( "employees/employee/lastName", "lastName" );
	         digester.addBeanPropertySetter( "employees/employee/salary", "salary" );
	         digester.addBeanPropertySetter( "employees/employee/developerSkill", "developerSkill" );
	         digester.addBeanPropertySetter( "employees/employee/testerSkill", "testerSkill" );
	         digester.addBeanPropertySetter( "employees/employee/teamPlayerSkill", "teamPlayerSkill" );
	         
	         digester.addBeanPropertySetter( "employees/employee/systemKnowledgeSkill", "systemKnowledgeSkill" );
	         digester.addBeanPropertySetter( "employees/employee/designerSkill", "designerSkill" );
	         digester.addBeanPropertySetter( "employees/employee/curriculumVitae", "curriculumVitae" );
	         digester.addBeanPropertySetter( "employees/employee/htmlCurriculumVitae", "htmlCurriculumVitae" );
	         digester.addBeanPropertySetter( "employees/employee/imagePath", "imagePath" );

	         
	         
	         digester.addSetNext( "employees/employee", "addEmployee" );

	         if (file != null) {
	        	 results = (Employees)digester.parse( file );
	         }
	        
	      } 
		  catch( Exception exc ) {
	         exc.printStackTrace();
	      }
		
		
		return results;
		
	}
	
	
	
	
	
	
	
	
	public  Companies getCompanies() {
		
		Companies results = null;
		Bundle bundle = Platform.getBundle("org.promasi.ui.exportwizard");
        URL fileURL = bundle.getEntry(COMPANIES);
        File file = null;
        
        
        try {
            file = new File(FileLocator.resolve(fileURL).toURI());
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		
		
		 try {
	         Digester digester = new Digester();
	       
	         
	         digester.setValidating( false );

	         digester.addObjectCreate( "companies", Companies.class );

	         digester.addObjectCreate( "companies/company", CompanyModel.class );
	         digester.addBeanPropertySetter( "companies/company/companyName", "companyName" );
	         digester.addBeanPropertySetter( "companies/company/description", "description" );
	         digester.addBeanPropertySetter( "companies/company/htmlDescription", "htmlDescription" );

	         
	         digester.addBeanPropertySetter( "companies/company/startTime", "startTime" );
	         digester.addBeanPropertySetter( "companies/company/endTime", "endTime" );
	         digester.addBeanPropertySetter( "companies/company/budget", "budget" );
	         digester.addBeanPropertySetter( "companies/company/prestigePoints", "prestigePoints" );
	         digester.addBeanPropertySetter( "companies/company/imagePath", "imagePath" );
	         digester.addSetNext( "companies/company", "addCompany" );

	         if (file != null) {
	        	 results = (Companies)digester.parse( file );
	         }
	        
	      } 
		  catch( Exception exc ) {
	         exc.printStackTrace();
	      }
	      return results;
	}

}
