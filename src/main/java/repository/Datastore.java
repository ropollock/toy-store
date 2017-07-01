package repository;

import models.*;

import java.util.List;

public interface Datastore {
    List<Kit> getKits();
    List<KitType> getKitTypes();
    List<Order> getOrders();
    List<PartType> getPartTypes();
    List<PartTypeCategory> getPartTypeCategories();
    List<Customer> getCustomers();
    List<PartInventory> getPartInventories();
    List<Session> getSessions();
    Integer nextId(List<? extends Identifiable> list);
}
