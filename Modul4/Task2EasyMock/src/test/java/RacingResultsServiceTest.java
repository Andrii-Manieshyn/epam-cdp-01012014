import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;
import static junitparams.JUnitParamsRunner.$;
import static org.easymock.EasyMock.replay;

import java.util.*;

/**
 * @author Andrii_Manieshyn
 */

@RunWith(JUnitParamsRunner.class)
public class RacingResultsServiceTest {

    public RacingResultsServiceImpl racingResultsService;

    @Before
    public void initMockito(){
        racingResultsService = new RacingResultsServiceImpl();
    }


    public Object [] subscriptionInput(){
        return $(Race.BICYCLE_RACES, Race.BOAT_RACES, Race.CAR_RACES);
    }


    @Test
    @Parameters(method = "subscriptionInput")
    public void whenUserWantToSubscribeForNews_thanRacingResultServiceShouldSubscribeHim(Race race){
        Set<Race> subscriptionSet = new HashSet<Race>();
        Client client = createNiceMock(Client.class);
        expect(client.getSubsription()).andReturn(subscriptionSet).times(2);

        replay(client);

        racingResultsService.subscribeUserForNews(client, race);

        Assert.assertTrue(client.getSubsription().contains(race));
        verify(client);
    }

    @Test
    public void whenUserWantToSubscribeForNews_thanClientReturnNullSet(){
        Client client = createNiceMock(Client.class);
        expect(client.getSubsription()).andReturn(null).times(1);

        replay(client);

        racingResultsService.subscribeUserForNews(client, Race.BICYCLE_RACES);

        verify(client);
    }


    public Object [] unsubscriptionInput(){
        Set<Race> raceSetFirst = new HashSet<Race>();
        raceSetFirst.add(Race.BICYCLE_RACES);
        raceSetFirst.add(Race.BOAT_RACES);

        Set<Race> raceSetFirstExpected = new HashSet<Race>();
        raceSetFirst.add(Race.BOAT_RACES);

        Set<Race> raceSetSecond = new HashSet<Race>();
        Set<Race> raceSetSecondExpected = new HashSet<Race>();

        return $( $(raceSetFirst, raceSetFirstExpected), $(raceSetSecond, raceSetSecondExpected));
    }


    @Test
    public void whenUserWantToUnsubscribeForNews_thanReturnSetIsNull(){
        Client client = createNiceMock(Client.class);
        expect(client.getSubsription()).andReturn(null).times(1);

        replay(client);
        racingResultsService.unsubscribeUserForNews(client, Race.BICYCLE_RACES);

        verify(client);
    }

    @Test
    @Parameters(method = "unsubscriptionInput")
    public void whenUserWantToUnsubscribeForNews_thanRacingResultServiceShouldUnsubscribeHim(Set<Race> raceSet, Set<Race> raceSetExpected){
        Client client = createNiceMock(Client.class);
        expect(client.getSubsription()).andReturn(raceSet).times(2);

        replay(client);
        racingResultsService.unsubscribeUserForNews(client, Race.BICYCLE_RACES);

        Assert.assertTrue(client.getSubsription().containsAll(raceSetExpected));
        verify(client);
    }


    public Object [] sendMessagesToAllSubscribers(){

        // Subscriptions
        Set<Race> subscriptionForBicycleAndCarRaces = new HashSet<Race>();
        subscriptionForBicycleAndCarRaces.add(Race.BICYCLE_RACES);
        subscriptionForBicycleAndCarRaces.add(Race.CAR_RACES);

        Set<Race> subscriptionForBicyclerRaces = new HashSet<Race>();
        subscriptionForBicyclerRaces.add(Race.BICYCLE_RACES);

        Set<Race> subscriptionForBoatRaces = new HashSet<Race>();
        subscriptionForBoatRaces.add(Race.BOAT_RACES);

        // Clients
        Client clientSubscribedForBicycleAndCarRaces = new Client();
        clientSubscribedForBicycleAndCarRaces.setSubsription(subscriptionForBicycleAndCarRaces);

        Client clientSubscribedForBoatRaces = new Client();
        clientSubscribedForBoatRaces.setSubsription(subscriptionForBoatRaces);

        Client clientSubscribedForBicycleRace = new Client();
        clientSubscribedForBicycleRace.setSubsription(subscriptionForBicyclerRaces);

        //Messages
        Message boatMessage = createNiceMock(Message.class);
        expect(boatMessage.getRaceType()).andReturn(Race.BOAT_RACES).anyTimes();

        Message carMessage= createNiceMock(Message.class);
        expect(carMessage.getRaceType()).andReturn(Race.CAR_RACES).anyTimes();

        Message bicycleMessage = createNiceMock(Message.class);
        expect(bicycleMessage.getRaceType()).andReturn(Race.BICYCLE_RACES).anyTimes();


        replay(boatMessage);
        replay(carMessage);
        replay(bicycleMessage);

        // Tests parameters
        List<Client> userSubscribedForBoat = new ArrayList<Client>();
        userSubscribedForBoat.add(clientSubscribedForBicycleAndCarRaces);
        userSubscribedForBoat.add(clientSubscribedForBicycleRace);

        List<Message> messageList= new ArrayList<Message>();
        messageList.add(boatMessage);
        messageList.add(bicycleMessage);
        messageList.add(carMessage);

        List<Client> userListWithDifferentSubscription = new ArrayList<Client>();
        userListWithDifferentSubscription.add(clientSubscribedForBicycleAndCarRaces);
        userListWithDifferentSubscription.add(clientSubscribedForBicycleRace);
        userListWithDifferentSubscription.add(clientSubscribedForBoatRaces);

        List<Message> messagesWithSubscriptionExceptBoatRaces = new ArrayList<Message>();
        messagesWithSubscriptionExceptBoatRaces.add(bicycleMessage);
        messagesWithSubscriptionExceptBoatRaces.add(carMessage);

        return $( $(userSubscribedForBoat, messagesWithSubscriptionExceptBoatRaces), $(userListWithDifferentSubscription, messageList));
    }

