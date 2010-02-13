package org.promasi.core.tests;


import java.util.List;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.promasi.communication.Communicator;
import org.promasi.communication.IMessageReceiver;
import org.promasi.core.Event;
import org.promasi.core.ISdObject;
import org.promasi.core.SdModel;
import org.promasi.core.SdSystem;
import org.promasi.core.equations.CalculatedEquation;
import org.promasi.core.equations.ConstantEquation;
import org.promasi.core.equations.ExternalEquation;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.core.sdobjects.FlowSdObject;
import org.promasi.core.sdobjects.OutputSdObject;
import org.promasi.core.sdobjects.StockSdObject;
import org.promasi.core.sdobjects.VariableSdObject;
import org.promasi.utilities.TestUtil;


/**
 * 
 * Tests the {@link SdSystem} class.
 * 
 * @author eddiefullmetal
 */
public class SdSystemTester
{

    /**
     * Setup the logging.
     */
    @Before
    public void setUp ( )
    {
        TestUtil.initializeLogging( );
    }

    /**
     * Test if an {@link SdSystem} with a simple {@link SdModel} is run
     * correctly.
     */
    @Test
    public void testSdSystem ( )
    {
        // Define the names of all SdObjects.
        final String sumDeveloperKey = "sumdeveloper";
        final String maxTeamplayerKey = "maxteamplayer";
        final String workingEmployeesKey = "workingemployees";
        final String qualityKey = "quality";
        final String productivityKey = "productivity";
        final String workToDoKey = "worktodo";
        final String workAccomplishmentKey = "workaccomplishment";

        // Define the values of constant equations and stock initial values.
        final double valueOfSumDeveloper = 20.0;
        final double valueOfMaxTeamplayer = 5.0;
        final double valueOfWorkingEmployees = 4.0;
        final double initialValueOfWorkToDo = 1000.0;

        // Create all SdObjects.
        List<ISdObject> sdObjects = new Vector<ISdObject>( );
        // Create all the SdObjects with constant equations.
        AbstractSdObject sumDeveloper = new VariableSdObject( sumDeveloperKey );
        sumDeveloper.setEquation( new ConstantEquation( valueOfSumDeveloper ) );
        sdObjects.add( sumDeveloper );
        // -----------
        AbstractSdObject maxTeamplayer = new VariableSdObject( maxTeamplayerKey );
        maxTeamplayer.setEquation( new ConstantEquation( valueOfMaxTeamplayer ) );
        sdObjects.add( maxTeamplayer );
        // ----------
        AbstractSdObject workingEmployees = new VariableSdObject( workingEmployeesKey );
        workingEmployees.setEquation( new ConstantEquation( valueOfWorkingEmployees ) );
        sdObjects.add( workingEmployees );
        // Create all the SdObjects with calculated equations.
        AbstractSdObject quality = new VariableSdObject( qualityKey );
        quality.setEquation( new CalculatedEquation( quality, "(" + sumDeveloperKey + "/" + workingEmployeesKey + ") + " + maxTeamplayerKey ) );
        quality.addDependency( sumDeveloper );
        quality.addDependency( workingEmployees );
        quality.addDependency( maxTeamplayer );
        sdObjects.add( quality );
        // -----------
        AbstractSdObject productivity = new VariableSdObject( productivityKey );
        productivity.setEquation( new CalculatedEquation( productivity, maxTeamplayerKey + " + " + sumDeveloperKey ) );
        productivity.addDependency( maxTeamplayer );
        productivity.addDependency( sumDeveloper );
        sdObjects.add( productivity );
        // ------------
        AbstractSdObject workAccomplishment = new FlowSdObject( workAccomplishmentKey );
        workAccomplishment.setEquation( new CalculatedEquation( workAccomplishment, productivityKey + " + " + qualityKey ) );
        workAccomplishment.addDependency( productivity );
        workAccomplishment.addDependency( quality );
        sdObjects.add( workAccomplishment );
        // -------------
        AbstractSdObject workToDo = new StockSdObject( workToDoKey, initialValueOfWorkToDo );
        workToDo.setEquation( new CalculatedEquation( workToDo, "-" + workAccomplishmentKey ) );
        workToDo.addDependency( workAccomplishment );
        sdObjects.add( workToDo );
        // Create and run the first step of the system.
        SdSystem system = new SdSystem( );
        system.initialize( sdObjects );
        system.executeStep( );

        // Define the expected results after the first step.
        final Double expectedValueOfQuality = 10.0;
        final Double expectedValueOfProductivity = 25.0;
        final Double expectedValueOfWorkAccomplishment = 35.0;
        final Double expectedValueOfWorkToDo = 965.0;
        // Check all values for first step.
        Assert.assertEquals( expectedValueOfQuality, quality.getValue( ) );
        Assert.assertEquals( expectedValueOfProductivity, productivity.getValue( ) );
        Assert.assertEquals( expectedValueOfWorkAccomplishment, workAccomplishment.getValue( ) );
        Assert.assertEquals( expectedValueOfWorkToDo, workToDo.getValue( ) );

    }

