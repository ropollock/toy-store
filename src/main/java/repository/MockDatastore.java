package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import utils.File;
import models.Identifiable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * MockDatastore
 *
 * Provides an in memory datastore container by bootstrapping json resources and mapping them to types
 */
public class MockDatastore implements Datastore {
    private static final Logger LOGGER = LogManager.getLogger(MockDatastore.class);

    private static class LazyHolder {
        private static final MockDatastore INSTANCE = new MockDatastore();
    }

    public static MockDatastore getInstance() {
        return LazyHolder.INSTANCE;
    }

    private List<Kit> kits;
    private List<KitType> kitTypes;
    private List<Order> orders;
    private List<PartType> partTypes;
    private List<PartTypeCategory> partTypeCategories;
    private List<Customer> customers;
    private List<PartInventory> partInventories;
    private List<Session> sessions;

    private final String KIT_TYPES_PATTERN = "kit-types/*.json";
    private final String PART_TYPES_PATTERN = "part-types/*.json";
    private final String PART_TYPE_CATEGORIES_PATTERN = "part-type-categories/*.json";
    private final String CUSTOMERS_PATTERN = "customers/*.json";
    private final String SESSIONS_PATTERN = "sessions/*.json";

    private MockDatastore() {
        init();
    }

    /**
     * init
     *
     * Provision the mock memory datastore with the bootstrap types.
     */
    public void init() {
        LOGGER.info("Initializing mock datastore");

        // Load kit types
        try {
            this.loadKitTypes();
        }
        catch (IOException e) {
            LOGGER.error("Unable to load kit types", e);
        }

        // Load part type categories
        try {
            this.loadPartTypeCategories();
        }
        catch (IOException e) {
            LOGGER.error("Unable to load part type categories", e);
        }

        // Load part types
        try {
            this.loadPartTypes();
        }
        catch (IOException e) {
            LOGGER.error("Unable to load part types", e);
        }

        // Load customers
        try {
            this.loadCustomers();
        } catch (IOException e) {
            LOGGER.error("Unable to load customers", e);
        }

        // Generate part inventory
        try {
            this.loadPartInventory();
        } catch (NullPointerException e) {
            LOGGER.error("Unable to generate part inventory", e);
        }

        // Load sessions
        try {
            this.loadSessions();
        } catch (IOException e) {
            LOGGER.error("Unable to sessions", e);
        }

        // Init orders
        this.loadOrders();

        // Init kits
        this.loadKits();
    }

    private void loadOrders() {
        this.orders = new ArrayList<>();
    }

    private void loadKits() {
        this.kits = new ArrayList<>();
    }

    /**
     * loadKitTypes
     * <p>
     * Load and map kit types from json
     *
     * @throws IOException
     */
    private void loadKitTypes() throws IOException {
        Resource[] kitTypeResources = File.getResourceInPath(KIT_TYPES_PATTERN);
        this.kitTypes = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for(Resource kitTypeResource : kitTypeResources) {
            if(kitTypeResource == null) {
                LOGGER.warn("Null kit type resource when loading kit types");
                continue;
            }

            String kitTypeJson = File.readResource(kitTypeResource.getInputStream());
            try {
                this.kitTypes.add(mapper.readValue(kitTypeJson, KitType.class));
                LOGGER.info(String.format("Mapped Kit Type from JSON %s", kitTypeResource.getURL()));
            } catch (Exception e) {
                LOGGER.error(String.format("Unable to map Kit Type %s JSON: %s",
                        kitTypeResource.getURL(), kitTypeJson), e);
            }
        }
    }

    /**
     * loadPartTypeCategories
     * <p>
     * Load and map part type categories from json
     *
     * @throws IOException
     */
    private void loadPartTypeCategories() throws IOException {
        Resource[] partTypeCategoryResources = File.getResourceInPath(PART_TYPE_CATEGORIES_PATTERN);
        this.partTypeCategories = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for(Resource partTypeCategoryRes: partTypeCategoryResources) {
            if(partTypeCategoryRes == null) {
                LOGGER.warn("Null part type category resource when loading part type categories");
                continue;
            }

            String partTypeCategoryJson = File.readResource(partTypeCategoryRes.getInputStream());
            try {
                this.partTypeCategories.add(mapper.readValue(partTypeCategoryJson, PartTypeCategory.class));
                LOGGER.info(String.format("Mapped Part Type Category from JSON %s", partTypeCategoryRes.getURL()));
            } catch (Exception e) {
                LOGGER.error(String.format("Unable to map Part Type Category %s JSON: %s",
                        partTypeCategoryRes.getURL(), partTypeCategoryJson), e);
            }
        }
    }