    @Test
    @Parameters(method = "sendMessagesToAllSubscribers")
    public void whenMessageCame_thanSendingToAllSubscribeUsers(List<Client> clientList, List<Message> messageToSend){

            ClientService clientService = createNiceMock(ClientService.class);
            expect(clientService.getAllClients()).andReturn(clientList).anyTimes();
            LoggerServiceImpl loggerService = createNiceMock(LoggerServiceImpl.class);

            replay(clientService);
            replay(loggerService);
        for (Message message : messageToSend) {
            racingResultsService.setClientService(clientService);
            racingResultsService.setLoggerServiceImpl(loggerService);

            racingResultsService.sendMessage(message);
        }

        for (Message message : messageToSend){
            Race messageTopic = message.getRaceType();
            for (Client client : clientList){
                if (client.getSubsription().contains(messageTopic)){
                    if (client.getMessages() != null)
                        Assert.assertTrue(client.getMessages().contains(message));
                }
            }
        }

        for (Message message : messageToSend){
            Race messageTopic = message.getRaceType();
            for (Client client : clientList){
                if (!client.getSubsription().contains(messageTopic)){
                    if (client.getMessages() != null)
                        Assert.assertFalse(client.getMessages().contains(message));
                }
            }
        }
    }

    @Test
    public void whenMessageCame_thanUsersReturnNullMessageList() {

        Client emptyClient = new Client();
        Set<Race> raceSet= new HashSet<Race>();
        raceSet.add(Race.BICYCLE_RACES);
        emptyClient.setSubsription(raceSet);

        List<Client> emptyClients = new ArrayList<Client>();
        emptyClients.add(emptyClient);

        ClientService clientService = createNiceMock(ClientService.class);
        expect(clientService.getAllClients()).andReturn(emptyClients).times(1);

        LoggerServiceImpl loggerService = createNiceMock(LoggerServiceImpl.class);

        replay(clientService);
        replay(loggerService);

        racingResultsService.setClientService(clientService);
        racingResultsService.setLoggerServiceImpl(loggerService);

        racingResultsService.sendMessage(new Message());

        verify(clientService);
    }

    @Test
    public void whenMessageCame_thanUsersReturnNullRaceSet() {

        Set<Race> raceSet= new HashSet<Race>();
        raceSet.add(Race.BICYCLE_RACES);

        Client emptyClient = createNiceMock(Client.class);

        expect(emptyClient.getMessages()).andReturn(null).anyTimes();
        expect(emptyClient.getSubsription()).andReturn(raceSet).anyTimes();

        List<Client> emptyClients = new ArrayList<Client>();
        emptyClients.add(emptyClient);

        ClientService clientService = createNiceMock(ClientService.class);
        expect(clientService.getAllClients()).andReturn(emptyClients).times(1);

        replay(emptyClient);
        replay(clientService);

        racingResultsService.setClientService(clientService);

        racingResultsService.sendMessage(new Message());

        verify(clientService);
        verify(emptyClient);
    }









