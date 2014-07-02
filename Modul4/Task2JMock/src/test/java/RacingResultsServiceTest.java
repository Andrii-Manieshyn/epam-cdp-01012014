import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        racingResultsService = new RacingResultsServiceImpl();
    }


    public Object [] subscriptionInput(){
        return $(Race.BICYCLE_RACES, Race.BOAT_RACES, Race.CAR_RACES);
    }


    @Test
    @Parameters(method = "subscriptionInput")
    public void whenUserWantToSubscribeForNews_thanRacingResultServiceShouldSubscribeHim(final Race race){
        Mockery context = new Mockery();
        final Set<Race> subscriptionSet = new HashSet<Race>();
        final Client clientImpl = context.mock(Client.class);

        context.checking(new Expectations() {{
            atLeast(1).of(clientImpl).getSubsription();
            will(returnValue(subscriptionSet));
        }});
        context.checking(new Expectations() {{
            atLeast(1).of(clientImpl).setSubsription(subscriptionSet);
        }});

        racingResultsService.subscribeUserForNews(clientImpl, race);

        context.assertIsSatisfied();

        Assert.assertTrue(clientImpl.getSubsription().contains(race));
    }


    @Test
    public void whenUserWantToSubscribeForNews_thanClientReturnNullSet(){
        Mockery context = new Mockery();
        final Client clientImpl = context.mock(Client.class);

        context.checking(new Expectations() {{
            atLeast(1).of(clientImpl).getSubsription();
            will(returnValue(null));
        }});
        racingResultsService.subscribeUserForNews(clientImpl, Race.BICYCLE_RACES);

        context.assertIsSatisfied();
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

        Mockery context = new Mockery();
        final Client clientImpl = context.mock(Client.class);

        context.checking(new Expectations() {{
            atLeast(1).of(clientImpl).getSubsription();
            will(returnValue(null));
        }});

        racingResultsService.unsubscribeUserForNews(clientImpl,  Race.BICYCLE_RACES);

        context.assertIsSatisfied();
    }

    @Test
    @Parameters(method = "unsubscriptionInput")
    public void whenUserWantToUnsubscribeForNews_thanRacingResultServiceShouldUnsubscribeHim(final Set<Race> raceSet,final Set<Race> raceSetExpected){
        Mockery context = new Mockery();
        final Client clientImpl = context.mock(Client.class);

        context.checking(new Expectations() {{
            atLeast(1).of(clientImpl).getSubsription();
            will(returnValue(raceSet));
        }});
        context.checking(new Expectations() {{
            atLeast(1).of(clientImpl).setSubsription(raceSet);
        }});

        racingResultsService.subscribeUserForNews(clientImpl, Race.BICYCLE_RACES);

        Assert.assertTrue(clientImpl.getSubsription().containsAll(raceSetExpected));

        context.assertIsSatisfied();
    }


    @Test
    public void whenMessageCame_thanSendingToAllSubscribeUsers(){
        Mockery context = new Mockery();
        //Messages
        final Message boatMessage = context.mock(Message.class, Race.BOAT_RACES.name());
        context.checking(new Expectations() {{
            atLeast(1).of(boatMessage).getRaceType();
            will(returnValue(Race.BOAT_RACES));
        }});

        final Message carMessage=  context.mock(Message.class, Race.CAR_RACES.name());
        context.checking(new Expectations() {{
            atLeast(1).of(carMessage).getRaceType();
            will(returnValue(Race.CAR_RACES));
        }});

        final Message bicycleMessage = context.mock(Message.class, Race.BICYCLE_RACES.name());
        context.checking(new Expectations() {{
            atLeast(1).of(bicycleMessage).getRaceType();
            will(returnValue(Race.BICYCLE_RACES));
        }});

        Set<Race> subscriptionForBicycleAndCarRaces = new HashSet<Race>();
        subscriptionForBicycleAndCarRaces.add(Race.BICYCLE_RACES);
        subscriptionForBicycleAndCarRaces.add(Race.CAR_RACES);

        Set<Race> subscriptionForBicyclerRaces = new HashSet<Race>();
        subscriptionForBicyclerRaces.add(Race.BICYCLE_RACES);

        Set<Race> subscriptionForBoatRaces = new HashSet<Race>();
        subscriptionForBoatRaces.add(Race.BOAT_RACES);


        Client clientSubscribedForBicycleAndCarRaces = new ClientImpl();
        clientSubscribedForBicycleAndCarRaces.setSubsription(subscriptionForBicycleAndCarRaces);

        Client clientSubscribedForBoatRaces = new ClientImpl();
        clientSubscribedForBoatRaces.setSubsription(subscriptionForBoatRaces);

        Client clientSubscribedForBicycleRace = new ClientImpl();
        clientSubscribedForBicycleRace.setSubsription(subscriptionForBicyclerRaces);


        List<Message> messageList= new ArrayList<Message>();
        messageList.add(boatMessage);
        messageList.add(bicycleMessage);
        messageList.add(carMessage);

        final List<Client> userListWithDifferentSubscription = new ArrayList<Client>();
        userListWithDifferentSubscription.add(clientSubscribedForBicycleAndCarRaces);
        userListWithDifferentSubscription.add(clientSubscribedForBicycleRace);
        userListWithDifferentSubscription.add(clientSubscribedForBoatRaces);


        final ClientService clientService = context.mock(ClientService.class);
        context.checking(new Expectations() {{
            atLeast(1).of(clientService).getAllClients();
            will(returnValue(userListWithDifferentSubscription));
        }});
        final LoggerService loggerService = context.mock(LoggerService.class);

        for (Message message : messageList) {
            racingResultsService.setClientService(clientService);
            racingResultsService.setLoggerService(loggerService);

            racingResultsService.sendMessage(message);
        }

        for (Message message : messageList){
            Race messageTopic = message.getRaceType();
            for (Client client : userListWithDifferentSubscription){
                if (client.getSubsription().contains(messageTopic)){
                    if (client.getMessages() != null)
                        Assert.assertTrue(client.getMessages().contains(message));
                }
            }
        }

        for (Message message : messageList){
            Race messageTopic = message.getRaceType();
            for (Client client : userListWithDifferentSubscription){
                if (!client.getSubsription().contains(messageTopic)){
                    if (client.getMessages() != null)
                        Assert.assertFalse(client.getMessages().contains(message));
                }
            }
        }
    }




    @Test
    public void whenMessagesWithSubscriptionExceptBoatRacesCame_thanSendingToAllSubscribeUsers(){
        Mockery context = new Mockery();
        //Messages
        final Message boatMessage = context.mock(Message.class, Race.BOAT_RACES.name());
        context.checking(new Expectations() {{
            atLeast(1).of(boatMessage).getRaceType();
            will(returnValue(Race.BOAT_RACES));
        }});

        final Message carMessage=  context.mock(Message.class, Race.CAR_RACES.name());
        context.checking(new Expectations() {{
            atLeast(1).of(carMessage).getRaceType();
            will(returnValue(Race.CAR_RACES));
        }});

        final Message bicycleMessage = context.mock(Message.class, Race.BICYCLE_RACES.name());
        context.checking(new Expectations() {{
            atLeast(1).of(bicycleMessage).getRaceType();
            will(returnValue(Race.BICYCLE_RACES));
        }});

        Set<Race> subscriptionForBicycleAndCarRaces = new HashSet<Race>();
        subscriptionForBicycleAndCarRaces.add(Race.BICYCLE_RACES);
        subscriptionForBicycleAndCarRaces.add(Race.CAR_RACES);

        Set<Race> subscriptionForBicyclerRaces = new HashSet<Race>();
        subscriptionForBicyclerRaces.add(Race.BICYCLE_RACES);

        Set<Race> subscriptionForBoatRaces = new HashSet<Race>();
        subscriptionForBoatRaces.add(Race.BOAT_RACES);


        Client clientSubscribedForBicycleAndCarRaces = new ClientImpl();
        clientSubscribedForBicycleAndCarRaces.setSubsription(subscriptionForBicycleAndCarRaces);

        Client clientSubscribedForBoatRaces = new ClientImpl();
        clientSubscribedForBoatRaces.setSubsription(subscriptionForBoatRaces);

        Client clientSubscribedForBicycleRace = new ClientImpl();
        clientSubscribedForBicycleRace.setSubsription(subscriptionForBicyclerRaces);

        List<Message> messageList = new ArrayList<Message>();
        messageList.add(bicycleMessage);
        messageList.add(carMessage);

        final List<Client> userListWithDifferentSubscription = new ArrayList<Client>();
        userListWithDifferentSubscription.add(clientSubscribedForBicycleAndCarRaces);
        userListWithDifferentSubscription.add(clientSubscribedForBicycleRace);


        final ClientService clientService = context.mock(ClientService.class);
        context.checking(new Expectations() {{
            atLeast(1).of(clientService).getAllClients();
            will(returnValue(userListWithDifferentSubscription));
        }});
        final LoggerService loggerService = context.mock(LoggerService.class);

        for (Message message : messageList) {
            racingResultsService.setClientService(clientService);
            racingResultsService.setLoggerService(loggerService);

            racingResultsService.sendMessage(message);
        }

        for (Message message : messageList){
            Race messageTopic = message.getRaceType();
            for (Client client : userListWithDifferentSubscription){
                if (client.getSubsription().contains(messageTopic)){
                    if (client.getMessages() != null)
                        Assert.assertTrue(client.getMessages().contains(message));
                }
            }
        }

        for (Message message : messageList){
            Race messageTopic = message.getRaceType();
            for (Client client : userListWithDifferentSubscription){
                if (!client.getSubsription().contains(messageTopic)){
                    if (client.getMessages() != null)
                        Assert.assertFalse(client.getMessages().contains(message));
                }
            }
        }
    }

    @Test
    public void whenMessageCame_thanUsersReturnNullMessageList() {
        Mockery context = new Mockery();
        final Client emptyClient = context.mock(Client.class);
        final Set<Race> raceSet= new HashSet<Race>();
        raceSet.add(Race.BICYCLE_RACES);

        final List<Client> emptyClients = new ArrayList<Client>();
        emptyClients.add(emptyClient);

        final ClientService clientService = context.mock(ClientService.class);
        context.checking(new Expectations() {{
            atLeast(1).of(clientService).getAllClients();
            will(returnValue(emptyClients));
        }});

        LoggerService loggerService = context.mock(LoggerService.class);
        context.checking(new Expectations() {{
            atLeast(1).of(emptyClient).getSubsription();
            will(returnValue(raceSet));
        }});
        context.checking(new Expectations() {{
            atLeast(1).of(emptyClient).getMessages();
            will(returnValue(null));
        }});

        final Message message = context.mock(Message.class);
        context.checking(new Expectations() {{
            atLeast(1).of(message).getRaceType();
            will(returnValue(Race.BICYCLE_RACES));
        }});


        racingResultsService.setClientService(clientService);
        racingResultsService.setLoggerService(loggerService);

        racingResultsService.sendMessage(message);

        context.assertIsSatisfied();
    }

}
