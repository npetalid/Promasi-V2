package org.promasi.server.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.promasi.game.model.generated.GameModelModel;
import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.EmployeeModel;
import org.promasi.game.model.generated.EmployeeTaskModel;
import org.promasi.game.model.generated.MarketPlaceModel;
import org.promasi.game.multiplayer.IMultiPlayerGame;
import org.promasi.game.multiplayer.IServerGameListener;
import org.promasi.game.multiplayer.MultiPlayerGame;
import org.promasi.game.model.generated.ProjectModel;
import org.promasi.network.tcp.NetworkException;
import org.promasi.protocol.client.IClientListener;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.messages.AssignEmployeeTasksRequest;
import org.promasi.protocol.messages.DischargeEmployeeRequest;
import org.promasi.protocol.messages.EmployeeDischargedRequest;
import org.promasi.protocol.messages.EmployeeHiredRequest;
import org.promasi.protocol.messages.GameFinishedRequest;
import org.promasi.protocol.messages.GameStartedResponse;
import org.promasi.protocol.messages.HireEmployeeRequest;
import org.promasi.protocol.messages.InternalErrorResponse;
import org.promasi.protocol.messages.LeaveGameRequest;
import org.promasi.protocol.messages.LeaveGameResponse;
import org.promasi.protocol.messages.OnExecuteStepRequest;
import org.promasi.protocol.messages.OnTickRequest;
import org.promasi.protocol.messages.ProjectAssignedRequest;
import org.promasi.protocol.messages.ProjectFinishedRequest;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.server.ProMaSiServer;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;

/**
 *
 * @author m1cRo Represent the playing game user state. In this state user is a
 * player of the running ProMaSi game.
 */
public class PlayingGameClientState implements IServerGameListener, IClientListener {

    /**
     * Instance of {@link MultiPlayerGame} which represent the running game.
     */
    private MultiPlayerGame _game;

    /**
     * The client id.
     */
    private String _clientId;

    /**
     * Instance of {@link ProMaSiClient} needed in order to handle ProMaSi
     * system requests and communicate with a client.
     */
    private ProMaSiClient _client;

    /**
     * Instance of {@link ProMaSiServer} which contains the business logic of
     * ProMaSi server.
     */
    private ProMaSiServer _server;

    /**
     * Instance of {@link ILogger} interface implementation, need for logging.
     */
    private ILogger _logger = LoggerFactory.getInstance(ChooseGameClientState.class);

    /**
     * Constructor will initialize the object.
     *
     * @param server instance of {@link ProMaSiServer}
     * @param client instance of {@link ProMaSiClient}
     * @param clientId Client id, this is the identification string given by
     * user in login procedure.
     * @param game Instance of {@link MultiPlayerGame} represent the running
     * game.
     * @throws NetworkException In case of invalid arguments, such as null
     * argument.
     */
    public PlayingGameClientState(ProMaSiServer server, ProMaSiClient client, String clientId, MultiPlayerGame game) throws NetworkException {
        if (game == null) {
            _logger.error("Initialization failed because a wrong argument game == null");
            throw new NetworkException("Wrong argument game==null");
        }

        if (clientId == null) {
            _logger.error("Initialization failed because a wrong argument clientId == null");
            throw new NetworkException("Wrong argument clientId==null");
        }

        if (client == null) {
            _logger.error("Initialization failed because a wrong argument client == null");
            throw new NetworkException("Wrong argument client==null");
        }

        if (server == null) {
            _logger.error("Initialization failed because a wrong argument server == null");
            throw new NetworkException("Wrong argument server==null");
        }

        _client = client;
        _server = server;
        _game = game;
        _game.addListener(this);
        _clientId = clientId;
        _logger.debug("Initialization initializatin complete");
    }

    @Override
    public void onReceive(ProMaSiClient client, String recData) {
        try {
            Object object = new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
            if (object instanceof HireEmployeeRequest) {
                HireEmployeeRequest request = (HireEmployeeRequest) object;
                if (request.getEmployeeId() == null) {
                    client.sendMessage(new WrongProtocolResponse());
                    client.disconnect();
                }

                _game.hireEmployee(_clientId, request.getEmployeeId());
            } else if (object instanceof DischargeEmployeeRequest) {
                DischargeEmployeeRequest request = (DischargeEmployeeRequest) object;
                if (request.getEmployeeId() == null) {
                    client.sendMessage(new WrongProtocolResponse());
                    client.disconnect();
                }

                _game.dischargeEmployee(_clientId, request.getEmployeeId());
            } else if (object instanceof GameStartedResponse) {
            } else if (object instanceof AssignEmployeeTasksRequest) {
                AssignEmployeeTasksRequest request = (AssignEmployeeTasksRequest) object;
                if (request.getEmployeeId() == null || request.getTasks() == null) {
                    client.sendMessage(new WrongProtocolResponse());
                    client.disconnect();
                }

                _game.assignTasks(_clientId, request.getEmployeeId(), request.getTasks());
            } else if (object instanceof LeaveGameRequest) {
                _game.removeListener(this);
                _client.removeListener(this);
                _client.addListener(new ChooseGameClientState(_server, _client, _clientId));
                client.sendMessage(new LeaveGameResponse());
            } else {
                client.sendMessage(new WrongProtocolResponse());
                client.disconnect();
            }
        } catch (NetworkException e) {
            client.sendMessage(new InternalErrorResponse());
            client.disconnect();
        }
    }

