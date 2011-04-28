/**
 * 
 */
package org.promasi.temp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalTime;
import org.promasi.game.company.Company;
import org.promasi.game.company.Employee;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.project.Project;
import org.promasi.game.project.ProjectTask;
import org.promasi.game.project.SerializableProject;
import org.promasi.sdsystem.SdSystem;
import org.promasi.sdsystem.sdobject.FlowSdObject;
import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.sdsystem.sdobject.InputSdObject;
import org.promasi.sdsystem.sdobject.OutputSdObject;
import org.promasi.sdsystem.sdobject.StockSdObject;
import org.promasi.sdsystem.sdobject.equation.CalculatedEquation;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.file.RootDirectory;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class GameMaker {
	public static void makeGame(){
		try{
			String path=RootDirectory.getInstance().getRootDirectory();
	        
	        ISdObject input=new InputSdObject();
	        ISdObject flow=new FlowSdObject(new CalculatedEquation("developer"));
	        ISdObject stock=new StockSdObject(new CalculatedEquation("flow"),0.0);
	        ISdObject output=new OutputSdObject(new CalculatedEquation("stock"));
	        ISdObject percCompleted=new OutputSdObject(new CalculatedEquation("50"));
	        Map<String, ISdObject> sdObjects=new TreeMap<String, ISdObject>();
	        sdObjects.put("developer", input);
	        sdObjects.put("flow", flow);
	        sdObjects.put("stock", stock);
	        sdObjects.put("output", output);
	        sdObjects.put(ProjectTask.CONST_PROGRESS_SDOBJECT_NAME, percCompleted);
	        SdSystem sdSystem=new SdSystem(sdObjects);
	        ProjectTask task1=new ProjectTask("Test1","Test1",sdSystem);
	        
	        input=new InputSdObject();
	        flow=new FlowSdObject(new CalculatedEquation("developer"));
	        stock=new StockSdObject(new CalculatedEquation("flow"),0.0);
	        output=new OutputSdObject(new CalculatedEquation("stock"));
	        percCompleted=new OutputSdObject(new CalculatedEquation("50"));
	        sdObjects=new TreeMap<String, ISdObject>();
	        sdObjects.put("developer", input);
	        sdObjects.put("flow", flow);
	        sdObjects.put("stock", stock);
	        sdObjects.put("output", output);
	        sdObjects.put(ProjectTask.CONST_PROGRESS_SDOBJECT_NAME, percCompleted);
	        sdSystem=new SdSystem(sdObjects);
	        ProjectTask task2=new ProjectTask("Test2","Test2",sdSystem);
	        sdObjects.clear();
	                
	        input=new InputSdObject();
	        flow=new FlowSdObject(new CalculatedEquation("developer"));
	        stock=new StockSdObject(new CalculatedEquation("flow"),0.0);
	        output=new OutputSdObject(new CalculatedEquation("stock"));
	        percCompleted=new OutputSdObject(new CalculatedEquation("time"));
	        sdObjects=new TreeMap<String, ISdObject>();
	        sdObjects.put("developer", input);
	        sdObjects.put("flow", flow);
	        sdObjects.put("stock", stock);
	        sdObjects.put("output", output);
	        sdObjects.put(ProjectTask.CONST_PROGRESS_SDOBJECT_NAME, percCompleted);
	        sdSystem=new SdSystem(sdObjects);
	        ProjectTask mainTask=new ProjectTask(Project.CONST_DEPLOY_TASK_NAME,Project.CONST_DEPLOY_TASK_NAME,sdSystem);
	        sdObjects.clear();
	        
	        //task1.makeBridge("output", "developer", task2);
	        //task2.makeBridge("output", "developer", mainTask);
	        
	        Map<String, ProjectTask> tasks=new TreeMap<String, ProjectTask>();
	        tasks.put(task1.getName(), task1);
	        tasks.put(task2.getName(), task2);
	        tasks.put(mainTask.getName(), mainTask);
	        Project project=new Project("Test","Test",100000,tasks,100,100);
	        project.makeBridge(task1.getName(), "output", task2.getName(), "developer");
	        project.makeBridge(task2.getName(), "output", mainTask.getName(), "developer");
	        
	        SerializableProject sProject=project.getSerializableProject();
	        String xmlString=sProject.serialize();
        
	        String separator=RootDirectory.getInstance().getSeparator();
            PrintWriter out = new PrintWriter(new FileWriter(path+"SinglePlayer"+separator+"Tutorial"+separator+"Projects"+separator+"tempProject"));
            out.print(xmlString);
            out.close();

            Company company=new Company("Test","Test",new LocalTime().withHourOfDay(9), new LocalTime().withHourOfDay(17),10000,0.0);
            SerializableCompany sCompany=company.getSerializableCompany();
                
            out = new PrintWriter(new FileWriter(path+"SinglePlayer"+separator+"Tutorial"+separator+"Company"));
            out.print(sCompany.serialize());
            out.close();
                
            Map<String, Double> employeeSkills=new TreeMap<String, Double>();
            employeeSkills.put("developer", 6.0);
            employeeSkills.put("tester", 5.0);
            employeeSkills.put("teamPlayer", 8.0);
            employeeSkills.put("systemKnowledge", 9.0);
            Employee employee=new Employee("James","Rowland","xd332211","<table><tr><td rowspan=\"2\"><img src=\"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/4QPmRXhpZgAASUkqAAgAAAAHAA8BAgAJAAAAYgAAABABAgAP"+
            									"AAAAbAAAADEBAgALAAAAfAAAADIBAgAUAAAAiAAAABMCAwABAAAAAgAAAJiCAgAFAAAAnAAAAGmH"+
            									"BAABAAAAogAAAAAAAABGVUpJRklMTQAARmluZVBpeCAzODAwICAAAEdJTVAgMi42LjMAADIwMDk6"+
												"MDM6MjggMDE6Mjk6MzcAICAgIAAAIwCaggUAAQAAAEwCAACdggUAAQAAAFQCAAAiiAMAAQAAAAIA"+
												"AAAniAMAAQAAAGQAAAAAkAcABAAAADAyMjADkAIAFAAAAFwCAAAEkAIAFAAAAHACAAABkQcABAAA"+
												"AAECAwACkQUAAQAAAIQCAAABkgoAAQAAAIwCAAACkgUAAQAAAJQCAAADkgoAAQAAAJwCAAAEkgoA"+
												"AQAAAKQCAAAFkgUAAQAAAKwCAAAHkgMAAQAAAAUAAAAIkgMAAQAAAAAAAAAJkgMAAQAAABAAAAAK"+
												"kgUAAQAAALQCAAB8kgcAEgEAALwCAAAAoAcABAAAADAxMDABoAMAAQAAAAEAAAACoAQAAQAAAEsA"+
												"AAADoAQAAQAAAGYAAAAOogUAAQAAAM4DAAAPogUAAQAAANYDAAAQogMAAQAAAAMAAAAXogMAAQAA"+
												"AAIAAAAAowcAAQAAAAMAAAABowcAAQAAAAEAAAABpAMAAQAAAAAAAAACpAMAAQAAAAAAAAADpAMA"+
												"AQAAAAAAAAAGpAMAAQAAAAAAAAAKpAMAAQAAAAAAAAAMpAMAAQAAAAAAAAAAAAAACgAAAAgCAAAY"+
												"AQAAZAAAADIwMDQ6MDg6MTYgMDU6Mjg6NTgAMjAwNDowODoxNiAwNToyODo1OAAPAAAACgAAADoC"+
												"AABkAAAALAEAAGQAAAB9AQAAZAAAAAAAAABkAAAALAEAAGQAAAAUBQAAZAAAAEZVSklGSUxNDAAA"+
												"ABQAAAAHAAQAAAAwMTMwABACAAgAAAACAQAAARADAAEAAAADAAAAAhADAAEAAAAAAAAAAxADAAEA"+
												"AAAAAAAAEBADAAEAAAACAAAAERAKAAEAAAAKAQAAIBADAAEAAAAAAAAAIRADAAEAAAAAAAAAIhAD"+
												"AAEAAAAAAAAAIxADAAIAAAD8A/0CMBADAAEAAAAAAAAAMRADAAEAAAAAAAAAMhADAAEAAAABAAAA"+
												"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"+
												"mpnZjcje1zb+LYzGYuy8FaWz/9k=\" align=\"left\" /></td><td><h3>James Rowland</h3><hr></td></tr><tr><td><p>He is working for UBM for 10 years, his skills as a coder are really low<br>he has only experience with scripting languages,<br>but he has a really good experience in testing.He worked as a tester<br> for 6 years before UBM.<p>He basically knows how to test almost every product in your department.</td></tr></table>",50.0,employeeSkills);
           
            Employee employee2=new Employee("Samuel","Garcia","xd332212",	"<table><tr><td rowspan=\"2\"><img src=\"data:image/jpeg;base64,"+
            																"/9j/4AAQSkZJRgABAQEAYABgAAD/4QiMRXhpZgAASUkqAAgAAAAHABIBAwABAAAAAQAAABoBBQAB"+
																			"AAAAYgAAABsBBQABAAAAagAAACgBAwABAAAAAgAAADEBAgALAAAAcgAAADIBAgAUAAAAfgAAAGmH"+
																			"BAABAAAAkgAAALwAAABgAAAAAQAAAGAAAAABAAAAR0lNUCAyLjYuMwAAMjAwOTowMzoyOCAwMToz"+
																			"MDowOQADAAGgAwABAAAAAQAAAAKgBAABAAAASwAAAAOgBAABAAAAYgAAAAAAAAAGAAMBAwABAAAA"+
																			"BgAAABoBBQABAAAACgEAABsBBQABAAAAEgEAACgBAwABAAAAAgAAAAECBAABAAAAGgEAAAICBAAB"+
																			"AAAAagcAAAAAAABIAAAAAQAAAEgAAAABAAAA/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcG"+
																			"BQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4z"+
																			"NDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIy"+
																			"MjIyMjIyMjIyMjIyMjL/wAARCABLADkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAEC"+
																			"AwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0Kx"+
																			"wRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1"+
																			"dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ"+
																			"2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QA"+
																			"tREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYk"+
																			"NOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaH"+
																			"iImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq"+
																			"8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDlvDen+b5Fqyjzbn5p3xysY6gfXgVL4o+xpqyyop/s6Eol"+
																			"2iHAkZeQo9ccZ+v1qLT7i8sQJGkhDBCCCxywJz/OuenOoa7cIsMTvBFlUCjAZifmb6k5rCMlLc6J"+
																			"Qa2IGvJZ7q6nKgNPkNt42gnOB/Kkjwg4HHuK6mx8C6rMF32rgE854rr7P4ZwvZ4lbbNjPqBUTnCJ"+
																			"Uac2c1o/jS0js/sF/anyyrKXj5GCcjj2NbXgp7OC/v4bd0kVyCkinPy9h+VcX4k8Ptol+bcqcH7r"+
																			"HjNc9Dc3umvK9nO8XmIyNtPY1hWw6q03GL3HGo4S1O48YfE14p7jTdHjQhcxvcsc899uP515z/bE"+
																			"3ofzqePw9eT2b3ClcryE5yw9j0z7ZrK8pv7prahQo048sCJ1aqd3od9rzTRqI4Y4o4ydp2g8Z/Gv"+
																			"SvCWnQ2mlW6KoyFHaub8U6G9zvawtpXdcMQ0JXbg56nFdD4b13SpIooPtirLgDafWs5J2VjsStJ3"+
																			"O7tkUp7U8jZIcdKpzXVvp8PnXE4SLP3utZzeLdML/ufPlTvIkRIFTKDcQvqcr8SbI3Dwy5xsB7da"+
																			"8wWBS5UgHsfavaPElsmt6LJPaN5jRqWAxjPtXkVrY3V1ei2tojLO7cL0/P06U6b9wyqx95Grpdqx"+
																			"tWhPl4IwgIxu9M+ornP+EV1f/nxi/wC+6S41u+0TWLi0uYF3xPtPZuOnI4Iqx/wnk/8Az7j/AL6o"+
																			"UKsG+VblupRkkpvVHuOqXED2zMrq0bghgD+lcXbafdXVpGttIyHCGFwoKquOQff3qDW9TXT7Uxs5"+
																			"N4GKqQcb07FgOK6HwjIt34atpAQXEeCB6jgj8xVu6940TjJ8prr5txoyRkEqGIBJycZ9azF8MtPc"+
																			"RSpLJCUXaUSQhWPPzEevNb9rJbrp8UUchkI6lVJAPoa1Y1jHz7cHFTFvUbtYow2qWtrtySSMHPeu"+
																			"O8KWdvZ6vqo2KZIpdvPZTkj+VdjdTBFOTzXGreR+HLrWta1AH7OVUoEGSTyMfrWSWthOyabPLvif"+
																			"JDJ46vBCABGkaMR3IUZ/w/CuOzVvVL+XU9Sub2b/AFk8hc+2TVTPtXpRVlY8ucuaTZ0c09w7PJM7"+
																			"ea56scnH1ruPh/q7SWt3pobbj96mD1HfH4/zrkHVTHuHYHIqrpmoy6bq8N2p2lSA/wDunr/jWleF"+
																			"4WKpT5Z3PdNKuXihUJbSnjkbwOc9a3re4mkjJkiaMZ4DHNYukXENxFHcsY8YyKbrXiG2to+ZEjGO"+
																			"OeTXm6npyceiLd3OAWZjgDua4TxtfgeHZCG4kuFQe+MmrQvrrWW2x7o7YnljwWrH+INpINAtPJXM"+
																			"cMuW+mMZohH95G5jUleLsedT2EYikuEOCAPlC5A/wFZ/H9ytq0nCohUAsDg56EelXP8AiW/8+C/9"+
																			"916Lh2POEA80HB2nqVI/WsqTIlfnPJGa0Cx3oc9OKznPJ/3q2q7ISOu8PXX2u1S3jvJoJoxgxrKQ"+
																			"GHqBXVWPh+OeQTS75W65c5/nXlAJU7lJDA8Edq3dK8TazbqUjv5QoGAGAb+YrinTfRnRCp0Z61Fa"+
																			"JBgKoA9qz/EV9plppzw38yb3Q7IRy7Htx/WvOdR8U626BTqEoB67AF/kKzLUmWXzJCXdhksxySc+"+
																			"tTHDczTbLde2iRXu4UiVfLXaWfIGeg9Kj3P/AHv51Yu+Z29sYqpXcjkZ/9n/4gxYSUNDX1BST0ZJ"+
																			"TEUAAQEAAAxITGlubwIQAABtbnRyUkdCIFhZWiAHzgACAAkABgAxAABhY3NwTVNGVAAAAABJRUMg"+
																			"c1JHQgAAAAAAAAAAAAAAAAAA9tYAAQAAAADTLUhQICAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+
																			"AAAAAAAAAAAAAAAAAAAAAAAAAAAAABFjcHJ0AAABUAAAADNkZXNjAAABhAAAAGx3dHB0AAAB8AAA"+
																			"ABRia3B0AAACBAAAABRyWFlaAAACGAAAABRnWFlaAAACLAAAABRiWFlaAAACQAAAABRkbW5kAAAC"+
																			"VAAAAHBkbWRkAAACxAAAAIh2dWVkAAADTAAAAIZ2aWV3AAAD1AAAACRsdW1pAAAD+AAAABRtZWFz"+
																			"AAAEDAAAACR0ZWNoAAAEMAAAAAxyVFJDAAAEPAAACAxnVFJDAAAEPAAACAxiVFJDAAAEPAAACAx0"+
																			"ZXh0AAAAAENvcHlyaWdodCAoYykgMTk5OCBIZXdsZXR0LVBhY2thcmQgQ29tcGFueQAAZGVzYwAA"+
																			"AAAAAAASc1JHQiBJRUM2MTk2Ni0yLjEAAAAAAAAAAAAAABJzUkdCIElFQzYxOTY2LTIuMQAAAAAA"+
																			"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAWFlaIAAAAAAAAPNR"+
																			"AAEAAAABFsxYWVogAAAAAAAAAAAAAAAAAAAAAFhZWiAAAAAAAABvogAAOPUAAAOQWFlaIAAAAAAA"+
																			"AGKZAAC3hQAAGNpYWVogAAAAAAAAJKAAAA+EAAC2z2Rlc2MAAAAAAAAAFklFQyBodHRwOi8vd3d3"+
																			"LmllYy5jaAAAAAAAAAAAAAAAFklFQyBodHRwOi8vd3d3LmllYy5jaAAAAAAAAAAAAAAAAAAAAAAA"+
																			"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABkZXNjAAAAAAAAAC5JRUMgNjE5NjYtMi4xIERl"+
																			"ZmF1bHQgUkdCIGNvbG91ciBzcGFjZSAtIHNSR0IAAAAAAAAAAAAAAC5JRUMgNjE5NjYtMi4xIERl"+
																			"ZmF1bHQgUkdCIGNvbG91ciBzcGFjZSAtIHNSR0IAAAAAAAAAAAAAAAAAAAAAAAAAAAAAZGVzYwAA"+
																			"AAAAAAAsUmVmZXJlbmNlIFZpZXdpbmcgQ29uZGl0aW9uIGluIElFQzYxOTY2LTIuMQAAAAAAAAAA"+
																			"AAAALFJlZmVyZW5jZSBWaWV3aW5nIENvbmRpdGlvbiBpbiBJRUM2MTk2Ni0yLjEAAAAAAAAAAAAA"+
																			"AAAAAAAAAAAAAAAAAAAAAHZpZXcAAAAAABOk/gAUXy4AEM8UAAPtzAAEEwsAA1yeAAAAAVhZWiAA"+
																			"AAAAAEwJVgBQAAAAVx/nbWVhcwAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAo8AAAACc2lnIAAA"+
																			"AABDUlQgY3VydgAAAAAAAAQAAAAABQAKAA8AFAAZAB4AIwAoAC0AMgA3ADsAQABFAEoATwBUAFkA"+
																			"XgBjAGgAbQByAHcAfACBAIYAiwCQAJUAmgCfAKQAqQCuALIAtwC8AMEAxgDLANAA1QDbAOAA5QDr"+
																			"APAA9gD7AQEBBwENARMBGQEfASUBKwEyATgBPgFFAUwBUgFZAWABZwFuAXUBfAGDAYsBkgGaAaEB"+
																			"qQGxAbkBwQHJAdEB2QHhAekB8gH6AgMCDAIUAh0CJgIvAjgCQQJLAlQCXQJnAnECegKEAo4CmAKi"+
																			"AqwCtgLBAssC1QLgAusC9QMAAwsDFgMhAy0DOANDA08DWgNmA3IDfgOKA5YDogOuA7oDxwPTA+AD"+
																			"7AP5BAYEEwQgBC0EOwRIBFUEYwRxBH4EjASaBKgEtgTEBNME4QTwBP4FDQUcBSsFOgVJBVgFZwV3"+
																			"BYYFlgWmBbUFxQXVBeUF9gYGBhYGJwY3BkgGWQZqBnsGjAadBq8GwAbRBuMG9QcHBxkHKwc9B08H"+
																			"YQd0B4YHmQesB78H0gflB/gICwgfCDIIRghaCG4IggiWCKoIvgjSCOcI+wkQCSUJOglPCWQJeQmP"+
																			"CaQJugnPCeUJ+woRCicKPQpUCmoKgQqYCq4KxQrcCvMLCwsiCzkLUQtpC4ALmAuwC8gL4Qv5DBIM"+
																			"KgxDDFwMdQyODKcMwAzZDPMNDQ0mDUANWg10DY4NqQ3DDd4N+A4TDi4OSQ5kDn8Omw62DtIO7g8J"+
																			"DyUPQQ9eD3oPlg+zD88P7BAJECYQQxBhEH4QmxC5ENcQ9RETETERTxFtEYwRqhHJEegSBxImEkUS"+
																			"ZBKEEqMSwxLjEwMTIxNDE2MTgxOkE8UT5RQGFCcUSRRqFIsUrRTOFPAVEhU0FVYVeBWbFb0V4BYD"+
																			"FiYWSRZsFo8WshbWFvoXHRdBF2UXiReuF9IX9xgbGEAYZRiKGK8Y1Rj6GSAZRRlrGZEZtxndGgQa"+
																			"KhpRGncanhrFGuwbFBs7G2MbihuyG9ocAhwqHFIcexyjHMwc9R0eHUcdcB2ZHcMd7B4WHkAeah6U"+
																			"Hr4e6R8THz4faR+UH78f6iAVIEEgbCCYIMQg8CEcIUghdSGhIc4h+yInIlUigiKvIt0jCiM4I2Yj"+
																			"lCPCI/AkHyRNJHwkqyTaJQklOCVoJZclxyX3JicmVyaHJrcm6CcYJ0kneierJ9woDSg/KHEooijU"+
																			"KQYpOClrKZ0p0CoCKjUqaCqbKs8rAis2K2krnSvRLAUsOSxuLKIs1y0MLUEtdi2rLeEuFi5MLoIu"+
																			"ty7uLyQvWi+RL8cv/jA1MGwwpDDbMRIxSjGCMbox8jIqMmMymzLUMw0zRjN/M7gz8TQrNGU0njTY"+
																			"NRM1TTWHNcI1/TY3NnI2rjbpNyQ3YDecN9c4FDhQOIw4yDkFOUI5fzm8Ofk6Njp0OrI67zstO2s7"+
																			"qjvoPCc8ZTykPOM9Ij1hPaE94D4gPmA+oD7gPyE/YT+iP+JAI0BkQKZA50EpQWpBrEHuQjBCckK1"+
																			"QvdDOkN9Q8BEA0RHRIpEzkUSRVVFmkXeRiJGZ0arRvBHNUd7R8BIBUhLSJFI10kdSWNJqUnwSjdK"+
																			"fUrESwxLU0uaS+JMKkxyTLpNAk1KTZNN3E4lTm5Ot08AT0lPk0/dUCdQcVC7UQZRUFGbUeZSMVJ8"+
																			"UsdTE1NfU6pT9lRCVI9U21UoVXVVwlYPVlxWqVb3V0RXklfgWC9YfVjLWRpZaVm4WgdaVlqmWvVb"+
																			"RVuVW+VcNVyGXNZdJ114XcleGl5sXr1fD19hX7NgBWBXYKpg/GFPYaJh9WJJYpxi8GNDY5dj62RA"+
																			"ZJRk6WU9ZZJl52Y9ZpJm6Gc9Z5Nn6Wg/aJZo7GlDaZpp8WpIap9q92tPa6dr/2xXbK9tCG1gbblu"+
																			"Em5rbsRvHm94b9FwK3CGcOBxOnGVcfByS3KmcwFzXXO4dBR0cHTMdSh1hXXhdj52m3b4d1Z3s3gR"+
																			"eG54zHkqeYl553pGeqV7BHtje8J8IXyBfOF9QX2hfgF+Yn7CfyN/hH/lgEeAqIEKgWuBzYIwgpKC"+
																			"9INXg7qEHYSAhOOFR4Wrhg6GcobXhzuHn4gEiGmIzokziZmJ/opkisqLMIuWi/yMY4zKjTGNmI3/"+
																			"jmaOzo82j56QBpBukNaRP5GokhGSepLjk02TtpQglIqU9JVflcmWNJaflwqXdZfgmEyYuJkkmZCZ"+
																			"/JpomtWbQpuvnByciZz3nWSd0p5Anq6fHZ+Ln/qgaaDYoUehtqImopajBqN2o+akVqTHpTilqaYa"+
																			"poum/adup+CoUqjEqTepqaocqo+rAqt1q+msXKzQrUStuK4trqGvFq+LsACwdbDqsWCx1rJLssKz"+
																			"OLOutCW0nLUTtYq2AbZ5tvC3aLfguFm40blKucK6O7q1uy67p7whvJu9Fb2Pvgq+hL7/v3q/9cBw"+
																			"wOzBZ8Hjwl/C28NYw9TEUcTOxUvFyMZGxsPHQce/yD3IvMk6ybnKOMq3yzbLtsw1zLXNNc21zjbO"+
																			"ts83z7jQOdC60TzRvtI/0sHTRNPG1EnUy9VO1dHWVdbY11zX4Nhk2OjZbNnx2nba+9uA3AXcit0Q"+
																			"3ZbeHN6i3ynfr+A24L3hROHM4lPi2+Nj4+vkc+T85YTmDeaW5x/nqegy6LzpRunQ6lvq5etw6/vs"+
																			"hu0R7ZzuKO6070DvzPBY8OXxcvH/8ozzGfOn9DT0wvVQ9d72bfb794r4Gfio+Tj5x/pX+uf7d/wH"+
																			"/Jj9Kf26/kv+3P9t////2wBDAAUDBAQEAwUEBAQFBQUGBwwIBwcHBw8LCwkMEQ8SEhEPERETFhwX"+
																			"ExQaFRERGCEYGh0dHx8fExciJCIeJBweHx7/2wBDAQUFBQcGBw4ICA4eFBEUHh4eHh4eHh4eHh4e"+
																			"Hh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh7/wAARCABiAEsDASIAAhEBAxEB"+
																			"/8QAHAAAAwACAwEAAAAAAAAAAAAABQYHAAQCAwgB/8QAPBAAAgEDAgQDBAgFAgcAAAAAAQIDBAUR"+
																			"ACEGEjFBByJRE2FxgQgUMpGhsdHwIzNiweFCghUWQ1KSsvH/xAAaAQADAQEBAQAAAAAAAAAAAAAD"+
																			"BAUCBgEA/8QAJhEAAgIBBAEEAgMAAAAAAAAAAQIAAxEEEiExQQUTIlEykSMkQv/aAAwDAQACEQMR"+
																			"AD8AkvAsUNJbJOIq6lWaoAIpIWXPNKeURoq+g5gcdzgdetL4h4atts8Ma+S6yRx10iCrqahjzFqg"+
																			"NzKoxjI5uVBjBI7ZJ0i8OXK1c9omnKrDS1rztCrHOCrcijPUh+U/LR3xA4tt8lUtzrJ0qKS2Qiak"+
																			"giPklq5MiM7/AGinLITldvIe+6a2hj3HTWV8RGW4LTcOXy53eSSfim4yCgjjmHnpYUxzk9gSAF6f"+
																			"LB0u0VWIrXV0KMc1LxmRwDuFLEr82Kn/AG66qmsrb3cHrq4l5nHKAh/lj/Siqeij03Pz0SpLZUSI"+
																			"kUYbochmHXbP+NbZAIMEngTWpUWKRWRA4Vw2DvnfOD7ttP3hlxhaOHpmhuNPKlLNTvA8kY5zzF+a"+
																			"NsH/ALcsO+xB7aHWzgG9VlIZ46Nxynsu/wD837DQW9cN3CgkYVNPKERvMWjIx8yPyOlXVLPjmHC2"+
																			"IN2JW/EZ7Fxdw/HebVVx1FVSwK86qcuAMI5IO/Qqcnrj46ePCb6pHwVR0tNjngyk3rz56/A/vcHX"+
																			"lJqq429Q9HO8TshWR4sqSp6rkdvXPrpt4W8XK+wcGXGiSBv+JlPZ0k2PKg2AJH9ILn3kj5yPU/Tb"+
																			"b6lVOeYxRqlUndKr4o+Nli4Sq5bTbac3i5xErKivyRQn0ZsHJ9w+ZGvN104rku1xnuNxZ5auocvK"+
																			"5PU/ppaqJJJ5nmldpJJGLOzHJJJySffrrwTvjVTRek6fSJhRk+TFX19rNkHAl+WFbXTq7WoUwYYK"+
																			"1Kbke4nY/DbS1DZ6njbi16Sl5YKalUcxU5Bc/A7n9NHeN6apeKRErKhHXoPrJUP6nlOB92NMP0d6"+
																			"KFLeaplJaR+ZmO5Y4Gsbiil/MobA7hD1DfB3gpQ86yV1QQMYVUAJAznvt+GqNa/Cfh2l5X9nPMRv"+
																			"5n3P3D46ZbSMMCAdxpkpCojDddvXXgtdxyYVq0r6EE2i00FBGtPBTpHGowEA2A0meNPCVHc+Hpah"+
																			"cRmIFsAYGeuf8/lqi1AUTeUde2gnGkAqbBVQucBoirYGcZHppEE12ZmsBxPEFZTfwgGUHDAHG2cY"+
																			"76GVNu9oXkEZ5j5jv3zg/l+Wnvi2309FVEorBFLMQzc2/QD785/TQeiVMxgklcEEfMZ/IaqmwgZE"+
																			"lGsBsGFOGrDQx2aQy0UC0kygtHUQhlfPTEg80bb7N66mPE9rW13+soFjnjWGTCrNjn5cZGcbHYjc"+
																			"bHVt4So46F+ZjMWCeaNnLqFYZBwMMuQc5wRg6X+JfC+6V98qqykvEbQSsDH7XZgMDbYAbdBgdANJ"+
																			"6bVBLGDtxKWq0fvVoyD9YlL8SbXaWgero6601PlOISmWDdh5MnO/TbJHTQ/wMlgorVLS3CSOlljq"+
																			"SqiTKcwIBBGexBzq6XahoqepNZ9TpjID5ZPZAsPnjUV41tFul4yuNZcaI3GSrNOaZTM6LGWBTDcp"+
																			"AxmMkk5/toxQH4mGYf7EulmiSaINDUrMgBPkbO3y0VWSKDlNTUJEm+7sF+PXUM4Hrqnhzii4WiG3"+
																			"mm+qjnndpWZZEyv2CwUnIbKg7kKTgDVB8RqSivMMMbQxTsqEqWBGDjsB3218UWs4MwC1nUYLlxfw"+
																			"1Qycj3SBjt9g835a+mut95pHNHVw1MTjlJjcNjI7+nz1Famy3O3m2mw01prY5lK1jzUoZqbDgEeY"+
																			"hmXlyc782Ogzp84FscgdaxqGjpJhkFqZOQMM98AbHrgjQNTWAAw4n1akZz4kX8aLLU2q8mAIfZyZ"+
																			"kR+X7QJxgfAA6S7bS1tZWRUNupKmtqWB5YoI2ldsDBIC5JHfpr0R9IO1ir4Ypq0JiSlnAJx/pYYP"+
																			"48v7OuHgvwtQ0HDxrGdRX3CnDMwOG5GPMqD3d2x12HQa0Ls1j7gTp99p8Ty1HxnU267VNPU2+kkM"+
																			"czIZRFyzAA4wSOvb7tMaeK4jUItDOwA6lgNDvpK22jtvi9dI6Mcvt0hqZkxukkkas2feSeb/AHd9"+
																			"Tjm9/wCOnzoqLQGIiJ1t9DGsN1Pc1Vxa72oVE0YqLfKvkrKfzKh/rA6D+rppUsxivXE90p3ZZEEF"+
																			"OMZzvzSk/wBtBuL+KaPh1rnR0VVHNNWkssSHIhJXB2HTfffudBfA26iPjqppp3LSVlMrKWPUxsdh"+
																			"8pPw0E0v7ZcjiUhqUFgrz3LHJbaekgpqWFGHtZkDMT8zgfAHc6brjb4pEgBUEFRjO2dCrrWUdDJS"+
																			"1tSvtERiTEv2s4wD/bHv92jElbPcCqx0cQhYHlJfzp6Z2x/fptpRtx5EbLLkCfaa0rC/L7GNgd8n"+
																			"7XzGibIlISiY2GNhrYglIYOxDbbEjvrQuUwR/aHqflgaJYo2wIO48xS8UFWfhC5Ix6RFgTvgjcaH"+
																			"eEM89XwFb5c8ohQxhtmLBZXIHXsDsdbvE8YulBUUbsyxyIysQcH999TDxM4muXh14U01jscInmuH"+
																			"1iI1RPmhjGOZ8AbnDbenXfGl6lLFUHeT+p49oQlz9SA+L18j4i8S79d4n9pDLVssTA7MiYRCPdyq"+
																			"NKWCd/XX1ySdjnJ+OuIDY2zjXQgADAnNMxZi33HZZKkyvPKDEQCIxIMFjg7nPX/Otjhq9SWG+0Fz"+
																			"P26epEjsd2ZDlXOOw5SfuGtmeANAjHEgzk53zoLdTHHJLHEgRjgOR15SDkfl9+qFlYZCDMoxVgRP"+
																			"VFxq1qEobhMks8cpUqIyOmNuvz0z227NEknJaKtw784xKoVdum/w1HPAfigXCzJZrixkkoR7Ncnf"+
																			"lxlT9w/DVxsEEZm9qTN7MjKrzk/h0xrlyCjFTOqosqevcRmELPcKqqV3NHNAmcKHZd/u113eYg8m"+
																			"cs2wyNdl6uNPSxlYJCz4/wDHY6Q75xbDTZlnl2XIXsSe3v8AUaGQTB5A6hK+10NLTu0ki5UZOG3G"+
																			"/wCmon9IKuZavh+kEgWZKaSbr9n2jAH/ANcaeLXJX324isq1MNMDzJGRucdz+/TUk+kTM7+IUaMG"+
																			"5UoY1XPQ+ZtF0Sf2QT9GJ6yz+E4iPPZqRhUVqyvByIDyrHzojZxkgbhD6jODtjcaAc+DhkVz3bHX"+
																			"TbQTtFTipVx7RUCkHcEHGQR3BzrdPC9irSaqC6rRJJuKdypMZ7jJ3Iz092NW7K9pzIw5nYZDAGSV"+
																			"Tscrjvk6CXcqZGZgS7kZPwHb7tFoGaERCdI2glPKrcuHTuM+vpobf0Q1EaIOXqc9znH6HVC3iszA"+
																			"MIeH95qbFxPSVFNGshmxFJGx5c77EHsd/wAdXum8WEgi+rS2mvilA5ThFII6bHONeYkLAgqWVkbY"+
																			"g4xqn8HcY26shiprzKtLWphRK4/hze8nsfjt7+2omoqydwEe015Ubcx0u/GnEF3YQ2u2+wXf+LJu"+
																			"fuH665cP8L1LzCuucrVMx3/iNsCfQdtM3DFDTVUSTQyRujbq6YKtt2P766Ympoo1EYPMPUH9+mkG"+
																			"OTHxk8kzSo6dIk5AoC9gMY1PPHbhFrxTLcaZQaqmygIH2vKDj4ddP93v1hsURlvF0pqNAchZHy7f"+
																			"BBlj8hqV8beKxvcM9p4bonpqZyGkrKgD2h9ORei/E5OPQ68ppua5WQT62yoVlWPcjkTT09HJK0eF"+
																			"RvOG9emNbi1ylQWCgkA42OM7+o1yvyhKNaSPbOCfduMaHMkSnlIGQANx7tdKjESE0MzkmFsnOGGM"+
																			"9vMND7qT9Yj3P2P11ms0TUfhPF7mp/1JB7v7DXJv5IPfWazSQ6mplBXVtE/tKOsqKZwueaKQofvG"+
																			"iH/MnEU2I5r9dZEPVXrJCDt7zrNZpc/lGk6mhSEyVETSEuWYFi2+fjphtYBjfPeRtZrNOVwJg+57"+
																			"1NQT1AH5DQ2b+YflrNZog8zB7n\" //2Q== align=\"left\" /></td><td><h3>Samuel Garcia"+
																			"</h3><hr></td></tr><tr><td><p>He is working for UBM for a year, his skills as a"+
																			"coder are low<br&gt;but he has a really good experience in testing. He worked as"+
																			"a tester<br>for 6 years. <p>Since he is new he is only working with new project"+
																			" managers,<br>he knows how promasi OS works and has tested it many times. He hasn't"+
																			"any knowledge<br>in our other products though.</td></tr></table>",40.0,employeeSkills);
            Employee employee3=new Employee("FirstName3","LastName3","xd332213","CVadsasdf",40.0,employeeSkills);
                
            Map<String, Employee> employees=new TreeMap<String, Employee>();
            employees.put(employee.getEmployeeId(), employee);
            employees.put(employee2.getEmployeeId(), employee2);
            employees.put(employee3.getEmployeeId(), employee3);
                
            MarketPlace marketPlace=new MarketPlace(employees);
            xmlString=marketPlace.getSerializableMarketPlace().serialize();
                
            out = new PrintWriter(new FileWriter(path+"SinglePlayer"+separator+"Tutorial"+separator+"MarketPlace"));
            out.print(xmlString);
            out.close();
                
            out = new PrintWriter(new FileWriter(path+"SinglePlayer"+separator+"Tutorial"+separator+"GameInfo"));
				
            out.print("<img src=\"data:image/jpeg;base64," +
            			"/9j/4AAQSkZJRgABAQEASABIAAD/4QIwRXhpZgAATU0AKgAAAAgACQEPAAIAAAAFAAAAegEQAAIA"+
						"AAAJAAAAgAESAAMAAAABAAEAAAEaAAUAAAABAAAAigEbAAUAAAABAAAAkgEoAAMAAAABAAIAAAEx"+
						"AAIAAAALAAAAmgEyAAIAAAAUAAAApodpAAQAAAABAAAAugAAAABTT05ZAABEU0MtRjgyOAAAAAAA"+
						"SAAAAAEAAABIAAAAAUdJTVAgMi42LjMAADIwMDk6MDM6MjUgMjE6NDQ6NTEAABaCmgAFAAAAAQAA"+
						"AciCnQAFAAAAAQAAAdCIIgADAAAAAQACAACIJwADAAAAAQBAAACQAAAHAAAABDAyMjGQAwACAAAA"+
						"FAAAAdiQBAACAAAAFAAAAeySAQAKAAAAAQAAAgCSAgAFAAAAAQAAAgiSBAAKAAAAAQAAAhCSBQAF"+
						"AAAAAQAAAhiSBwADAAAAAQAFAACSCAADAAAAAQAAAACSCQADAAAAAQAQAACSCgAFAAAAAQAAAiCg"+
						"AQADAAAAAQAAAACgAgAEAAAAAQAAAJagAwAEAAAAAQAAALukAQADAAAAAQAAAACkAgADAAAAAQAA"+
						"AACkAwADAAAAAQAAAACkBgADAAAAAQAAAAAAAAAAAAAAAQAAAFAAAAAEAAAAATIwMDg6MDE6MTgg"+
						"MjE6NTc6MzMAMjAwODowMToxOCAyMTo1NzozMwAAAA/hAAACgwAAAAQAAAABAAAAAAAAAAEAAAAh"+
						"AAAAEAAAAFEAAAAK/9sAQwABAQEBAQEBAQEBAQEBAgIDAgICAgIEAwMCAwUEBQUFBAUFBQYIBgUG"+
						"BwYFBQcJBwcICAgJCAUGCQoJCAoICAgI/9sAQwEBAQECAgIEAgIECAUFBQgICAgICAgICAgICAgI"+
						"CAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgI/8AAEQgAuwCWAwEiAAIRAQMRAf/E"+
						"AB8AAAICAgMBAQEAAAAAAAAAAAYHBQgACgMECQILAf/EAEIQAAICAQMDAgQEBAUDAQYHAAECAwQF"+
						"BhESBxMhADEIFCJBFSMyUQlhcYEWFyRCkQozobEYJUNS0fA0RGJygsHx/8QAHQEAAQUBAQEBAAAA"+
						"AAAAAAAABwMEBQYIAgEACf/EAEIRAAECAgYGBwUHAgYDAAAAAAECAwARBAUhMUFRBxJhccHwBhNS"+
						"gaGx0QgiQpGyFCMyYnLC4RXiFjNTgqLxJEOj/9oADAMBAAIRAxEAPwD0KrqWiHcYtEp8g+vu5LQh"+
						"gkiZfq/2qvv66ctzsxlpHHjf0OQ5OGxOJOQHE+/7+jwhokzgaqeNwiF1Jiq1+nLzhTnsT4Pk+gqv"+
						"pWKpTaPslBKQvJh/49OV3pbklgFYj7+x9EEuMoSVYWVo5YOO58g+naaWpCdWEepSozhA4vDWMRfr"+
						"syoVOzD+Xn056+TmqLVRm5uzb+D429R2dxtRakV2B40ljO/g+SP29D9fILb2lPCOJD7E/f14596N"+
						"Yx4klFghy0cl8/ZEagCMAHl+/wDX1J2XZ5ezw3UbAEf7vQHp2VpXJDEAeAFPtv6amDoDIXYIlkiM"+
						"zuIoldwq77e5LeB/U/t6r9Oebo6S44ZACZnsvMSdGbU6QlAmT5xy4YyFVrQbykbl238b+mRj8fHL"+
						"D228SH9hvv6+6ukcjgdaaf0nqHC34or6SyxvUZWEsSLuzhxv9O5XfxuAd9vRJhuen+vdXTGmsrk7"+
						"GHqqkFyL5VZZFlksxMQx/SqpGqsZBxYLN7H6gAx0m0u1XRiUMK6xRTNMrUq2awnI43QQan0e0+kS"+
						"U4nUSDIzvG2RlMRGY7SmPy2VXFm5ioMnJGJVjnkCfSTxB29yCQR/Zv2Prt3tA2MNLJRy2LmpWSCU"+
						"DJ9Mg3P1KfZh/MH0PdPdWJn+vupspBaoy7tDjpPkYm4SRQ250CkSqoQgqxcqPDmQKXXYm2V3t1KD"+
						"yQJBNhUpTyfK2Ye3AJRZ3LM7NyibizqNvp2BLbcB6GLWnKlGkh3q5tKl7vxC8GSrjdcR8ourmi5n"+
						"7PqdZ94J24HuwisMencfEghAHeA5Hx/49dyDSVKwD+Sqt43JHpx5fQVSXJzWMHceA9xo0oXHEZnb"+
						"bkOy5P5n077AgHZWPnbf0PVBbrZCWrbpy050+lo5EKMv9j6M/R/prQq0RrUVyaryk2KHdxExAxrj"+
						"o3SqAvVpCLMxaD3xFS6MpVK0bqkKOU2323O/rSW/iRabnxPx9/E3o2oPlY7OYxtyWcDykE+Jpzt/"+
						"y0xH7edvufW8Xl5Z3KRs/Ie+w9vWlt/GHxd6r/ED6s16o7RzuG05ckmUAmOJMfHWYgffcwkf22+/"+
						"qfWpSm1T5tiGIndHmrds0L80mWlj7Oj8SGiqIPPzUo/3eff/AGgfudv3Yelg135aC5qHIxJJZmjM"+
						"FWH347jYf0GxO5Gx23+7bei3MWK2TaPHwt2dJ4skb7+LEnkkn99tz/yT/uHoDsW47E756/zjqI7f"+
						"LR+59z5/bffb+4/YD1HAE3x6lED+DtY/D4VLkwkkuyTPCAoAYIhIP9v0k/uSP29Z6GKBx8ktuS8I"+
						"2h7jkKW8Asxb3/ofH9/WeuENqImIXIAMo3t8hbuTwMJGCLKw4j1wUoLfOKQoZVQEsN/c+ujjJoch"+
						"WimkkaaLwVZW9vRVXyNOISQJEvdA3J3HhvR1W3q+6BFJaIJmY+Extq6ZrVjuwVgN0UON2Pv6JMbl"+
						"aJqvXeV0RR52Pk/z9QVZ7GTEkf1Rv9tzsD+/j+/ppdMtB4XUutMFp3PU7t+nZ2WT5WVYmUGRELOx"+
						"9kHP2HkkqPv6rnSGtmaBQ3KXSfwtiZlfZlOXCJmqatcpj6KM1+JZkJwqtW2YkowtBMXZn3VR7ke3"+
						"/wBP+fU1gOiXUzVWjbWvMbSrT4KLkTHHaj70agupcx7huO8bj/5jxJAI8+r9dI+l1Lp1rHqLpnHS"+
						"53OabkqfnGxTr2InPGIiNpz45BJQwj4gEOSwBAB6mlL8uN6N9SbWJgw1WhLcWGOFKLxQqssj7qpY"+
						"fUdpAF2AUfQPG7bZp6Te0a4lKkVQ0JCRClAnWFkwU2apmb5m6DTUuhtBIXWDhmZghOBwM7ZjuELh"+
						"uh+F01090nqjG39S1szNZpwTwZBI2WeZ3XcqgAZVA7jDYOCqedidy2+qNjDw5DpPXmr1aeox/qnl"+
						"s0ykpQQSBeXYAGwbf6fPFgvsD6+tXpj8f086d/hww9ev+OoyJDYkqpMRY3I3m3eQcgN4/B5hZNws"+
						"ZB6/VLIzQ5PpZHErxuMdBIorZJKkIZq8x8BwWi3G4BfwwJX3HoAVz0qrGnvpVTHlKKVkCZAkCbRO"+
						"+WFuGyCvVtQUGhtlNHbCZgT2kCw790cOuoY5evGJgyUeOFeGnHwDySsCx+Wfdok+oPufDjdSAoP/"+
						"AG29R8GPkk+JjOtYaSwFzscsaWcgtlI9rgZeEaeU2YuRE25WQu5BR19dzqPavV+vcMSfj8NRawCT"+
						"RFYEYBaYbafywA3IZdtk3+/cO0Fjp79v4mNboZLlZK+ZFiXniI6gljWeNju++8isCu1gfW6cQR9B"+
						"AqrH/rO1XkIl3VglQ2DziA0T8/H1y1ML1vNWmecdtr+SitSPxyNhV4yKAJVChQinYoqqDuVbe19u"+
						"WK/AVqcGt/hluGsCDK6hiCQoY8ZASBureW3A225eqM9PtTu3WrqvLj56OQWBbTzGjB2ChTKWg/da"+
						"YcWYHiG7XueJVjufVtqWSj5R4m5WsCd+4GgnQ94803XdVO535eGTfcHxxDePKOSG0GWfHnzhN0TU"+
						"SNnCD+OjZfIp2q0iRSZaozn5YS8i1UAkhiOz523dCR4C+Pr2+sdDHdw2JjysKWawpRENNMh7Z7hQ"+
						"8bGwJbbieBH/AMg23Zto6rkYIp8as5M7R2qDQjkW+WkEZAA2/Qx+nwd12J33JIEimWQ1IKQiWVUp"+
						"cgoh/NH+oG2xY7MPO438+5PufStGpDjZC0GSgRIiyW7uhF9hDgKFiYOBtgB1PUr4jJfJrLYeJ4+f"+
						"5yBXjHJl2OxIO4Xfce4Ye3rRM/iY9WtVdXfjZ6uZC9SpYlafc0nRgg8/KUqFyxCGZvBd2KySMT5B"+
						"lKqdlQDeX6qy1ocxQhrMKsDy2YwOJA+mXfj59iC58Dx7kfSQPWhx/EcpS6f+MvrklJRG02o83wby"+
						"PpkutNuD9vEoP8h5/b1tXoFTXKbUbdJfUVKN5xMlEcIzF0uoaKNWqmGhJM7t6ZxSzLmGedNP0pDD"+
						"hahDWJN9ubD3B/mCD/cH7KPQLnL65ORxCI62JrD6V24jb9/+P+B+259SWRsiKKLD15D9TKJX235H"+
						"7/29vH8gP39AupbMctkY+ixFaMBHI88mB8k/v/8A2f8AzOFsAxHtmdsCyVZM5eyKQSiGFJA45pv4"+
						"IAHj+x/p7es9RE2RsY/IWVxkjxEom/EA7+W3/wDJ9Z6QC0ixQnHS0rJmmN7XTeLyVGlDDIJZUCry"+
						"A8nf0T1aUmPlmt3EMcfPmnJvLe239PTM01jkvRxsIwXA5MQPPoL1zWzE9nhju0iIw+t1Hj+XrQQp"+
						"HWOlF0D4taiARBjgK0Fx453k7bAcgqn7erF9FanZ6r6UaKCactDM26VxMECvEd238Rj3HL+ew8nx"+
						"WrRkfy8kb2pO9ZcbBR+/9Pt6tD0brI3V3TkIWBbEVKSWJXL8mLRzFiqr4YgR/wC7wBuBuzDYMaaV"+
						"FuoaVLswRtGw162YnnFk8B/hmDVnV60ghlzkUcxkSSvIHWqOyCC/PtHeRW32XcFC/LhsPQrFJBF0"+
						"K1PZqWpZ5XniEjR3e4/hY2KyOfDAhjyjA/SSP1e81pqC5lsj1tKTylT3hERkBOpbcqAlYDdWPbVQ"+
						"GJVwVjPhSTGrWSn0Gs1bcdxVnsQgEwoPAjgI+lf1qeIKkeSeLH7j1+e7iSlKhO8C8zvkY1sg6xE7"+
						"L46mtJG/wn0woCHL5eWXJCVbIaPJMrG0AA0zAL522Gw3IBj+xPqH6vwTtrDp81GZKmRXD1FE7UI5"+
						"5N/l51JLP9Mu58ESeA2223I+jDW8sdLD9KcY8de0q3JDEHq9wM4lB3SOIhfZh/MkoR5Dn0A9ScxJ"+
						"d1/oileh/DUkxwWKSV2jSbjFOjcVAPdPuDv4/Uvum5coV99/v4jOE+pBb/2x9dURSt9fZbCw42a3"+
						"8ujHj35JRtFWZSz78VPjddhtsfr8qfQFT1Cl3rzrx64rrY+YmsAebEx3aNi4h5bRDyGPn6Sxk22m"+
						"29Dd61ffr9kLMuopsjivkJ5+zZtmGCo3aqbhQPqI38soBHJt/wDefUfgcrLR61a+khgyVhHgctHA"+
						"0NeHn24TyeT9TEctz9zvwH6NvTejmxq2dqvIbIUdSJqnZYIhtNX436z9VWztmrNCMZZCtk7TSkD5"+
						"uftrFGB9O4LdrcEIBIDuUUm0kTTQUBbqrDBh5fwyYCNzZxzEKducx/NrN5IKL4HJ0HunqkGhdVZ7"+
						"J9cOq1OSS1FYGOycDtjMc8tiSIzyMUkldeK+CvcK+BxXiQHJNmqeYBhgyVayuSmOFwFp5a8RoTwc"+
						"iPHyrfRd8doGT7/kuB4YlukEMoJ/Nzz3x4SCtQGyGWmYtQTw2rE8NJVmdgZJN4p+3JuqxyeVn23I"+
						"2bbkSG3PMej7A5d5LUOOkrxkkSiMxozFQzJIv0t5G4I8+zEjbYNt6TlHI02yVqKQ9hfmcjHLJVgC"+
						"Wypj+nlSk8RrvvuE+oKHHuin1L6ZsJwhuQtTuVonp252hkeWtAXcrwnYkSo2/JTwP6vpG/cT07Ch"+
						"zsHO6GxSL58zjh69zSUm0zLEWsWhNeZWG+/Fu1sP5bbex9gQB7etI/8AipVbWM+LvX1mVe3Ze4kz"+
						"Ajf/ALuOpy+38y7f38/v63k9cVFzmO0ZLOLIjVJ2UySI6MA+30MACR59j587+xHrTV/jfafiw3xX"+
						"5bKV42SK7TxMqtt4b/3esP2/nXP/AN7+th6H30r6NhqVqdb6geMZs0lslFda+cvplwjxXtiaV4q1"+
						"MtLel99m/QP23+39f6n9txHLJHWC0IQZLT7c9vG249v5eP8Agef5+jySQ4Ol8wFMuWsgrEvvx9vf"+
						"+/8AydvsPS+vj8LjWeY7335N9R32/cn+m/8Ac+PVvUcv+h6xAicC+oqEOJu42KORZpJKpeTc7fVz"+
						"99vOw222H8vWeh/PmzGsN20zNPI3kt5b2+/9gP6es9RriTPKOi3O0R+lpR0e2GrM6R8A3ltgPb1/"+
						"b+lsdehlftbpuGLbAbn0X2c3TsSmE8FT9idy37+uBMnRWNo0QycRsFBHv6MIW7+I3xVQhF0AmI0c"+
						"tO0JFrKpLbkt9x/b08uidJ36xzSPFF8omOqJIZJu1EqlrYKuw8gENvuP0j6vOy+l1ayJuGNIj2Hj"+
						"232+x/8Avf07vhsinyHUnJ3oYL0l9GhjRq8IklVUVZNlB8N5cngfvvvvuB6Emm91X+H3SrEgfMwQ"+
						"dF7STWyCnAE/IQx9D0e7V612K8dowmLIOycYinAmzyDSqeU6kEcgPqAP0+Sdh/NwuOj2Lir1/wAi"+
						"OxVimkFdoEic7blnPmLfiQQu+/1EbAeu3iLJ/wAG9U7sjYmsXSeZJO1vyHdkB8KdoT9e4BA+59n3"+
						"9cmWsPU6ZYTjPbWcZuhExFn85VBf2jYndQVB5L7bKo8SN6wu8uYI/R9OQ3RqNCOPnAt1FkSbUXSv"+
						"nXxttmu2JyUtNE3kJsrOvh1OzkcTv9Mp9yPS/wBSWTZ6saNxNRs3HyxEjyLRsq8Uil7BA3lG8P6d"+
						"vO24UMfMhJLepMdSfMdFZCt2OKe5e4vYorZiLCKJtkVCArbhOXLYb9niQC26dz8lS11m0XFNZxFy"+
						"NcFZsSLaaSqY9pbAPJAD3TsFLONx5VNj2vPTKU9cD+c/tzjpZV1Z/T6wK3LTz/EHKskoS6mFdoOW"+
						"N+asqpr1mATbwg2O67D9O253Qj0D47K1KvXLqDZ+ZwTTGgQ7Xp33X8muTwgUbb8iQC+/Jucm+z+p"+
						"vA1r1n4gcl+FwZGzWODmmb5C5FHWkDVKhPNpPqJ8fUPARhIg+xItpK8Yetmr3iyV/EZBsIsgjGJN"+
						"qzJypVzsshBVd048Qu/GPtg/Up9eUKX3Vt5OeQ3Ry8D75OyAjQtu5lesnUtLGLy9+vLRspyGRWpU"+
						"AG7fUwfmeO/t4Un6SCFUen9a+Zxunq0uZGVxUk+i8FbojP1u8bad9iDRsQkivGQXKpJs4DSRkLxX"+
						"1U/RcuLtdf8AV8qL0/s2BQcvNlLLxCMCJOfKAAcuJPEbk8mXn/vJ9O7RGpsde01qTCacq6n04INI"+
						"16eQFBhkKmQnjnDPZsI4PyqOePIoRwmLMdgzekgkfZkKl2uGXrCbjwDxSTfKUPyQmPV74LN01xtp"+
						"tQy144b90IVR6oddsgp4sNygUk7HeJj7sBL6VljqV8ddWaVbv4Zjr9Z7JNC6p+c4k02H5U4GwIL7"+
						"7fT5Ji2IRj7FT8duW8a2Ip6RGp6QW1hKf4ppv8yoSY/kd+9yO7EqntvLGfKL6KqEjyaTMvE0aMun"+
						"ZiZqpbJ07XYyStykjb66I3YDx43dG32kYevnFTB7/Mc5R80mRnDUz9o5JqVMcVyaZS8lozVmiun6"+
						"nK9/j+Wx2B2KbAAFNtlX1qkfx7tOLiesGgsuIyxl0/irJbfYMy28jF7/ANNv6D1tZTI9eWrNWqTW"+
						"6IzuSirvWsCzQLGFT+RIfrIIbzz914beQ3rWv/6gfD2Y26YZ+xGsUhwRjQA+whyKEj+3zPrWmg9z"+
						"Wqd1AwUr6UmM6aW2tWsW15hH1ERrUW6CYmh/iHMhzfkbaGE7jgux2AH2J/n7D+ZPoAlx/GCTUOZf"+
						"tsTzRCP0rudvB++/sP7ncn00lrjNT2NXahkSth4TvBC/lQu42J8eQdx/+5iPYbAhzU59b3zkbKyV"+
						"NMwyFI499mnbf+X38+T9gdgd929Xwquis6sJPUGPu5mqctYhaGh3xFCoOx9mJ2/f28n9/A9thnoz"+
						"1Xlnzdt8Np6s0uPgYPzhj5cyF4jjsCAo8+fv428e+emq1JnaYXbbBF0fo65bFpcEk8MpaflyJ323"+
						"J9fOIoWYGJsMSWA8sD5HrkwxyyXjVuQNHUG3HkPJ9NSxh4rFaOQMICQPI+/9fRtfd6v3DjFCQ1rW"+
						"iBfH4KnZLOrBWY77L7/39O/4fq8FXVGpRItGWuLRJF67JXgkIrVx+pByRtx4kU7huK+w39AlajRx"+
						"tZ5UYGYKeRJ9/TA+Hy5E97U9yKzcSdDbmmFemZzGnYdQZAw4OpCeSp+hR9WxG5AGn2kH+hyvmtPn"+
						"BY0TtJ/qczghUSGnbGUl6PdQbMQyuxoRsXqPFusbNGAG28yqS/0nyVYx7ngpPr71K9afpppilPLR"+
						"WRtQ0hDC2PkhWY9x/Mcq77Hc/wDbB8kpIPCDeGwk9L/J7Vhkp4MymnAsXesMk8oJiUyRkKO0Svhk"+
						"bcqh++wHrvaxa1/lzplbUuWanNqBUnlMcRrylYySHgTYIQp91IBViB59sWqUSZDtJHgRhIfONKg8"+
						"eEB2u445s90jNJZZLHzV3nNjMlxlCCHZVmWTbj5YhSB9SszNxKLsuL9xYernTphkM5Tm/wAL25YY"+
						"7GNF1XUWbYU15AfpXcvxDHlz+YPkFCCPqDkqs+a6TUFyWn2tx/NzV4chUkqSQgq3hJR9Ux2Vidt+"+
						"AEqHck+k5cqZ2Xq5oibH1snHE2lbRebB5VJO5IbEnmZZByhcgKpQAqqorbqZW9c0O1xEr9Y/syn5"+
						"+MduAhBnl683RASZDFZPrdPJ29HZh/8AD/JHtM9N4yK1bkpiAHcCjiGfz3D239jsoDoGwt3rxq78"+
						"Lw+vO22H+qHD3oTBPMaUDMzvId15tuzDxwYyRjfYb97PWse/WD5XL5SzUrpp6v2I8tiu5Lsatfg0"+
						"Tr9Pa8gRLsGaMnc8lY+l1oLEi78QuqbtbE4/VdB8VxeGvmmx8EDCjUVwUB2+mSMmYjctMGbyHO3d"+
						"CSD1Mr5nhvPhHD5PvjCQhd9N9QWcb8TWerW9Rafw1xoZuxYu4Nrk0aGtCUkjHsdwyiNdvKNG3niR"+
						"6tThVo5LRtmHG09IZkwdL701mfR0wp28d2Z1RmykLttYIDIjj3kiKOAOA9Vi6M5S/S+KKWOHUvUb"+
						"AfM13Z2xWEXJNJvRi4Bdo34zSKw7ak/SrOCPoHF54nJUNW6Z0jQuzaI6pXqPTrOBcdarvgMjpkxT"+
						"ck7cwHHIOoLyxqAoeM2YmJ2O/wAFAsISNv7ebo5WJOmezjD+oW/xvW2VyOPsPqXLxak0pMlmpF+C"+
						"aiRniCOkVKYLFI+4Cl22USFH/TIdpqEGxXu06leO9qGridUSW4Iz+FZWsUtd+M303EdneNnPZHjg"+
						"Zo9i0Kj0AWLgksZOPJ5BsjPMuibsNDXVf5XL3o+75FPIQ7LFCEbiXKs7wSFtw0LEmeVv2qSnEanM"+
						"OGStl9YUkpav2mp0JpircYcjGeUtjnsObFh3SreBIfTcifP5ub7dkdiU+coZVLIrZyl+WaxjZ8tL"+
						"qGvYctH8pkJTPWj37lYAIEBJBdf/AInPx9a7eDH/AFDFND066GXk8849Q15GO++wbGSr5/8A4t/9"+
						"7+vdDAmeS7pzIZBM/jGkOmL8MWQjW2lyOSEL3FtqD2vpQFY3O5Uldt4vXlJ/1DujQ3wxdJ9TRwgG"+
						"HUGQoclBOxmxssv9v/wv/j1qj2fXQqjPtHFR8UnvwxjPmmdgh5tz8o8FAxpu37lrVk8NdjLXwddV"+
						"5qPBlcDYnx9/fb9gf3PqEyE+U1TDZ0tpWsgxsCma3YTwgiUAbb/ZfB/mx8DwDyye7PkqVPC4wGGP"+
						"5dFsSEEDYjdv7Ek7/c77e2+5FPqrHaD07kdP4uD53PW3CqvHffkpUu+3ufI2T77j2HuSHZhNkU1F"+
						"9sL3JfgmkK8OPtxy3ppOMrKh+s+D9TeRsN9wBv8Av/P1nqSo6G2kN7VYOUydgNIyFztD7eCQfLbb"+
						"fyHsNx59Z6bKSZ3Q6S6oCQj9MAaexVezJNAJJZPfdzyC+pLspNVkhcs6ey8fceuahQy+xhev8vV5"+
						"EliQSf5eib8PrwLCyfqPuNvc+iy4uVhM4paRZZCxtaaMlWaN71yOZgQhXb6R/T0W9JlOF07ra1ar"+
						"z2adepl2ib5w1HEitOhkTzwkIO5Kn9YB5ewHqRnxWUSZ5YIPoJDDbz/6+o/pvHWj0h1VuW7On4ZH"+
						"xOVm/wBVCLKmRnYhS0Z/JlHIsGYDjx47fUG9AP2haVrVQ2gm9Yyw3wV9ENH/APPcV+Tbidkd2sbU"+
						"XSfLUFuWxBNaxin/AN3vYh8SghZXYc0cbbgg/VtJ7jbaF1LCDpnRMM81aa4mYmMgF0m9sKxP1qQA"+
						"6EliOSndlm9vuQZOWNenWUsxtlAr5bGwo9azHDEd5XYRvDy4FfoDBztxWNhuC/Ew2pbNQaY6e/PW"+
						"sTIwzl3atLTaN4mNWPzFMW3bcjwAd07R33Mg2yC4D1sj2x4E58AY0agpA7v2g4eohYa6NuTOdNI8"+
						"lktR4XCNjrbCW5SW3S8TyKezx34DmgDcuJaZdx9LDkkMjQr39faau1zozIhNNTRzfKzvQt1yHBdJ"+
						"k5fmsu6h3DDkpgGx4+HfqtZxqLQxpVtR413xFySWzjbwsR2P9W6BjAW3Q8RxAUEEqkh8k+q3yaww"+
						"Gp9XabWnrTQGoLGP03LSatl4DFLUeIgNEsy7dxYyx+xLdzZuPAcuKCSSyT2j5I5u+cLPoBSsbPXm"+
						"+Oni5dRUOtmXa5Z1rh6drAV54YK8IvxNFJj6sifUQWjMgZpI0B2jQvGduIULzSWRxeR+IHKfndId"+
						"TXY8JWRZM5zx01cRY2tGF4kbvDCAkbN57gFeTfyCSfS0eQTqpevUMBrKrWfTtbuTaXyS2Y3kehAS"+
						"XjJ+l3Ku0h4kRP3F5AHdwjptkMbe+IXsNq7Qv4o2AqyyU9c4swRoExlcFlkBUNFGpQQDie7E8bFg"+
						"QWdSgqH3G88Obo4faP3n6RAX0amTGfFBRmgj62Yyv2yosaQkisyO8mPj5ARvyYTybgkgER8pRugI"+
						"LWa0PkquodC9MsBe1npLL2o9J6tx8OE17ijQrY7e2xRqV3gFtTuU/LKlzHLHNGWXkAKn9JsC2O+J"+
						"/D5OHTXUqxjyFjGQ0Nl+VledCN/l0rySLykJYmZ9m7Z7mzJuWe3Wgbj47T/QzTOR1dBRqNX15VGK"+
						"6k4dX07AXsk8K9lRHzeQqFYmSUw2om2AD7N5RgPs6Z5n9sI0wELmMh5GCLUMuV0lpfN4zKW9U6Qp"+
						"XdF6HvPhs9W/GcfnVS67RMtgM7062xd40Zl35WYSv6dp6lRNLORLSmn03pr/ABrm8dHZwcgzOnEi"+
						"kxfcaOCqTz/SV3kCgdlwdyYhsu8pjJMH08XI09OdTNB4nL9JcJkay4y1+NVMzEt5nFy6rhvkIpG8"+
						"dsFFinV/PGTk7RrWaVvXEuqcTWx2QL9Qa3Z1P08LQ957OL+iKDGTllTntIWIL/mfMxe529eoRaO7"+
						"id/gIQbVMTv5A5vgm0UK9PTcd7HYxqEE2nsLk5zg74kxwlWzIha7C55LPyB8JvtKP2kO9Nv46lGL"+
						"UvwTYOJFSZqOuqUnMeeAfGZOE/8AJkA/v6tJo+QZHT8lekmnM1YraSaFZIg9C1W7V4+XRwFsyqCV"+
						"O+308SPKeq/fxW6Euf8Agk13ZljY16OZw1zyANg12ODfz7eJyN/sD60p7OVqn05aniFc4wC9OFjT"+
						"ax2V+EjGhQc1HWw1Ctj4hNkpl+vgOWzfYkD3Ow8D29j7e8nhaFDTkVrPajn5WmQsHP1Nu3+1R/uY"+
						"/c/1+25PDi48diMAMtZG95pZoFU/r2VtiFH/AKn+Y/kPUG9K/qGxWyOYZ0x36II18bgAeB9wDsPP"+
						"uft9ti68TdjFBQJ24RE5GDUXUOcThK9HFw7iESs3b5eAfKjd2/c7bDbbx989ZltfHGitUwtauWiU"+
						"Ru7L+XsP9qgEfsPP/r9s9Ri1EG0wqUE/hj9Pg6+QCQzD5aso3LMRsP8Az6kMZ1CwISGVXW05P+4/"+
						"b0O4bQmnPlHjvSNMoXdj3CQT/Tf0T0dHaFUKFWMcRuAT4I/bb0eH6LRRNMj8ooKHXjIzET8nUrCS"+
						"RfQ8YTfZthv/AG9BmmsnrjF9MNcZHSFa9dgfGtBkzVqs6JBL29t+Sht+XlW87sB5P6SUDQGj5kaY"+
						"OnbH5jpz+kbefO3qrtvL5yrl+nOHx+pczh6eQqX2v46ukQ/F0SvX2DyMxEBjZw4A25HYEr7Pln2j"+
						"1Mt0KjoamJqOzDce+6DfocacdpD2tK5PnD8ymSrz9MhSkp6eGWGVrTIsss0V9I1LsXVgnGSNXUEp"+
						"+rmqgrs3MJrJdTs3lMla0QEmysmn54bBorJJOJRcreVdewBWH5JflykV9owBEVIlh7enns9QcZrq"+
						"7RxdWqcVDjEuHIwtWnmWeew0KViCTKiiMpKH4fmBdj/tL/8ABlrTGaodRcles0cDqKTv15I8RVMN"+
						"cU+VYmxKm81pebAIJdmSRhxJRtxk3XJeCx/qGeV4vPf2uBjQyWvuyCZHVsznI+mUV90pZzmpOpet"+
						"HtYXUTQULdylQswCOlNJB8lj5WaFUQCxAZJpkaaTk6sJEDcYkVK/dBs7Ncv6vpyZ3RtTGG7nZzBL"+
						"j5pKcjJYKqzSOviwnFwFGwZjOV32JDe6S42G91B6lzHRkV25PlMvMsc2WEeRycYx+NU2K5EadqqO"+
						"JjZObEujnkdwkaD+GLUbxVNW2TqWXTtYT6iBsz4EPhuQuzKoG0aH5wmNk5FvqaOy35vhgyqt2QYU"+
						"cz5J7vGJJ9P4/wBI4w0Ok2HVupty5HpizmrMmnIp1t6OzXamjM1OJzDLGGABJ+mdid0ki28lmIFu"+
						"ldubIfETj6WQ1xSwVK5hKtp6+t8TvRkIxMJ76z/SojTZvlEJ3aJ1H+w7SfQ6u+r+qE+TXR+H1HIN"+
						"J1qnHR2X+RyZEePihMc31gKhMP8AqW7qF5BG/A8iscd0J1NlMB8QmCoT6/yWmkbCU5krdQcQZsEP"+
						"/dUbiVbA2Covl6ad1N1+jZuPGJzV6pBiWCjw5v8A5+fbSdcHsiEr0jxc7/FnpHUR01qqSvNwilva"+
						"Dy3Zykf+hhBiSAOu8hJHfYjwJHO6+7Xo6caoq4CfotQs9RMz0+FfUGvMeU6nYY29K1O68yGGKcFO"+
						"TSbLDZ/PPCcmTiQfq8+OlGAeL4jNC6sTS8+SqsYi2e0PmGr6grKsUUTRxQhgTMC68/BLLY9xx8XY"+
						"6S6vxundc9KJIOqmuunkdLqHrWCWv1GxPzemsbHNBah3aY8C80pdYLStYO0sxm2BDM0lQUksJO30"+
						"3ecQ1YMpBA2T8DsiYx+lbEfSyK1HoPXunorfRC9O1zR2YSxWzhgtKj2MnGwHZiBZUsBQ/FuMqkbF"+
						"lPrduxrDqJms4MlpHqxmTrTSRXM4tn07qO7LPWSMRQQtsI15hY3blGFnCv4D7hNaOxNsdOdKWf8A"+
						"LnUOEF7pRq2BMnoLOkWM01ewimbJ1vy94Iwe3PHvKJYpo5B5j+htx6po6xt6qvXdTdO+q9xh05ka"+
						"tqOi2A1DOwmWCSjRYqu6qrIk86LsYZI5g3jduFNySBzd/ORhkl0pJOH8gc290HGmZW/DqmFyeb+X"+
						"MeJ1JSqVtVVBVlpGPIc2ggnReNm079wg8mCSRzRncOuwL/EPrVs58CPWusiBZ5Mdh7o5AM28eUpS"+
						"nwfc7KfP7+fTXxWWq6Uy17AZixq3p3BTy+vsQamoaf4piqRlkSRadezGSXm5LGskrM3GQJKPDsXU"+
						"fxVxx5n4Oer1adBME0PLa34lo4+zXEu42+wMfj+3rQfs5JJp9IbOAQfkf52QH9NygaG0r9Y+YHpG"+
						"gbhNN4s6i1LdzLQnHY/LXKpikcdpe2zMWdj4KjxsPY+58egnM3shrnNSYHTMJ+TaaRzNISgkU7/U"+
						"T7qnEe36jt7fb0S6p01ls91C6m42vdkhwUWo7Us7HbdpHl8EKPdtv38L5P8AIx+R1Rg+nHbq4+rD"+
						"ey0TOy1y+wBKEBpWHn7g7e52HsPPowU0yWoCy0+cDWhjWZQcwD4QGZPT2I0lcuVMvaiZkkEazSbJ"+
						"3PG/gE+B/wDUb7+s9f1NFal1tdt5zVuQnxlqQ7iPsjuKCAQOBIEa7H299/J/c56SbdUEgBML6sfo"+
						"gxZzVEEc1dtTtLEG2TgwLOP3LD29F2E1ysDLDkLlqawngjubg/19Lazi6Wn567U6UogK7swcHcn3"+
						"HkE+uOtUrmea3FPJSdm8DfdQNvt+3rb9IqllxNos3CM7NVg4hV9u+LCy6/LVmTFzy7uhDAjcnx/t"+
						"/n66xhrYy6mHtLlshYehG0MT2ba1mYXqDNzlTirShOSxpJ44tIVPJASnR8yI5mQ0pVCFmbnxJ2G+"+
						"x+y/8j+w9G1uOta6o0bcVepbrwaflSC/ZlRKUTtkKO0Cwdks1hjGe2Qy+YyOD8gUwL7YdFTRlVe0"+
						"j4lK8pcyjWHs5ul77U4rAJ9YKG15qfIZiz0vj1FjcNoes1LLw1YWBvresQX4nZwJlkgjMdaNY3Kg"+
						"FxKObEcQpen2Tv3eonVi6MzqY4+hlY6xl7ipXxZ/Dse3bSJpj8yshmU7lBtJMDxPbEnrv4uy8/WH"+
						"qTizfx2Pysi4aIdiu6yT7R5JpFuSdrikR5bxcDvyaRTx5BXgumNe/ltedU7Nh9V5LH1MnZirSvYZ"+
						"fwpRjcOTHSiZyskDtKxLBQBNNOdiV3OImHdd5tSj8av2c3iNOISnUUEi5PjblCf6U5HFUcl1NTI6"+
						"d0xdmsZbO2BjJL4N+2RBUjFuOYPvGihAHhPncncJ4Vll8J+uv8Oaa1dm4dZ6p07b7epaYybYpZsM"+
						"N8nfjev2gA3zW8SxI/ncw25CW5hmJej1rGxwdcsLHS0jmu5ltS5ZtOrbZZJHVwpyZsmVuJQceVYL"+
						"4acjhFx2dSfCiauJ0RrTVN3UWq9Mz2Y9TImdeJZcTbf8XyUJqfLc9xY3hEKScf8A8rZk8d3eTuqk"+
						"J1GDtNvcMf7o6palzXq9kc8iHR0GoRZXqGIRhNEdTZf8H0OTaTyXyOeVnxycqsz8xxjgbevZPdj5"+
						"tDXPBvZBTpLnrtH4i9MUbetdS6cibB0pkr6/pd7Su34TE/f+YI/KjJUvTTuReF4cW24xQ/wo6gua"+
						"q1xIuY0RFq0xYOxj1GFlFbU5jrPNBErS7qFp9tV5/mJyhakSHK7j66WZW3L8QWiMPV1PqNrL4SpL"+
						"Bg+p2Mkk0lYH4VE7TNOUYxQDttLUAZARDw3+yStXtEFky+LiOb4b0hwK1hP4fSETpPGTt1u6aZZN"+
						"E4qzBCHjg1LovLdrO1ws8JMiQee9bj74CqoYtFaXx+Xs16ej2p6+j85obIQdY9WaAzcPVfPhqHU/"+
						"TvcxFOF4Jo/nLdkogad+61e1GbBIksGTiNt2o/jdF3aXWjpF1Ezui8VjsAUksnW2ncsBYwqQZJK7"+
						"2Upnb5m1Az+I1SyXrXY3Mf5TerxdPteVqsWjNPY/4gKAtV+vV7JVdMdTNPBKYjlozRDO5G0Y4nAc"+
						"SNBPXLKAZo5OCHbnK0EHqJTx9NoiFrX3VBUrxzhznHZ0zhg+l+keWbo/eMeW0vryrFnOnOd45LOm"+
						"vYKtJYh5RjtVg5Qpym79aeM7EpwEhf1tQzGl7dL/ADL0dq+aTp/oiQ0db4QY7IRCvaY/h+Ml4R95"+
						"IRISZwkneqWv+43bLeuvobQ7xf5K5Ox0b1HKt+91Dxqal6T5zfK6mkgeb6IK3ONlipbOqgs/eqED"+
						"Yle36ibuqEyHTZKcvVXTmYT/ACRwtaanqrAmvNX+VvtIMfinIi7xQO0sE353OKRoyx4hgg42UpNm"+
						"HAbuPEpNPJKhLm3vyzHpajSFHI6J128cv+POkzQa81BRk+VkGfwmMinxPPsxJ5aSVgy83Cgmu/Mk"+
						"GPdQnrNaGU+FvqzgK0MViWfQWbqSFHBVf9BOh+r+RHv/AOnt6IqUU/Trqfkw+kdUdJrNHqLU2m0P"+
						"khl8Rj+/hQ4hp13H5srLIJkfsf8AYsTQ+6qvotxHTyXV3QW82OSK9+I4bJ0yrgoAvKeHbb3+38vR"+
						"19nV9DdcvIXZNBPyUkbPIegw0ysKcq1tQ7Uvmknm2PzheoGe1Ji+q/U7SulY/mpr92vKJETZot4U"+
						"fkpOwQEyAcm8Aefcgjs4HSOH0fBY1Dqa3WmyMMqNJbkb8qsSykleXktuf1H6ifYAk+gH4os7ntC/"+
						"EPqO1pXJGgk9bGWJCU+idPlIDxKn3XkhG3v7jf39V31L1SyuqMjVl1HZsZCkkoZq9YiFIx7MUBDA"+
						"Nt4DNyPtuTtt6MNdLCKW6PzHzgTVEkrobRPZT5CLCag6uZzLZCenoGhY7Ct3Hm+U700wAC78CCEX"+
						"x7kbnx+nyDnrp6c6wdKcfWephq2S01RQBeM9dnksN79xmjLbnzt5PjbxsPHrPUGaSo2zicCZR+iF"+
						"LQjsRGNMXOzk+/fHHx9//wDPTC010m1LqHGvk0hq46oFJRrcroJtj54+CdvG25AHj39Xt+Df4RNF"+
						"9YtPHqfrnIXJ8VBdlox4eOEKJZozBIszykkOhDSI0ZTY8t+Q229cvxsfBnn8NQn178Mz6l0zcrSz"+
						"ST6dwNqzCLkk0kakwpGyoiIOTsv2PlRv49aM0uaa3qsCqLUyQpxCpKKgSBK8ATtM8bsoD/QzR+il"+
						"SpFOMkKE0gXnInKz+YorldA38bgsvcvXcZTda3fhNedpCU4Sclc7dtVfePbju3EP9SllKwNSdsh1"+
						"gDzHEDL2dOvSFyxKvcmEl1W+WWDucouXZBSYKQGVdy23EVE1jf6wdI8hncHqC+cBmqcDS2cRmYq2"+
						"OhqntmVe8lYRyFSrx+ZiV4JG/g9wmzPSLV+U19d0br/bTmPymb01TknGMyb5Pv8AzMVuX5dLBMSy"+
						"Vm4sEtfLx8uO4Zdivr89NKvTGsa5pFHpNZOaylOGQE5JEhYBO63I98a+0Y1FRKC063RBIBImcSbb"+
						"Sf8AqCLRFDHTdWOodqzZr2chjZcUskS1AsWI/wBLZUG9O0INiN1k/KUSScH7e4j5nmNdIU3sdScv"+
						"JX1g80efzIisTEnI11/DsQD8ivDzRY77kkbTNP7+inQ9s1uoHU6zVnF805K6bpX2hxbLSrsUsbHe"+
						"4p73JP18JXQ8V25AH6P/AClKr1Mf8GzuPkbPZ+WOZifxlgauPQGsO0CMewjHI8v18vqf2QLUFY1m"+
						"d6j9G7yHfBbUgDXliBxivfRKzHmcJ1409OumdRFLWqcz/huvOUTaO5aUZV7PcYdyLkA1bb/uW5AE"+
						"i7ZUhHwjZx6vTXVeqKufzelkkxWpKLaitxibFXeOYy0PyaVe6pFj8gwJIVbcVLMntMGkKejlwZrQ"+
						"/XrB1K+F1Jchj1bl7GlqcnCCoYstlkOXa2XYPJD3F7lcE7zXpU4x9goqq+EHNfh/SDVOpYL+V0lb"+
						"m0/qSrJqfNy8sBkQM7nozWird5At0dla8TFfIpX5dj3uc0tVrc0Mq2nmf90IvKCVLH5RwhpdGL+E"+
						"w2VoZ/NW8DlsKmk8ZFJa0XI0OqopDVlT5U+5WONg0Vj8yMOErOEbb6YbpZrOCT4gOnOiMbqvUGt8"+
						"1ZxFZ/8ALzXWMBwOSZcVFIZPmHgYx1oggnrAKwaOvx3HNdhv4eb+M6h2MflNX6FwNLDUsMuA+b0J"+
						"XmfWRkoy5GgrGDtzNtZ+UaSch4kaNqTheJCxQ/TrK4rWHXLpfoCjrbLdXtTZPG1UbpVmKAxh1BNF"+
						"j4GKw5GZYlWCEJ3oiBMGjrOnLaTdpSr0WM63aPmN58Iavqc6xREpag/nLZK/GFg+WxeC+ILo7qW5"+
						"0wGgs1Siez/mRickBj8KYs0y/i0tEtEtlaf1wPDJ3u5WMLdtQvNL+dNOoz6p09hcJhPiJ6K9WKGQ"+
						"+Iurl62l9X4BKSZxpqxg/wAQWSCJFoSMXrywiDipRHDDfi3m7k6VHSPXvpFmrvRXXmheoVO0s0Wp"+
						"Uz5m01j7UOfkhiuZJI5FC0axQ0ZkYRhooln8kKVsn8NOt8ta+GTR8+trPwzai0mnXrE3r2B1NdEG"+
						"Uu2Je3CVeeUvCmGmUCKeWRGClHdjIN1E1QBqsTTO/gduzKK9XCp6qVZZeo498W40NpnHYHXXTa1k"+
						"uh/VrRlyLqXrzDWtQdIM33spkXhW1/o8bVUq8SVACA3bXu1I3BVh+V6pbr34xBp7p7ozR+g9WzdW"+
						"JJum8OgbUF7SMUkGmopr/eMNSdGRm7PBZFtOsnDnwHPfmlButPxd0NSRajq9F8HiejuLlzefysMm"+
						"O1Ze84y6xggoRKTHXhghqO9cyqiPNFKyPxQ9s03kyGQx1l7EWE1NWQ57GIv4NY7szRsY+UrnyqRA"+
						"jaT6/CIT45DkyeUQCEmU/wC3d5GEW0pnrHxwvO3PPwsi/fTn4kupmjvk00T1M08+Ujjy2ToQZWKX"+
						"Hol2mXaOYdn8pY4eRV5Ds3Biw23YHYq+GHrPNf6KdOBnpsRFkbFVp7LwTRywtJNK8riJ1JR0/MPF"+
						"lYhl2O5339alWG1/Fk7tHEWtYaOzbSrqjGvR1XiuQstDjwTAsnFykSggyHkgaJjtvtxOwL8JUq53"+
						"4fek1+Olp2vDHiqSCPETNYx0G1aElK8vJu7EN9kbmxKhfqPo1+zTREUjpMWHri0s9+unYMjzZA10"+
						"00hbVSpcQbnE/Sr1jQ9/iNac/wAK/EEKMc6yrNgqcpKty9pJ4ti3s3/b+3ge3uPVCAoBBk3A9emf"+
						"8VypNT+JqJH/AERYmSmgK8SoiyNwbcfYeCPb+p8k+vMgnfyfRg6StFFPdSrAmBZ0YcCqvZUOyI5W"+
						"lJAVQFUe3rPXF6z1BxPTj9yn4DYYj8PNC/AipXtZ/NvHsuw4R35YFIH9Ih6svmcPHKbU7Oyudydg"+
						"Nz7+N9if+CPXiB0H/i5/Ap8PnTPFdB+o/WWDSfVXDWbqXKtnD3ZIIZrNuS3GTJHGVde3ZiclGI2L"+
						"DcMrhfQ7pP8AxFPgW+IbVkfTro58VfRPXPUGxYs06mn4c5FDlLs0KsZFiqzFJptlR2BjVgyo7Luq"+
						"sRJV8w68+t9STJSiZy2wzq9CUsIQDcAPARUj+Ll0E0NP8LvV/wCIKE6b0zrvTeCsStbyER/D8p3m"+
						"r1uWR4AySxxxqADsxCFvpJCFfGD4W8uuO6bdHZ8zlumkWnLGj9Iie3g4Wo4y7JPirxUK0qxrVpOx"+
						"U1lKxv8AV2yFLCP17zfxgdSNhP4cXxWTC7g8MY8CCL+ZqNZxtUrPExNqNIpe5AOP5icJCUDARyH6"+
						"G1WviR1U+jP4euf1O/UjVvTGzDprQkCar6dYRZJ6YeEQqywf6TjFL3wkKiSHtrNtwXftNmvSslDd"+
						"JogQn8SibsZpGR47jBq0ZFRbpBJsAHkY9FunuRy1XqL1CztjUkNnA/MW69d7PGKtp8x4/G8oOHc5"+
						"WubOJASo4tJ7HgG9QHSGm9fG9TLK1reESTLajuPJsZc3KjdpeUJEQ4Y1hCGduW2/aPJ/9lZfilzu"+
						"oqulaeQ09husWqMxW18mPWxonP2MXdxMZkw3cjZoRIZaiRtJLNyjRHjZ4yybqzDeqOrOuekOFp29"+
						"ALgMNNqHXMmnb51NNXrZOxirl+ZbJpmV4Ca8kcIZEjMsrOawCTgMUA1FpwkzrGUtbZgnaPnITzMG"+
						"Q0fV1wch5nZxjk6G5C3kNHfEBoJ8JV1XFBi9aapGmcdYEFfGvHm8uRnGtPKTMyG27tVHIGa9KnGP"+
						"siNEl8JFvIwaDz1k5GGpJNpPVFwZPVE7x6csomfy8XbpxLtxvggxxEhOTRZFxy7vcmUGpPiMxnSb"+
						"SlvTOr5tL3KeZymX0vR0vJkblYY9r+ezUkOQFqokxnl7kqyfLTcYOV4ozwmFIwA9LfiF6f8ARLQk"+
						"Wf1RiMxrCnaxOW0vX/GYFsabxN65ncsYp60RAb5hGKKBGOYmlyEpV1Mk5uNUo1ksqNsybfnjbP5m"+
						"IWmrBK0zuSOEXh6B6tx2O6m6L1dmqHT3oRRbTAxc2tNNY757PNJVS5j23r9mXjLfeqZZ2QHyKreN"+
						"+EfV6fdW/wAd6+9A+md3qnkuselzSWrlunWBxK4zNvKmPiZaNa3FPA80cR4vzZ/KRWj7bsy9+G64"+
						"OgnXTS2rNT5vSfw/27Olqubk6lvC2cSrHm8e+ThjNDwztLFbjhY8GSFq0Y5bBd+fSusb9r4lvh7x"+
						"2puquEOkcXXWqbGhar/46xyNjqaJSrdgTSNIjFUXwu4kuniC24kaIQQ2D2j5jafIQmpWqSQfgHDd"+
						"xG+K89ZKGNzvXPpL04wGk+tOms7czKVKuGt2JZ9O2JJc/cqRw2m2n2hXjHUnkjCgyQzHlIW3elHU"+
						"JM1ojqLqHpxl8/gp8vjb1uq0NCFpa1mQBA7JIVDKgHH6SB5ZyF8t6s71o1TgF+IrRWk73UHrHdwt"+
						"3MxU6+htQ12gtaphfP3v9BJNKEWF7CvtM5fcW7FhyVKNxo9rLOyY/V+XhiwmH0hSi7kK4yW0kqxH"+
						"tKdg4Uj9yW9hzfwxG6TtHbV9lUALAR85HZLxit05SesQqdstmzbzlH8uXbFG1bsyVNNXtRwQcn+T"+
						"kSOUBZQx4MzhhGH2KkuvFlU8iSCI+fOS1WzuQu4nW2nKKWMTJFdpzJOtkkAMVDjYIhJWTdz7lhsd"+
						"mYbtvZsVJmi03gcnvhxzSlZVWtShiUgVVBIT9RViG25HZdyxMTfyseDj1fPPZ13o5Y6OHdb26Sqs"+
						"gZFIjUmKQkFgshcjcDYkcSoZhud3NoyPCEtbI8yizmm9Vw5DVGCxCdRtH5uNNSZ3GSUs/i+UrStj"+
						"U3owSiOTiIw3N25xBo5GX7cDsR/w+tQUsV8LegsXkINF1JIY0iWDTk62cXEghiULWkBcNEOJ23ck"+
						"ex222GtzpzVtSxrrTdZtc6Dzkaa5ai1fK4ppbFYSVdxSqusL7TjclWVk3jJQtuOPr3N/h75LKXPh"+
						"g0YV0lpbFTq1lO1pi/FPh4eMzqBDJ3pGIIXc7FuLc1IUjgDj7L1EC+mDDahIKbWMrZKVkMsu+Bhp"+
						"vfKejzi02lK0+YG3ONVn+MzghiPidycsUEUFWTLZ+KPtghSFyUj+N/P/AMQfy8+Pv68gvXtd/G1N"+
						"09fKi3qkVWePO6iQhbLTbgy1XH6kXj4f22+/rxR9GTp4wW64pCDgqBR0Hd16qZVv+oxnrPWes9VK"+
						"LXGyf8ZuRlv/ABI9UnrSRNKuSFZFdQSzRxRxAfckfR7eqWWNSXqHUDNZClk8hj7dfI3bCPG7KVaL"+
						"m4bcsOLAgfzB29vVqviLkGb+I3qiBIhdta3qv1DfcC40f3I/b+m3qg7WxZyuYv1QUjkp5GUkeRuY"+
						"JT7hf5f08f29XhxMkAbPSKXQRjHt/wBDP4vXxhdT/hd61/BL1j6raH1n0hi0Rev43UGuMRLlr2AW"+
						"nEgirySL3Dcp7sDJFPBZcopRTsSp9Dfi21OdIfBVZvP1R1905leDSFeTUehceGuV3aSlEA8Pcrss"+
						"cvfEaKZV4LMDx8GN9Z34U8jBjsx1vzE2pdL6PrxdO8mWyObxv4jjqnK9QjD2K5imEqfV+kxSAkjd"+
						"dtyNhf4w9Uaw0fowfKdRdd9O9H76WqTZzR8Ci7VsyWaUKxSoO2JIZyFi2MqmESKQHVzGuUdPLAFa"+
						"UFIF4Juv94flMzst/TbbozRQ+fsVIUo4gf8AE7R5jfDO+MvENqzC6Gjh0l1I6qxVOtdWJpdM2/lJ"+
						"NM8ZMZJ2pUMUonqRbGWaRUT6JCC8PlmW3XjRuQuQdMpq2kdNarqxdXsfkZU1Tmnx+RxCHOWt7lQK"+
						"0InhjC84YWZhIPlxxsHihlPjCwWZzFTofd1Da6z9R6cXWXH4sT6Tiq0IcHKJ4ZQbEPammNVDB3JZ"+
						"O5xUKF3jLcnWfWfpqmYm6JZf/BWsup1SHqTUme1ltQWFyOluGUybLkRCziKStE0G8YI+rlASsm7A"+
						"Zwq93VQybpBR8BtTls7zBmpKAVOSE7ubjximnxKTZbGYXp3h49WdONKJ/mTHdn01mYRZlMD53NOJ"+
						"68z9wmeUukiAL+WbKrziMe3qv/VnV2MwvSfQEf8AmdrTTWSs6jgVosfRMuJdXzeY/IRo1GzOAvNi"+
						"6/my2gVfidnP1Yp6f0zg9Hx57S2htSYtdZpek0tlcxDis3SkbOZoJdjttDIHLOUlEfy7na1Cqsdk"+
						"Rq89ZtaY+l0k0FjsF1KbQ4l1LRlj0rkaaZSpAhyubLJDcWsOUpEhYOET6rsqtxMe8ZLqVoLQzK33"+
						"jlPH9R8xtin1i8Qt2Yl7o3YXWjnCG58celcdpnSeOy2U6LdTdB46zprp7eOTw+T+YrytcxNWZbZB"+
						"5R73yPmynbkaImWMhW3BlvjX0hrLN1sxqPK9M+mmrcedIV40SpdVLcqR/IIQ2xMgnAli5lSniZ/q"+
						"2Up6T3xq6t0jnenxt6WzfxA6FwUundJ49clLYllxkd2tWjp5EjebZYpr1awYA0qLGgdQkar2kNPj"+
						"Msaa1ro3U2qovh3tihFpeWvb1DjsmkE7yQyUEM/YZV5SgzFCSspK2033CDaboKSlLJB+NQxzTjZ4"+
						"EbrptHlBS3LLkA99uQPj84bOrtUtY1f0cyFz4gcIuHSlQJiipSHM2Ynv5GYGBwY5u/JzaflyJWa7"+
						"CvnYbUt6g2O9rfLNiK2cxuH3YQJk4hFeUNFG/mSR2ZQ2xb6eRKlTuoUen5meqettdaY6R6+w+I6K"+
						"3bUWnqrxy6kUvlcdZIytho0WRHXkz9xgpBBeOqC24Leqc64z9nNatmtyC7qKORpojkeIgMKdtFC9"+
						"tVA2YqoAACqY1I32Hp9R9YIU2ZX7Mjt84a1gsSbIF427ObI+5YESKyLWm9Q0JThrKNJStT7VV3Ba"+
						"KMKHj5MRupBLHig4EDiuDM0sPDnrWP1nqXSltcBj5omagLApp3UQTdqN1neQ78XDADZv5OvpWx5D"+
						"EY27haqUNQaaLYS6kL8kQUIxu7brGwhEmw5DYE/TsTsGJI6uobUuOty4vXs00Emna8lWvlaTyxsT"+
						"KFFyWP6pJGfwhQKTuQfO42croxmDeP7tyhhzjHNu4c8IuJpnUeSfqBhBBqfpHlmr6/wpSvk6qx2s"+
						"cJIG2SF2iUSX2/VAwZ2Klk5f7DsI/wAKzETUfhvjw9jRek9LPTv2gMbp/KC7Rrh7tsh0m703l9ub"+
						"J3DwdmXim3BdaPA2MlPqQTGj0bzzxap0zcQWe1Us1PpcfMuxaImyDu1cEuSOahW2Ketlb+EHiHr9"+
						"JOpOA/wPiNB/LZuSRadXItejmWS3eb5gM0shQyEljHy+kkjivsCb7OriWumdBTO/XF4/0XDdMZ9n"+
						"vGNH0xIK+jdJOWqf/okZcY1vP49GmnxnX+5fWJUhGoLibKQRGZaVGbbce5O5O48f8H14F+tk/wD6"+
						"h7CzY7rdqATxnlFnMSVJblxWbBwtsNv0blCeP9x7n1rYej5pRb1a7eOZB8ICujhc6pQMisf8jGes"+
						"9Z6z0PovUe9/UDUHD4jNZ5OZ5PlZ9a2rW4U+zZBnB8Ab++/v6oPhL7pkWhJrN/obaElhuT8pKD7n"+
						"1aHV961Y6n23mdXkfNyF24Lux75O5O3vv6TWkT8tmoI4AsavUyET7AfUhquCD/Igkf0PqQdrBbRO"+
						"IlERQ2AtsCGz8KN80afxI5eHVGkNEyQdP/OVz2OW/jKQkzuKTexAa84kU78V3hkAkaMlQAWXZM+L"+
						"zOWtL4PHlNf616Wy3tX4HDxZXT9M2FsO1nFKKdhO7Gdpu6YyxbZA6HY8dh4L/CNRx+A6d9adU0cb"+
						"jLOXfSVeOT56tHdgmU5Og+zwTh4n2dVcckPFkVhsVBHtv8cfUDWega3TDJ6N1DkNPXcl1UwGHvNX"+
						"IAtU7EmFWaJgRseYJBO3L9iNh6zhp1V19cUNtKR+A3yt9/alVm+Y2XzN+i9BaoDxUfi29jeOEHXx"+
						"az4qY9CrmSi6jZGRetuGxdbJ6ZneNYJ/xDcYy7CsgVqpELtNM6soWIj6XePki+tuio9RQfD7qhdM"+
						"6y6q0KfVOjcXJPYZcnp8pn8sC8iCGYtj65gO311wY4ogZBuUY/8AjK1BqDHf+y3ncXns3iMnP8SO"+
						"ktLWWpW5K6XMTNZvvLUlSMhJUcwR78wxIBG+zMCifiVt3cxp34DtWZO9fuajtfEVh8DZtvO/OfHD"+
						"N6kArN52MYEMICnwBGoG2w9AOpaG4W2Aky1gvwByAIuwMGCsnUjrVKw1fMfO/LvipfxHrlT056S4"+
						"2riNJHHjWMM7Y6zO0Ofp7ajzUpsWSJIg8W+8saFX5BKQ7bbdz1XDqhn7cnQzQWm4Nf6DkNbNU7Da"+
						"TydFU/Do/wAXzchsidt+9LymUquxIbJRAMvbVS0esdeq/SDo9mZadKfM3+ok1G3ckhV7EteLK2rM"+
						"cfcYFgqy14mABHgMv6WdWq1icHjdU1beIz8U+Rx1ae3cgjaeRe3LFfsyx+VYEqr/AFcSeO/29Emq"+
						"UJabR1nwKJzzzMvAHbfFRrRxSlqA+JIHlkPXdBZ1019j8v0LyNNetWssT1MaxRpw425WM+GSCHI2"+
						"oVUt2XIkSAVe35UK0lpSPbiN9StW9MqXTfV+NzLdUsN1GGLyRXIY/IEU6zGSj2JjEsqljxjtpIuw"+
						"B2rMPKEhM9UK9aOGzUNatYqWJJbU0M8azRySlg5Yq4I/USfbbf0tOsmXyGMGXt0p1inaFeRMasG/"+
						"I38hgQfYe/q21TQkLDSBeVE4DFNl05b5xA0ymlKlrl8IGOE7bTfulFx8LiY850W6PXx0ax/UXngJ"+
						"3jzr5uGrZmgjrZCWWSWFmVmdOKTDdnINJVBPdCka611Z8Jr3R+H1DlcLl8rT0vp6vDLh2dqzR/gF"+
						"IIZzGTGbAiKiZyQpsCcoNmX0iNcWLVHpb8PuoKNu5RzNrTollsQTNG3I2GUleJATceNl2Hv6NOr1"+
						"yTT3VTV+OwENDCY6LOy0oq9SrHDFDCka8VVFUKp8eSACd23J3O76kUBaFm2+fEccAIS+0BbaRK4D"+
						"hzaTA5grhgr4pn1betKtOeWRpWT/AFP1bluUblNo/b8sHbZdm225S6Zx8zbuzY3IdPrJtYFpqta8"+
						"hkjl3mBEsheMyPXPgeWA3G5RiQysfpRQxs2ptU5K/i8TmbOImb5FcjVjuRIBXZuLxzBklUkDdZAw"+
						"PsQR49Lrrjlb56J6h1PVsHEZxM7BJFPjlFLsK9ZGaKNYAqxw8nYiFQI13GyjYbI0XVepSWpSKjKd"+
						"khMzy2iGtMcLLBdwENqjQy0t3JpLoHQOqLEVzTFlY4skKszSRFy8jBZ0ZRAWJhDKA4JG0nsNor+C"+
						"rhlxenPiDpDQGU0GtjU9q/ELNh7K5hGmc/iETMBwjlLk8BuFIIB+w00fhu7nUjTvUt9cWbuo5tsS"+
						"xlnnfuMYrMjR7spDHifbc+3j28etvH+AfEsOc+M+BDJ2W1/l7BVnLASN8iWI3Pjck+B48+3olaJq"+
						"OqidMqG1rTKVkWTl/lEdqWPZ/ilaQXxSejVJcAlNKcp/5g2bM/58kP8AqU4rWO66QV5K7NBflw9z"+
						"uldgpixrwhR587+d9/bgNvv61fPW1V/1Po49d9IRr4T8Mw77f/qKZEE/8Kv/AB61VfWgNLiZV0va"+
						"lP0iAtoyI/pch21/UYz1nrPWehlBBj//2Q==\" hspace=\"5\" vspace=\"5\"/>"+
            				"<p>You just finished your masters degree<br>"+
            				"and after months of job hunting you finally<br>"+
            				"found a job in <i>UBM</i> as a project manager.<br>"+
            				"This is your first job so you got lots of stuff to learn...<br>"+
            				"<br>"+
            				"<B>Good Luck.</B>");
            out.close();
                
        } catch (NullArgumentException e1) {
                e1.printStackTrace();
        } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
        }catch (SerializationException e) {
                e.printStackTrace();
        } 
        catch (IOException e) {
			e.printStackTrace();
		}
	}
}
