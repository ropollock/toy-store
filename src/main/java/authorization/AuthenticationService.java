package authorization;

import models.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.DaoFactory;
import repository.SessionDao;

import java.util.Arrays;

public class AuthenticationService {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);
    private SessionDao sessionDao;

    public AuthenticationService() { init(); }

    private void init() {
        this.sessionDao = DaoFactory.getSessioDao(DaoFactory.DAO_TYPES.MOCK);
    }

    /**
     * authorized
     *
     * Checks that a token is found in the datastore.
     * For simplicity, no expiring or mapping to a user
     *
     * @param token String hash
     * @return boolean
     */
    public boolean authorized(String token) {
        Session session = sessionDao.findByToken(token);
        if(session == null) {
            LOGGER.info(String.format("Unauthorized session token %s", token));
            return false;
        } else {
            LOGGER.info(String.format("Authorized session token %s", token));
            return true;
        }
    }

    /**
     * hasPermission
     * <p>
     * Checks that each of the permissions specified are owned by the session.
     *
     * @param token String hash
     * @param permissions String array of permissions
     * @return boolean
     */
    public boolean hasPermission(final String token, String... permissions) {
        Session session = sessionDao.findByToken(token);
        if(session == null) {
            return false;
        }

        boolean permitted = Arrays.stream(permissions)
                .allMatch((requestPerm) -> session.getPermissions().stream().anyMatch(requestPerm::equalsIgnoreCase));

        if(!permitted) {
            LOGGER.info(String.format("session %s does not have permission %s", token, Arrays.toString(permissions)));
        } else {
            LOGGER.info(String.format("session %s has permission %s", token, Arrays.toString(permissions)));
        }

        return permitted;
    }
}