    @Override
    public void onTick(String clientId, IMultiPlayerGame game, DateTime dateTime) {
        if (clientId.equals(_clientId)) {
            _client.sendMessage(new OnTickRequest(dateTime.toString()));
        }
    }

    @Override
    public void messageSent(String clientId, IMultiPlayerGame game, String message) {
    }

    @Override
    public void onDisconnect(ProMaSiClient client) {
        _client.removeListener(this);
        _game.leaveGame(_clientId);
    }

    @Override
    public void onConnect(ProMaSiClient client) {
    }

    @Override
    public void onConnectionError(ProMaSiClient client) {
        _client.removeListener(this);
        _game.leaveGame(_clientId);
    }

    @Override
    public void playersListUpdated(IMultiPlayerGame game, List<String> gamePlayers) {
    }

    @Override
    public void gameStarted(String playerId, IMultiPlayerGame game, GameModelModel gameModel, DateTime dateTime) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void projectAssigned(String playerId, IMultiPlayerGame game, CompanyModel company, ProjectModel project, DateTime dateTime) {
        if (playerId.equals(_clientId)) {
            _client.sendMessage(new ProjectAssignedRequest(company, project, dateTime.toString()));
        }
    }

    @Override
    public void projectFinished(String playerId, IMultiPlayerGame game, CompanyModel company, ProjectModel project, DateTime dateTime) {
        if (playerId.equals(_clientId)) {
            _client.sendMessage(new ProjectFinishedRequest(project));
        }
    }

    @Override
    public void employeeHired(String playerId, IMultiPlayerGame game, MarketPlaceModel marketPlace, CompanyModel company, EmployeeModel employee, DateTime dateTime) {
        if (playerId.equals(_clientId)) {
            _client.sendMessage(new EmployeeHiredRequest(marketPlace, company, employee, dateTime.toString()));
        }
    }

    @Override
    public void employeeDischarged(String playerId, IMultiPlayerGame game, MarketPlaceModel marketPlace, CompanyModel company, EmployeeModel employee, DateTime dateTime) {
        if (playerId.equals(_clientId)) {
            _client.sendMessage(new EmployeeDischargedRequest(marketPlace, company, employee, dateTime.toString()));
        }
    }

    @Override
    public void employeeTasksAssigned(String playerId, IMultiPlayerGame game, CompanyModel company, EmployeeModel employee, List<EmployeeTaskModel> employeeTasks, DateTime dateTime) {
    }

    @Override
    public void employeeTaskDetached(String playerId, IMultiPlayerGame game, MarketPlaceModel marketPlace, CompanyModel company, EmployeeModel employee, EmployeeTaskModel employeeTask, DateTime dateTime) {
    }

    @Override
    public void companyIsInsolvent(String playerId, IMultiPlayerGame game, CompanyModel company, DateTime dateTime) {

    }

    @Override
    public void onExecuteStep(String playerId, IMultiPlayerGame game, CompanyModel company, DateTime dateTime) {

    }

    @Override
    public void onExecuteWorkingStep(String playerId, IMultiPlayerGame game, CompanyModel company, ProjectModel assignedProject, DateTime dateTime) {
        if (playerId.equals(_clientId)) {
            _client.sendMessage(new OnExecuteStepRequest(assignedProject, company, dateTime.toString()));
        }
    }

    @Override
    public void gameFinished(Map<String, GameModelModel> gameModels
    ) {
        if (gameModels.containsKey(_clientId)) {
            GameModelModel gameModel = gameModels.get(_clientId);
            Map<String, GameModelModel> models = new TreeMap<>(gameModels);
            models.remove(_clientId);
            GameFinishedRequest request = new GameFinishedRequest(_clientId, gameModel, models);
            _client.sendMessage(request);
        }
    }
}
