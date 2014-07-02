import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import static junitparams.JUnitParamsRunner.$;

import java.util.*;

/**
 * @author Andrii_Manieshyn
 */
@RunWith(JUnitParamsRunner.class)
public class RacingResultsServiceTest {

    public RacingResultsServiceImpl racingResultsService;

    @Before
    public void initMockito(){
        MockitoAnnotations.initMocks(this);
        racingResultsService = new RacingResultsServiceImpl();
    }


    public Object [] subscriptionInput(){
        return $(Race.BICYCLE_RACES, Race.BOAT_RACES, Race.CAR_RACES);
    }


    @Test
    @Parameters(method = "subscriptionInput")
    public void whenUserWantToSubscribeForNews_thanRacingResultServiceShouldSubscribeHim(Race race){
        Set<Race> subscriptionSet = new HashSet<Race>();
        Client client = mock(Client.class);
        when(client.getSubsription()).thenReturn(subscriptionSet);

        racingResultsService.subscribeUserForNews(client, race);

        verify(client).getSubsription();
        verify(client).setSubsription(subscriptionSet);
        Assert.assertTrue(client.getSubsription().contains(race));
    }

    @Test
    public void whenUserWantToSubscribeForNews_thanClientReturnNullSet(){
        Client client = mock(Client.class);
        when(client.getSubsription()).thenReturn(null);

        racingResultsService.subscribeUserForNews(client, Race.BICYCLE_RACES);

        verify(client).getSubsription();
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
        Client client = mock(Client.class);
        when(client.getSubsription()).thenReturn(null);

        racingResultsService.unsubscribeUserForNews(client, Race.BICYCLE_RACES);

        verify(client).getSubsription();
    }

