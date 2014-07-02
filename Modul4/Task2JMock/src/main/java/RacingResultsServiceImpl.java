/**
 * Created by Paladin on 18.06.2014.
 */

import java.util.List;
import java.util.Set;

/**
 * @author Andrii_Manieshyn
 */

public class RacingResultsServiceImpl {

    private ClientService clientService;
    private LoggerService loggerServiceImpl;


    public void subscribeUserForNews(Client clientImpl, Race bicycleRaces) {
        Set<Race> raceSet = clientImpl.getSubsription();
        if (raceSet != null) {
            raceSet.add(bicycleRaces);
            clientImpl.setSubsription(raceSet);
        }
    }

    public void unsubscribeUserForNews(Client clientImpl, Race race) {
        Set<Race> raceSet = clientImpl.getSubsription();
        if (raceSet != null) {
            raceSet.remove(race);
            clientImpl.setSubsription(raceSet);
        }
    }

    public void sendMessage(Message message) {
       List<ClientImpl> clientImplList = clientService.getAllClients();
        for (Client clientImpl : clientImplList){
            if (clientImpl.getSubsription().contains(message.getRaceType())){
                List<Message> messageList = clientImpl.getMessages();
                if (messageList!=null) {
                    messageList.add(message);
                    loggerServiceImpl.loggeMessage(clientImpl, message);
                    clientImpl.setMessages(messageList);
                }
            }
        }
    }


    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void setLoggerService(LoggerService loggerService) {
        this.loggerServiceImpl = loggerService;
    }
}
