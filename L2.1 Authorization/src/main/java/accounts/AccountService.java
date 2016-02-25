package accounts;

import dbService.DBException;
import dbService.DBService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountService {
    //private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;
    private DBService dbService;


    public AccountService() {
        //loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        dbService= new DBService();
        //dbService.printConnectionInfo();
    }

    public void addNewUser(UserProfile userProfile) throws DBException {
        //loginToProfile.put(userProfile.getLogin(), userProfile);
        dbService.addUser(userProfile);
    }

    public UserProfile getUserByLogin(String login) throws DBException {
        return dbService.getUser(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