    @Test
    @Parameters(method = "unsubscriptionInput")
    public void whenUserWantToUnsubscribeForNews_thanRacingResultServiceShouldUnsubscribeHim(Set<Race> raceSet, Set<Race> raceSetExpected){
        Client client = mock(Client.class);
        when(client.getSubsription()).thenReturn(raceSet);

        racingResultsService.unsubscribeUserForNews(client, Race.BICYCLE_RACES);

        verify(client).getSubsription();
        verify(client).setSubsription(raceSet);
        Assert.assertTrue(client.getSubsription().containsAll(raceSetExpected));

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
        Message boatMessage = mock(Message.class);
        when(boatMessage.getRaceType()).thenReturn(Race.BOAT_RACES);

        Message carMessage= mock(Message.class);
        when(carMessage.getRaceType()).thenReturn(Race.CAR_RACES);

        Message bicycleMessage = mock(Message.class);
        when(bicycleMessage.getRaceType()).thenReturn(Race.BICYCLE_RACES);

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
        ClientService clientService = mock(ClientService.class);
        when(clientService.getAllClients()).thenReturn(clientList);
        LoggerServiceImpl loggerService = mock(LoggerServiceImpl.class);

        racingResultsService.setClientService(clientService);
        racingResultsService.setLoggerServiceImpl(loggerService);

        for (Message message : messageToSend) {
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

        ClientService clientService = mock(ClientService.class);
        when(clientService.getAllClients()).thenReturn(emptyClients);

        LoggerServiceImpl loggerService = mock(LoggerServiceImpl.class);
        racingResultsService.setClientService(clientService);
        racingResultsService.setLoggerServiceImpl(loggerService);

        racingResultsService.sendMessage(new Message());

        verify(clientService).getAllClients();
    }

    @Test
    public void whenMessageCame_thanUsersReturnNullRaceSet() {

        Set<Race> raceSet= new HashSet<Race>();
        raceSet.add(Race.BICYCLE_RACES);

        Client emptyClient = mock(Client.class);
        when(emptyClient.getMessages()).thenReturn(null);
        when(emptyClient.getSubsription()).thenReturn(raceSet);

        List<Client> emptyClients = new ArrayList<Client>();
        emptyClients.add(emptyClient);

        ClientService clientService = mock(ClientService.class);
        when(clientService.getAllClients()).thenReturn(emptyClients);
        racingResultsService.setClientService(clientService);

        racingResultsService.sendMessage(new Message());

        verify(clientService).getAllClients();
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
        Client clientSubscribedForBicycleAndCarRaces = mock(Client.class);
        when(clientSubscribedForBicycleAndCarRaces.getSubsription()).thenReturn(subscriptionForBicycleAndCarRaces);

        Client clientSubscribedForBoatRaces = mock(Client.class);
        when(clientSubscribedForBoatRaces.getSubsription()).thenReturn(subscriptionForBoatRaces);

        Client clientSubscribedForBicycleRace = mock(Client.class);
        when(clientSubscribedForBicycleRace.getSubsription()).thenReturn(subscriptionForBicyclerRaces);

        //Messages
        Message boatMessage = mock(Message.class);
        when(boatMessage.getRaceType()).thenReturn(Race.BOAT_RACES);

        Message carMessage= mock(Message.class);
        when(carMessage.getRaceType()).thenReturn(Race.CAR_RACES);

        Message bicycleMessage = mock(Message.class);
        when(bicycleMessage.getRaceType()).thenReturn(Race.BICYCLE_RACES);

        // Tests parameters

        List<Message> messageList= new ArrayList<Message>();
        messageList.add(boatMessage);
        messageList.add(bicycleMessage);
        messageList.add(carMessage);

        List<Client> userListWithDifferentSubscription = new ArrayList<Client>();
        userListWithDifferentSubscription.add(clientSubscribedForBicycleAndCarRaces);
        userListWithDifferentSubscription.add(clientSubscribedForBicycleRace);
        userListWithDifferentSubscription.add(clientSubscribedForBoatRaces);

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
        Client clientSubscribedForBicycleAndCarRaces = mock(Client.class);
        when(clientSubscribedForBicycleAndCarRaces.getSubsription()).thenReturn(subscriptionForBicycleAndCarRaces);

        Client clientSubscribedForBoatRaces = mock(Client.class);
        when(clientSubscribedForBoatRaces.getSubsription()).thenReturn(subscriptionForBoatRaces);

        Client clientSubscribedForBicycleRace = mock(Client.class);
        when(clientSubscribedForBicycleRace.getSubsription()).thenReturn(subscriptionForBicyclerRaces);

        //Messages
        Message boatMessage = mock(Message.class);
        when(boatMessage.getRaceType()).thenReturn(Race.BOAT_RACES);

        Message carMessage= mock(Message.class);
        when(carMessage.getRaceType()).thenReturn(Race.CAR_RACES);

        Message bicycleMessage = mock(Message.class);
        when(bicycleMessage.getRaceType()).thenReturn(Race.BICYCLE_RACES);

        // Tests parameters

        List<Message> messageList= new ArrayList<Message>();
        messageList.add(boatMessage);
        messageList.add(bicycleMessage);
        messageList.add(carMessage);

        List<Client> userListWithDifferentSubscription = new ArrayList<Client>();
        userListWithDifferentSubscription.add(clientSubscribedForBicycleAndCarRaces);
        userListWithDifferentSubscription.add(clientSubscribedForBicycleRace);
        userListWithDifferentSubscription.add(clientSubscribedForBoatRaces);







        ClientService clientService = mock(ClientService.class);
        when(clientService.getAllClients()).thenReturn(userListWithDifferentSubscription);
        LoggerServiceImpl loggerService = new LoggerServiceImpl();
        FileManager fileManager = mock(FileManager.class);
        loggerService.setFileManager(fileManager);

        racingResultsService.setClientService(clientService);
        racingResultsService.setLoggerServiceImpl(loggerService);

        for (Message message : messageList) {
            racingResultsService.sendMessage(message);
        }

        verify(boatMessage, times(1)).getMessage();
        verify(boatMessage, times(1)).getDate();

        verify(bicycleMessage, times(2)).getMessage();
        verify(bicycleMessage, times(2)).getDate();

        verify(carMessage, times(1)).getMessage();
        verify(carMessage, times(1)).getDate();


        verify(clientSubscribedForBoatRaces, times(1)).getName();

        verify(clientSubscribedForBicycleAndCarRaces, times(2)).getName();

        verify(clientSubscribedForBicycleRace, times(1)).getName();

    }
}
