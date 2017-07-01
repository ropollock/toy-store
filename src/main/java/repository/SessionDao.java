package repository;

import models.Session;

public interface SessionDao {
    Session findByToken(String token);
}