    /**
     * Test if an {@link SdSystem} with an {@link SdModel} that has only basic
     * {@link AbstractSdObject}s.
     */
    @Test
    public void testComplicatedSdSystem ( )
    {
        // Define the names of all SdObjects.
        final String sumDeveloperKey = "sumdeveloper";
        final String maxTeamplayerKey = "maxteamplayer";
        final String workingEmployeesKey = "workingemployees";
        final String qualityKey = "quality";
        final String productivityKey = "productivity";
        final String workToDoKey = "worktodo";
        final String workAccomplishmentKey = "workaccomplishment";
        final String minimumTimeToPerformATaskKey = "minimumtimetoperformatask";
        final String maximumWorkRateKey = "maximumworkrate";
        final String feasibleWorkRateKey = "feasibleworkrate";
        final String potentialWorkRateKey = "potentialworkrate";
        final String staffKey = "staff";
        final String projectFinishedSwitchKey = "projectfinishedswitch";
        final String workDoneKey = "workdone";
        final String reworkGenerationKey = "reworkgeneration";
        final String timeToDiscoverReworkKey = "timetodiscoverrework";
        final String reworkDiscoveryKey = "reworkdiscovery";
        final String undiscoveredReworkKey = "undiscoveredrework";

        // Define the values of constant equations and stock initial values.
        final double valueOfSumDeveloper = 20.0;
        final double valueOfMaxTeamplayer = 7.0;
        final double valueOfWorkingEmployees = 4.0;
        final double valueOfMinimumTimeToPerformATask = 5.0;
        final double valueOfTimeToDescoverRework = 4.9;
        final double initialValueOfWorkToDo = 1000.0;

        // Create all SdObjects.
        List<ISdObject> sdObjects = new Vector<ISdObject>( );
        // Create all the SdObjects
        AbstractSdObject sumDeveloper = new VariableSdObject( sumDeveloperKey );
        AbstractSdObject maxTeamplayer = new VariableSdObject( maxTeamplayerKey );
        AbstractSdObject workingEmployees = new VariableSdObject( workingEmployeesKey );
        AbstractSdObject minimumTimeToPerformATask = new VariableSdObject( minimumTimeToPerformATaskKey );
        AbstractSdObject quality = new VariableSdObject( qualityKey );
        AbstractSdObject productivity = new VariableSdObject( productivityKey );
        AbstractSdObject maximumWorkRate = new VariableSdObject( maximumWorkRateKey );
        AbstractSdObject projectFinishedSwitch = new VariableSdObject( projectFinishedSwitchKey );
        AbstractSdObject staff = new VariableSdObject( staffKey );
        AbstractSdObject potentialWorkRate = new VariableSdObject( potentialWorkRateKey );
        AbstractSdObject feasibleWorkRate = new VariableSdObject( feasibleWorkRateKey );
        AbstractSdObject workAccomplishment = new FlowSdObject( workAccomplishmentKey );
        AbstractSdObject workDone = new StockSdObject( workDoneKey, -1 * initialValueOfWorkToDo );
        AbstractSdObject workToDo = new StockSdObject( workToDoKey, initialValueOfWorkToDo );
        AbstractSdObject reworkGeneration = new FlowSdObject( reworkGenerationKey );
        AbstractSdObject timeToDiscoverRework = new VariableSdObject( timeToDiscoverReworkKey );
        AbstractSdObject reworkDiscovery = new FlowSdObject( reworkDiscoveryKey );
        AbstractSdObject undiscoveredRework = new StockSdObject( undiscoveredReworkKey );

        sumDeveloper.setEquation( new ConstantEquation( valueOfSumDeveloper ) );
        sdObjects.add( sumDeveloper );
        // -----------
        maxTeamplayer.setEquation( new ConstantEquation( valueOfMaxTeamplayer ) );
        sdObjects.add( maxTeamplayer );
        // ----------
        workingEmployees.setEquation( new ConstantEquation( valueOfWorkingEmployees ) );
        sdObjects.add( workingEmployees );
        // -----------
        minimumTimeToPerformATask.setEquation( new ConstantEquation( valueOfMinimumTimeToPerformATask ) );
        sdObjects.add( minimumTimeToPerformATask );
        // -----------
        quality
                .setEquation( new CalculatedEquation( quality, "((" + sumDeveloperKey + "/" + workingEmployeesKey + ")+" + maxTeamplayerKey + ")/10" ) );
        quality.addDependency( sumDeveloper );
        quality.addDependency( workingEmployees );
        quality.addDependency( maxTeamplayer );
        sdObjects.add( quality );
        // -----------
        productivity.setEquation( new CalculatedEquation( productivity, "(" + sumDeveloperKey + "/" + workingEmployeesKey + ")-(10-"
                + maxTeamplayerKey + ")" ) );
        productivity.addDependency( maxTeamplayer );
        productivity.addDependency( sumDeveloper );
        productivity.addDependency( workingEmployees );
        sdObjects.add( productivity );
        // -------------
        maximumWorkRate.setEquation( new CalculatedEquation( maximumWorkRate, workToDoKey + "/" + minimumTimeToPerformATaskKey ) );
        maximumWorkRate.addDependency( workToDo );
        maximumWorkRate.addDependency( minimumTimeToPerformATask );
        sdObjects.add( maximumWorkRate );
        // -------------
        projectFinishedSwitch.setEquation( new CalculatedEquation( projectFinishedSwitch, "if(" + workDoneKey + ">-0.1, 1, 0)" ) );
        projectFinishedSwitch.addDependency( workDone );
        sdObjects.add( projectFinishedSwitch );
        // -------------
        staff.setEquation( new CalculatedEquation( staff, "if(" + projectFinishedSwitchKey + "==1, 0, " + workingEmployeesKey + ")" ) );
        staff.addDependency( projectFinishedSwitch );
        staff.addDependency( workingEmployees );
        sdObjects.add( staff );
        // -------------
        potentialWorkRate.setEquation( new CalculatedEquation( potentialWorkRate, productivityKey + "*" + staffKey ) );
        potentialWorkRate.addDependency( productivity );
        potentialWorkRate.addDependency( staff );
        sdObjects.add( potentialWorkRate );
        // -------------
        feasibleWorkRate.setEquation( new CalculatedEquation( feasibleWorkRate, "Min(" + maximumWorkRateKey + "," + potentialWorkRateKey + ")" ) );
        feasibleWorkRate.addDependency( maximumWorkRate );
        feasibleWorkRate.addDependency( potentialWorkRate );
        sdObjects.add( feasibleWorkRate );
        // ------------
        workAccomplishment.setEquation( new CalculatedEquation( workAccomplishment, feasibleWorkRateKey + " * " + qualityKey ) );
        workAccomplishment.addDependency( feasibleWorkRate );
        workAccomplishment.addDependency( quality );
        sdObjects.add( workAccomplishment );
        // -------------
        workDone.setEquation( new CalculatedEquation( workDone, workAccomplishmentKey ) );
        workDone.addDependency( workAccomplishment );
        sdObjects.add( workDone );
        // -------------
        workToDo.setEquation( new CalculatedEquation( workToDo, reworkDiscoveryKey + "-" + reworkGenerationKey + "-" + workAccomplishmentKey ) );
        workToDo.addDependency( workAccomplishment );
        workToDo.addDependency( reworkDiscovery );
        workToDo.addDependency( reworkGeneration );
        sdObjects.add( workToDo );
        // ------------
        reworkGeneration.setEquation( new CalculatedEquation( reworkGeneration, feasibleWorkRateKey + "*(1-" + qualityKey + ")" ) );
        reworkGeneration.addDependency( feasibleWorkRate );
        reworkGeneration.addDependency( quality );
        sdObjects.add( reworkGeneration );
        // -----------
        timeToDiscoverRework.setEquation( new ConstantEquation( valueOfTimeToDescoverRework ) );
        sdObjects.add( timeToDiscoverRework );
        // ----------
        reworkDiscovery.setEquation( new CalculatedEquation( reworkDiscovery, undiscoveredReworkKey + "/" + timeToDiscoverReworkKey ) );
        reworkDiscovery.addDependency( undiscoveredRework );
        reworkDiscovery.addDependency( timeToDiscoverRework );
        sdObjects.add( reworkDiscovery );
        // ----------
        undiscoveredRework.setEquation( new CalculatedEquation( undiscoveredRework, reworkGenerationKey + "-" + reworkDiscoveryKey ) );
        undiscoveredRework.addDependency( reworkGeneration );
        undiscoveredRework.addDependency( reworkDiscovery );
        sdObjects.add( undiscoveredRework );
        // Create and run the first step of the system.
        SdSystem system = new SdSystem( );
        system.initialize( sdObjects );

        // Define the expected results for all steps.
        final int numberOfSteps = 2;
        final Double[] expectedValuesOfSumDeveloper = new Double[] { 20.0, 20.0 };
        final Double[] expectedValuesOfMaxTeamplayer = new Double[] { 7.0, 7.0 };
        final Double[] expectedValuesOfWorkingEmployees = new Double[] { 4.0, 4.0 };
        final Double[] expectedValuesOfQuality = new Double[] { 1.2, 1.2 };
        final Double[] expectedValuesOfProductivity = new Double[] { 2.0, 2.0 };
        final Double[] expectedValuesOfWorkToDo = new Double[] { 992.0, 983.6734694 };
        final Double[] expectedValuesOfWorkAccomplishment = new Double[] { 9.6, 9.6 };
        final Double[] expectedValuesOfMinimumTimeToPerformATask = new Double[] { 5.0, 5.0 };
        final Double[] expectedValuesOfMaximumWorkRate = new Double[] { 200.0, 198.4 };
        final Double[] expectedValuesOfFeasibleWorkRate = new Double[] { 8.0, 8.0 };
        final Double[] expectedValuesOfPotentialWorkRate = new Double[] { 8.0, 8.0 };
        final Double[] expectedValuesOfStaff = new Double[] { 4.0, 4.0 };
        final Double[] expectedValuesOfProjectFinishedSwitch = new Double[] { 0.0, 0.0 };
        final Double[] expectedValuesOfWorkDone = new Double[] { -990.4, -980.8 };
        final Double[] expectedValuesOfReworkGeneration = new Double[] { -1.6, -1.6 };
        final Double[] expectedValuesOfTimeToDiscoverRework = new Double[] { 4.9, 4.9 };
        final Double[] expectedValuesOfReworkDiscovery = new Double[] { 0.0, -0.3265306 };
        final Double[] expectedValuesOfUndiscoveredRework = new Double[] { -1.6, -2.8734694000000003 };

        for ( int i = 0; i < numberOfSteps; i++ )
        {
            // Run step
            system.executeStep( );
            // Check values.
            Assert.assertEquals( expectedValuesOfSumDeveloper[i], sumDeveloper.getValue( ) );
            Assert.assertEquals( expectedValuesOfMaxTeamplayer[i], maxTeamplayer.getValue( ) );
            Assert.assertEquals( expectedValuesOfWorkingEmployees[i], workingEmployees.getValue( ) );
            Assert.assertEquals( expectedValuesOfQuality[i], quality.getValue( ) );
            Assert.assertEquals( expectedValuesOfProductivity[i], productivity.getValue( ) );
            Assert.assertEquals( expectedValuesOfWorkToDo[i], workToDo.getValue( ) );
            Assert.assertEquals( expectedValuesOfWorkAccomplishment[i], workAccomplishment.getValue( ) );
            Assert.assertEquals( expectedValuesOfMinimumTimeToPerformATask[i], minimumTimeToPerformATask.getValue( ) );
            Assert.assertEquals( expectedValuesOfMaximumWorkRate[i], maximumWorkRate.getValue( ) );
            Assert.assertEquals( expectedValuesOfFeasibleWorkRate[i], feasibleWorkRate.getValue( ) );
            Assert.assertEquals( expectedValuesOfPotentialWorkRate[i], potentialWorkRate.getValue( ) );
            Assert.assertEquals( expectedValuesOfStaff[i], staff.getValue( ) );
            Assert.assertEquals( expectedValuesOfProjectFinishedSwitch[i], projectFinishedSwitch.getValue( ) );
            Assert.assertEquals( expectedValuesOfWorkDone[i], workDone.getValue( ) );
            Assert.assertEquals( expectedValuesOfReworkGeneration[i], reworkGeneration.getValue( ) );
            Assert.assertEquals( expectedValuesOfTimeToDiscoverRework[i], timeToDiscoverRework.getValue( ) );
            Assert.assertEquals( expectedValuesOfReworkDiscovery[i], reworkDiscovery.getValue( ) );
            Assert.assertEquals( expectedValuesOfUndiscoveredRework[i], undiscoveredRework.getValue( ) );
        }
    }