    /**
     * loadCustomers
     * <p>
     * Load and map customers from json
     *
     * @throws IOException
     */
    private void loadCustomers() throws IOException {
        Resource[] customerResources = File.getResourceInPath(CUSTOMERS_PATTERN);
        this.customers = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for(Resource customerResource: customerResources) {
            if(customerResource == null) {
                LOGGER.warn("Null customer resource when loading customers");
                continue;
            }

            String customerJson = File.readResource(customerResource.getInputStream());
            try {
                this.customers.add(mapper.readValue(customerJson, Customer.class));
                LOGGER.info(String.format("Mapped Customer from JSON %s", customerResource.getURL()));
            } catch (Exception e) {
                LOGGER.error(String.format("Unable to map Customer %s JSON: %s",
                        customerResource.getURL(), customerJson), e);
            }
        }
    }

    /**
     * loadPartTypes
     * <p>
     * Load and map part types from json
     *
     * @throws IOException
     */
    private void loadPartTypes() throws IOException {
        Resource[] partTypeResources = File.getResourceInPath(PART_TYPES_PATTERN);
        this.partTypes = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for(Resource partTypeRes: partTypeResources) {
            if(partTypeRes == null) {
                LOGGER.warn("Null part type resource when loading part type");
                continue;
            }

            String partTypeJson = File.readResource(partTypeRes.getInputStream());
            try {
                this.partTypes.add(mapper.readValue(partTypeJson, PartType.class));
                LOGGER.info(String.format("Mapped Part Type from JSON %s", partTypeRes.getURL()));
            } catch (Exception e) {
                LOGGER.error(String.format("Unable to map Part Type  %s JSON: %s",
                        partTypeRes.getURL(), partTypeJson), e);
            }
        }
    }

    /**
     * loadSessions
     * <p>
     * Load and map sessions from json
     *
     * @throws IOException
     */
    private void loadSessions() throws IOException {
        Resource[] sessionResources = File.getResourceInPath(SESSIONS_PATTERN);
        this.sessions = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        for(Resource sessionRes: sessionResources) {
            if(sessionRes == null) {
                LOGGER.warn("Null session resource when loading session");
                continue;
            }

            String sessionJson = File.readResource(sessionRes.getInputStream());
            try {
                this.sessions.add(mapper.readValue(sessionJson, Session.class));
                LOGGER.info(String.format("Mapped Session from JSON %s", sessionRes.getURL()));
            } catch (Exception e) {
                LOGGER.error(String.format("Unable to map Session  %s JSON: %s",
                        sessionRes.getURL(), sessionJson), e);
            }
        }
    }

    /**
     * loadPartInventory
     *
     * Use the part types to generate a part inventory with random stock values.
     *
     * @throws NullPointerException
     */
    private void loadPartInventory()  throws NullPointerException {
        if(this.partTypes == null) {
            throw new NullPointerException("partTypes cannot be null when generating part inventory");
        }
        Random r = new Random();
        int max = 25;
        this.partInventories = this.partTypes.stream().map((pt) -> {
            PartInventory pi = new PartInventory();
            pi.setId(pt.getId());
            pi.setStock(r.nextInt(max));
            LOGGER.info(String.format("Generated part inventory: %s", pi.toString()));
            return pi;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Kit> getKits() {
        return kits;
    }

    @Override
    public List<KitType> getKitTypes() {
        return kitTypes;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public List<PartType> getPartTypes() {
        return partTypes;
    }

    @Override
    public List<PartTypeCategory> getPartTypeCategories() {
        return partTypeCategories;
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public List<PartInventory> getPartInventories() {
        return partInventories;
    }

    @Override
    public List<Session> getSessions() {
        return sessions;
    }

    /**
     * nextId
     *
     * Returns the next id integer given a list of identifiable.
     *
     * @param list List of identifiable
     * @return Integer
     */
    @Override
    public synchronized Integer nextId(List<? extends Identifiable> list) {
        if(list.size() == 0) {
            return 1;
        }
        // Find the max id and return the max + 1;
        return list.stream().max(Comparator.comparingInt(Identifiable::getId)).get().getId() + 1;
    }
}
