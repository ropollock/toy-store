package repository;

import java.security.InvalidParameterException;

public class DatastoreFactory {
    public enum DATASTORE_TYPES {
        MOCK
    }

    public static Datastore getDatastore(DATASTORE_TYPES type) throws InvalidParameterException {
        switch (type) {
            case MOCK:
                return MockDatastore.getInstance();
            default:
                throw new InvalidParameterException(
                        String.format("Unrecognized datastore type: %s", type.toString()));
        }
    }
}