    /**
     * Tests an {@link SdSystem} with an {@link SdModel} that has all kinds of
     * variables and events.
     */
    @Test
    public void testComplicatedSystemFull ( )
    {
        // Register the IMessageReceiver.
        TestReceiver receiver = new TestReceiver( );
        Communicator.getInstance( ).setMainReceiver( receiver );
        // Define the names of all SdObjects.
        final String sumDeveloperKey = "sumdeveloper";
        final String maxTeamplayerKey = "maxteamplayer";
        final String workingEmployeesKey = "workingemployees";
        final String qualityKey = "quality";
        final String productivityKey = "productivity";
        final String workToDoKey = "worktodo";
        final String workAccomplishmentKey = "workaccomplishment";
        final String minimumTimeToPerformATaskKey = "minimumtimetoperformatask";
        final String maximumWorkRateKey = "maximumworkrate";
        final String feasibleWorkRateKey = "feasibleworkrate";
        final String potentialWorkRateKey = "potentialworkrate";
        final String staffKey = "staff";
        final String projectFinishedSwitchKey = "projectfinishedswitch";
        final String workDoneKey = "workdone";
        final String reworkGenerationKey = "reworkgeneration";
        final String timeToDiscoverReworkKey = "timetodiscoverrework";
        final String reworkDiscoveryKey = "reworkdiscovery";
        final String undiscoveredReworkKey = "undiscoveredrework";

        // Define the values of constant equations and stock initial values.
        final double valueOfMinimumTimeToPerformATask = 5.0;
        final double valueOfTimeToDescoverRework = 4.9;
        final double initialValueOfWorkToDo = 1000.0;

        // Create all SdObjects.
        List<ISdObject> sdObjects = new Vector<ISdObject>( );
        // Create all the SdObjects
        AbstractSdObject sumDeveloper = new VariableSdObject( sumDeveloperKey );
        AbstractSdObject maxTeamplayer = new VariableSdObject( maxTeamplayerKey );
        AbstractSdObject workingEmployees = new VariableSdObject( workingEmployeesKey );
        AbstractSdObject minimumTimeToPerformATask = new VariableSdObject( minimumTimeToPerformATaskKey );
        AbstractSdObject quality = new OutputSdObject( qualityKey );
        AbstractSdObject productivity = new OutputSdObject( productivityKey );
        AbstractSdObject maximumWorkRate = new VariableSdObject( maximumWorkRateKey );
        AbstractSdObject projectFinishedSwitch = new VariableSdObject( projectFinishedSwitchKey );
        AbstractSdObject staff = new VariableSdObject( staffKey );
        AbstractSdObject potentialWorkRate = new VariableSdObject( potentialWorkRateKey );
        AbstractSdObject feasibleWorkRate = new VariableSdObject( feasibleWorkRateKey );
        AbstractSdObject workAccomplishment = new FlowSdObject( workAccomplishmentKey );
        AbstractSdObject workDone = new StockSdObject( workDoneKey, -1 * initialValueOfWorkToDo );
        AbstractSdObject workToDo = new StockSdObject( workToDoKey, initialValueOfWorkToDo );
        AbstractSdObject reworkGeneration = new FlowSdObject( reworkGenerationKey );
        AbstractSdObject timeToDiscoverRework = new VariableSdObject( timeToDiscoverReworkKey );
        AbstractSdObject reworkDiscovery = new FlowSdObject( reworkDiscoveryKey );
        AbstractSdObject undiscoveredRework = new StockSdObject( undiscoveredReworkKey );

        // Register the events.
        Event workToDoEvent = new Event( "worktodo", new CalculatedEquation( workToDo, "if(" + workToDoKey + "<992.0,1,0)" ), workToDo );
        workToDo.addEvent( workToDoEvent );

        // ------------
        sumDeveloper.setEquation( new ExternalEquation( sumDeveloper ) );
        sdObjects.add( sumDeveloper );
        // -----------
        maxTeamplayer.setEquation( new ExternalEquation( maxTeamplayer ) );
        sdObjects.add( maxTeamplayer );
        // ----------
        workingEmployees.setEquation( new ExternalEquation( workingEmployees ) );
        sdObjects.add( workingEmployees );
        // -----------
        minimumTimeToPerformATask.setEquation( new ConstantEquation( valueOfMinimumTimeToPerformATask ) );
        sdObjects.add( minimumTimeToPerformATask );
        // -----------
        quality
                .setEquation( new CalculatedEquation( quality, "((" + sumDeveloperKey + "/" + workingEmployeesKey + ")+" + maxTeamplayerKey + ")/10" ) );
        quality.addDependency( sumDeveloper );
        quality.addDependency( workingEmployees );
        quality.addDependency( maxTeamplayer );
        sdObjects.add( quality );
        // -----------
        productivity.setEquation( new CalculatedEquation( productivity, "(" + sumDeveloperKey + "/" + workingEmployeesKey + ")-(10-"
                + maxTeamplayerKey + ")" ) );
        productivity.addDependency( maxTeamplayer );
        productivity.addDependency( sumDeveloper );
        productivity.addDependency( workingEmployees );
        sdObjects.add( productivity );
        // -------------
        maximumWorkRate.setEquation( new CalculatedEquation( maximumWorkRate, workToDoKey + "/" + minimumTimeToPerformATaskKey ) );
        maximumWorkRate.addDependency( workToDo );
        maximumWorkRate.addDependency( minimumTimeToPerformATask );
        sdObjects.add( maximumWorkRate );
        // -------------
        projectFinishedSwitch.setEquation( new CalculatedEquation( projectFinishedSwitch, "if(" + workDoneKey + ">-0.1, 1, 0)" ) );
        projectFinishedSwitch.addDependency( workDone );
        sdObjects.add( projectFinishedSwitch );
        // -------------
        staff.setEquation( new CalculatedEquation( staff, "if(" + projectFinishedSwitchKey + "==1, 0, " + workingEmployeesKey + ")" ) );
        staff.addDependency( projectFinishedSwitch );
        staff.addDependency( workingEmployees );
        sdObjects.add( staff );
        // -------------
        potentialWorkRate.setEquation( new CalculatedEquation( potentialWorkRate, productivityKey + "*" + staffKey ) );
        potentialWorkRate.addDependency( productivity );
        potentialWorkRate.addDependency( staff );
        sdObjects.add( potentialWorkRate );
        // -------------
        feasibleWorkRate.setEquation( new CalculatedEquation( feasibleWorkRate, "Min(" + maximumWorkRateKey + "," + potentialWorkRateKey + ")" ) );
        feasibleWorkRate.addDependency( maximumWorkRate );
        feasibleWorkRate.addDependency( potentialWorkRate );
        sdObjects.add( feasibleWorkRate );
        // ------------
        workAccomplishment.setEquation( new CalculatedEquation( workAccomplishment, feasibleWorkRateKey + " * " + qualityKey ) );
        workAccomplishment.addDependency( feasibleWorkRate );
        workAccomplishment.addDependency( quality );
        sdObjects.add( workAccomplishment );
        // -------------
        workDone.setEquation( new CalculatedEquation( workDone, workAccomplishmentKey ) );
        workDone.addDependency( workAccomplishment );
        sdObjects.add( workDone );
        // -------------
        workToDo.setEquation( new CalculatedEquation( workToDo, reworkDiscoveryKey + "-" + reworkGenerationKey + "-" + workAccomplishmentKey ) );
        workToDo.addDependency( workAccomplishment );
        workToDo.addDependency( reworkDiscovery );
        workToDo.addDependency( reworkGeneration );
        sdObjects.add( workToDo );
        // ------------
        reworkGeneration.setEquation( new CalculatedEquation( reworkGeneration, feasibleWorkRateKey + "*(1-" + qualityKey + ")" ) );
        reworkGeneration.addDependency( feasibleWorkRate );
        reworkGeneration.addDependency( quality );
        sdObjects.add( reworkGeneration );
        // -----------
        timeToDiscoverRework.setEquation( new ConstantEquation( valueOfTimeToDescoverRework ) );
        sdObjects.add( timeToDiscoverRework );
        // ----------
        reworkDiscovery.setEquation( new CalculatedEquation( reworkDiscovery, undiscoveredReworkKey + "/" + timeToDiscoverReworkKey ) );
        reworkDiscovery.addDependency( undiscoveredRework );
        reworkDiscovery.addDependency( timeToDiscoverRework );
        sdObjects.add( reworkDiscovery );
        // ----------
        undiscoveredRework.setEquation( new CalculatedEquation( undiscoveredRework, reworkGenerationKey + "-" + reworkDiscoveryKey ) );
        undiscoveredRework.addDependency( reworkGeneration );
        undiscoveredRework.addDependency( reworkDiscovery );
        sdObjects.add( undiscoveredRework );
        // Create and run the first step of the system.
        SdSystem system = new SdSystem( );
        system.initialize( sdObjects );

        // Define the expected results for all steps.
        final int numberOfSteps = 2;
        final Double[] expectedValuesOfSumDeveloper = new Double[] { 20.0, 20.0 };
        final Double[] expectedValuesOfMaxTeamplayer = new Double[] { 7.0, 7.0 };
        final Double[] expectedValuesOfWorkingEmployees = new Double[] { 4.0, 4.0 };
        final Double[] expectedValuesOfQuality = new Double[] { 1.2, 1.2 };
        final Double[] expectedValuesOfProductivity = new Double[] { 2.0, 2.0 };
        final Double[] expectedValuesOfWorkToDo = new Double[] { 992.0, 983.6734694 };
        final Double[] expectedValuesOfWorkAccomplishment = new Double[] { 9.6, 9.6 };
        final Double[] expectedValuesOfMinimumTimeToPerformATask = new Double[] { 5.0, 5.0 };
        final Double[] expectedValuesOfMaximumWorkRate = new Double[] { 200.0, 198.4 };
        final Double[] expectedValuesOfFeasibleWorkRate = new Double[] { 8.0, 8.0 };
        final Double[] expectedValuesOfPotentialWorkRate = new Double[] { 8.0, 8.0 };
        final Double[] expectedValuesOfStaff = new Double[] { 4.0, 4.0 };
        final Double[] expectedValuesOfProjectFinishedSwitch = new Double[] { 0.0, 0.0 };
        final Double[] expectedValuesOfWorkDone = new Double[] { -990.4, -980.8 };
        final Double[] expectedValuesOfReworkGeneration = new Double[] { -1.6, -1.6 };
        final Double[] expectedValuesOfTimeToDiscoverRework = new Double[] { 4.9, 4.9 };
        final Double[] expectedValuesOfReworkDiscovery = new Double[] { 0.0, -0.3265306 };
        final Double[] expectedValuesOfUndiscoveredRework = new Double[] { -1.6, -2.8734694000000003 };

        for ( int i = 0; i < numberOfSteps; i++ )
        {
            // Run step
            system.executeStep( );
            // Check values.
            Assert.assertEquals( expectedValuesOfSumDeveloper[i], sumDeveloper.getValue( ) );
            Assert.assertEquals( expectedValuesOfMaxTeamplayer[i], maxTeamplayer.getValue( ) );
            Assert.assertEquals( expectedValuesOfWorkingEmployees[i], workingEmployees.getValue( ) );
            Assert.assertEquals( expectedValuesOfQuality[i], quality.getValue( ) );
            Assert.assertEquals( expectedValuesOfProductivity[i], productivity.getValue( ) );
            Assert.assertEquals( expectedValuesOfWorkToDo[i], workToDo.getValue( ) );
            Assert.assertEquals( expectedValuesOfWorkAccomplishment[i], workAccomplishment.getValue( ) );
            Assert.assertEquals( expectedValuesOfMinimumTimeToPerformATask[i], minimumTimeToPerformATask.getValue( ) );
            Assert.assertEquals( expectedValuesOfMaximumWorkRate[i], maximumWorkRate.getValue( ) );
            Assert.assertEquals( expectedValuesOfFeasibleWorkRate[i], feasibleWorkRate.getValue( ) );
            Assert.assertEquals( expectedValuesOfPotentialWorkRate[i], potentialWorkRate.getValue( ) );
            Assert.assertEquals( expectedValuesOfStaff[i], staff.getValue( ) );
            Assert.assertEquals( expectedValuesOfProjectFinishedSwitch[i], projectFinishedSwitch.getValue( ) );
            Assert.assertEquals( expectedValuesOfWorkDone[i], workDone.getValue( ) );
            Assert.assertEquals( expectedValuesOfReworkGeneration[i], reworkGeneration.getValue( ) );
            Assert.assertEquals( expectedValuesOfTimeToDiscoverRework[i], timeToDiscoverRework.getValue( ) );
            Assert.assertEquals( expectedValuesOfReworkDiscovery[i], reworkDiscovery.getValue( ) );
            Assert.assertEquals( expectedValuesOfUndiscoveredRework[i], undiscoveredRework.getValue( ) );

            // Test if the outputSdObjects have notified the receiver.
            Assert.assertEquals( quality.getValue( ), receiver.getValuesOfQuality( ).get( i ) );
            Assert.assertEquals( productivity.getValue( ), receiver.getValuesOfProductivity( ).get( i ) );
        }

        Assert.assertTrue( receiver.isWorkToDoRaised( ) );
    }

