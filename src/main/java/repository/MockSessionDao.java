package repository;

import models.Session;

public class MockSessionDao implements SessionDao {
    private final Datastore datastore;

    public MockSessionDao() {
        this.datastore = DatastoreFactory.getDatastore(DatastoreFactory.DATASTORE_TYPES.MOCK);
    }

    @Override
    public Session findByToken(String token) {
        return this.datastore.getSessions().stream()
                .filter((session) -> token.equals(session.getToken()))
                .findFirst()
                .orElse(null);
    }
}
