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
    private LoggerServiceImpl loggerServiceImpl;


    public void subscribeUserForNews(Client client, Race bicycleRaces) {
        Set<Race> raceSet = client.getSubsription();
        if (raceSet != null) {
            raceSet.add(bicycleRaces);
            client.setSubsription(raceSet);
        }
    }

    public void unsubscribeUserForNews(Client client, Race race) {
        Set<Race> raceSet = client.getSubsription();
        if (raceSet != null) {
            raceSet.remove(race);
            client.setSubsription(raceSet);
        }
    }

    public void sendMessage(Message message) {
       List<Client> clientList = clientService.getAllClients();
        for (Client client : clientList){
            if (client.getSubsription().contains(message.getRaceType())){
                List<Message> messageList = client.getMessages();
                if (messageList!=null) {
                    messageList.add(message);
                    loggerServiceImpl.loggeMessage(client, message);
                    client.setMessages(messageList);
                }
            }
        }
    }


    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void setLoggerServiceImpl(LoggerServiceImpl loggerServiceImpl) {
        this.loggerServiceImpl = loggerServiceImpl;
    }
}