    public Object [] sendMessagesAndLoggeIt(){

        // Subscriptions
        Set<Race> subscriptionForBicycleAndCarRaces = new HashSet<Race>();
        subscriptionForBicycleAndCarRaces.add(Race.BICYCLE_RACES);
        subscriptionForBicycleAndCarRaces.add(Race.CAR_RACES);

        Set<Race> subscriptionForBicyclerRaces = new HashSet<Race>();
        subscriptionForBicyclerRaces.add(Race.BICYCLE_RACES);

        Set<Race> subscriptionForBoatRaces = new HashSet<Race>();
        subscriptionForBoatRaces.add(Race.BOAT_RACES);

        // Clients
        Client clientSubscribedForBicycleAndCarRaces = createNiceMock(Client.class);
        expect(clientSubscribedForBicycleAndCarRaces.getSubsription()).andReturn(subscriptionForBicycleAndCarRaces).anyTimes();

        Client clientSubscribedForBoatRaces = createNiceMock(Client.class);
        expect(clientSubscribedForBoatRaces.getSubsription()).andReturn(subscriptionForBoatRaces).anyTimes();

        Client clientSubscribedForBicycleRace = createNiceMock(Client.class);
        expect(clientSubscribedForBicycleRace.getSubsription()).andReturn(subscriptionForBicyclerRaces).anyTimes();

        //Messages
        Message boatMessage = createNiceMock(Message.class);
        expect(boatMessage.getRaceType()).andReturn(Race.BOAT_RACES).anyTimes();

        Message carMessage= createNiceMock(Message.class);
        expect(carMessage.getRaceType()).andReturn(Race.CAR_RACES).anyTimes();

        Message bicycleMessage = createNiceMock(Message.class);
        expect(bicycleMessage.getRaceType()).andReturn(Race.BICYCLE_RACES).anyTimes();

        // Tests parameters

        List<Message> messageList= new ArrayList<Message>();
        messageList.add(boatMessage);
        messageList.add(bicycleMessage);
        messageList.add(carMessage);

        List<Client> userListWithDifferentSubscription = new ArrayList<Client>();
        userListWithDifferentSubscription.add(clientSubscribedForBicycleAndCarRaces);
        userListWithDifferentSubscription.add(clientSubscribedForBicycleRace);
        userListWithDifferentSubscription.add(clientSubscribedForBoatRaces);


        replay(clientSubscribedForBicycleAndCarRaces);
        replay(clientSubscribedForBoatRaces);
        replay(clientSubscribedForBicycleRace);
        replay(boatMessage);
        replay(carMessage);
        replay(bicycleMessage);

        return $(  $(userListWithDifferentSubscription, messageList));
    }

    @Test
    public void whenMessageCame_thanLoggeMessage(){


        // Subscriptions
        Set<Race> subscriptionForBicycleAndCarRaces = new HashSet<Race>();
        subscriptionForBicycleAndCarRaces.add(Race.BICYCLE_RACES);
        subscriptionForBicycleAndCarRaces.add(Race.CAR_RACES);

        Set<Race> subscriptionForBicyclerRaces = new HashSet<Race>();
        subscriptionForBicyclerRaces.add(Race.BICYCLE_RACES);

        Set<Race> subscriptionForBoatRaces = new HashSet<Race>();
        subscriptionForBoatRaces.add(Race.BOAT_RACES);

        // Clients
        Client clientSubscribedForBicycleAndCarRaces = createNiceMock(Client.class);
        expect(clientSubscribedForBicycleAndCarRaces.getSubsription()).andReturn(subscriptionForBicycleAndCarRaces).anyTimes();

        Client clientSubscribedForBoatRaces = createNiceMock(Client.class);
        expect(clientSubscribedForBoatRaces.getSubsription()).andReturn(subscriptionForBoatRaces).anyTimes();

        Client clientSubscribedForBicycleRace = createNiceMock(Client.class);
        expect(clientSubscribedForBicycleRace.getSubsription()).andReturn(subscriptionForBicyclerRaces).anyTimes();

        //Messages
        Message boatMessage = createNiceMock(Message.class);
        expect(boatMessage.getRaceType()).andReturn(Race.BOAT_RACES).times(1);

        Message carMessage= createNiceMock(Message.class);
        expect(carMessage.getRaceType()).andReturn(Race.CAR_RACES).times(1);

        Message bicycleMessage = createNiceMock(Message.class);
        expect(bicycleMessage.getRaceType()).andReturn(Race.BICYCLE_RACES).times(1);



        replay(clientSubscribedForBicycleAndCarRaces);
        replay(clientSubscribedForBoatRaces);
        replay(clientSubscribedForBicycleRace);
        replay(boatMessage);
        replay(carMessage);
        replay(bicycleMessage);

        // Tests parameters

        List<Message> messageList= new ArrayList<Message>();
        messageList.add(boatMessage);
        messageList.add(bicycleMessage);
        messageList.add(carMessage);

        List<Client> userListWithDifferentSubscription = new ArrayList<Client>();
        userListWithDifferentSubscription.add(clientSubscribedForBicycleAndCarRaces);
        userListWithDifferentSubscription.add(clientSubscribedForBicycleRace);
        userListWithDifferentSubscription.add(clientSubscribedForBoatRaces);


        ClientService clientService = createNiceMock(ClientService.class);
        expect(clientService.getAllClients()).andReturn(userListWithDifferentSubscription).anyTimes();
        LoggerServiceImpl loggerService = new LoggerServiceImpl();
        FileManager fileManager = createNiceMock(FileManager.class);
        loggerService.setFileManager(fileManager);

        replay(clientService);
        replay(fileManager);

        for (Message message : messageList) {
            racingResultsService.setClientService(clientService);
            racingResultsService.setLoggerServiceImpl(loggerService);

            racingResultsService.sendMessage(message);
        }

        verify(boatMessage);
        verify(bicycleMessage);
        verify(carMessage);


        verify(clientSubscribedForBoatRaces);
        verify(clientSubscribedForBicycleAndCarRaces);
        verify(clientSubscribedForBicycleRace);

    }
}