    /**
     * 
     * This receiver is used to test if the
     * {@link SdSystemTester#testComplicatedSystemFull()} notifies correctly the
     * communicator.
     * 
     * @author eddiefullmetal
     * 
     */
    private class TestReceiver
            implements IMessageReceiver
    {

        /**
         * The value of sumdeveloper.
         */
        private final Double _valueOfSumDeveloper = 20.0;

        /**
         * The value of maxteamplayer.
         */
        private final Double _valueOfMaxTeamPlayer = 7.0;

        /**
         * The value of workingemployees.
         */
        private final Double _valueOfWorkingEmployees = 4.0;

        /**
         * Holds all the values of the quality.
         */
        private List<Double> _valuesOfQuality;

        /**
         * Holds all the values of productivity.
         */
        private List<Double> _valuesOfProductivity;

        /**
         * Flag that indicates if the worktodo event was raised.
         */
        private boolean _workToDoRaised;

        /**
         * Initializes the object.
         */
        public TestReceiver( )
        {
            _valuesOfQuality = new Vector<Double>( );
            _valuesOfProductivity = new Vector<Double>( );
            _workToDoRaised = false;
        }

        /**
         * 
         * 
         * @param sdObjectKey
         *            The key of the {@link ISdObject} that raised the event.
         * @param eventName
         *            The name of the event that was raised.
         */
        @Override
        public void eventRaised ( String sdObjectKey, String eventName )
        {
            if ( sdObjectKey.equals( "worktodo" ) && eventName.equals( "worktodo" ) )
            {
                _workToDoRaised = true;
            }
        }

        /**
         * The sumdeveloper,maxteamplayer and the workingemployees have external
         * equations.
         * 
         * @param sdObjectKey
         *            The key of the {@link ISdObject} that requested the value.
         * @return The value of the corresponding {@link ISdObject}.
         */
        @Override
        public Double valueRequested ( String sdObjectKey )
        {
            final String sumDeveloperKey = "sumdeveloper";
            final String maxTeamplayerKey = "maxteamplayer";
            final String workingEmployeesKey = "workingemployees";

            if ( sdObjectKey.equals( sumDeveloperKey ) )
            {
                return _valueOfSumDeveloper;
            }
            else if ( sdObjectKey.equals( maxTeamplayerKey ) )
            {
                return _valueOfMaxTeamPlayer;
            }
            else if ( sdObjectKey.equals( workingEmployeesKey ) )
            {
                return _valueOfWorkingEmployees;
            }
            throw new IllegalArgumentException( "sdObjectKey is invalid." );
        }

        /**
         * The quality and the productivity send their values.
         * 
         * @param sdObjectKey
         *            The key of the {@link ISdObject} that sent the value.
         * @param value
         *            The value of the {@link ISdObject}.
         */
        @Override
        public void valueSent ( String sdObjectKey, Double value )
        {
            final String qualityKey = "quality";
            final String productivityKey = "productivity";

            if ( sdObjectKey.equals( qualityKey ) )
            {
                _valuesOfQuality.add( value );
            }
            else if ( sdObjectKey.equals( productivityKey ) )
            {
                _valuesOfProductivity.add( value );
            }
        }

        /**
         * Gets the {@link #_valuesOfProductivity}.
         * 
         * @return The {@link #_valuesOfProductivity}.
         */
        public List<Double> getValuesOfProductivity ( )
        {
            return _valuesOfProductivity;
        }

        /**
         * Gets the {@link #_valuesOfQuality}.
         * 
         * @return The {@link #_valuesOfQuality}.
         */
        public List<Double> getValuesOfQuality ( )
        {
            return _valuesOfQuality;
        }

        /**
         * Gets the {@link #_workToDoRaised}.
         * 
         * @return The {@link #_workToDoRaised}.
         */
        public boolean isWorkToDoRaised ( )
        {
            return _workToDoRaised;
        }

    }
}
