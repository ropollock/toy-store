package domain;

import models.*;
import models.builders.KitBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.*;
import requests.CreateOrderRequest;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private final static Logger LOGGER = LogManager.getLogger(OrderService.class);
    private OrderDao orderDao;
    private CustomerDao customerDao;
    private KitDao kitDao;
    private PartTypeDao partTypeDao;
    private KitTypeDao kitTypeDao;
    private PartTypeCategoryDao partTypeCategoryDao;
    private PartInventoryService partInventoryService;

    public OrderService() {
        init();
    }

    public void init() {
        this.orderDao = DaoFactory.getOrderDao(DaoFactory.DAO_TYPES.MOCK);
        this.customerDao = DaoFactory.getCustomerDao(DaoFactory.DAO_TYPES.MOCK);
        this.kitDao = DaoFactory.getKitDao(DaoFactory.DAO_TYPES.MOCK);
        this.partTypeDao = DaoFactory.getPartTypeDao(DaoFactory.DAO_TYPES.MOCK);
        this.kitTypeDao = DaoFactory.getKitTypeDao(DaoFactory.DAO_TYPES.MOCK);
        this.partTypeCategoryDao = DaoFactory.getPartTypeCategory(DaoFactory.DAO_TYPES.MOCK);
        this.partInventoryService = new PartInventoryService();
    }

    public Order getOrder(final Integer id) {
        return orderDao.findById(id);
    }

    public Integer createOrder(CreateOrderRequest request)
            throws InvalidParameterException {
        // Build order
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setShippingAddress(request.getShippingAddress());

        // Generate order kits
        List<Kit> kits = kitsFromRequest(request.getKits());
        // Check for another duplicate kit within the constraint radius
        List<Order> duplicateKitOrders = kits.stream()
                .map(Kit::getPartsHash)
                .flatMap((partHash) -> kitDao.findByPartHash(partHash).stream())
                .map(Kit::getOrderId)
                .map(orderDao::findById)
                .filter(out -> out != null)
                .distinct()
                .collect(Collectors.toList());

        if(!duplicateKitOrders.isEmpty()) {
            // Call geo location endpoint
            GeoService geo = new GeoService();
            duplicateKitOrders.stream()
                    .forEach((kit) -> {
                        float distance = geo.getDistance(kit.getShippingAddress(),
                                order.getShippingAddress());
                        if (distance <= 100.0f) {
                            LOGGER.debug(String.format("Shipping addresses too close, distance %s", distance));
                            StringBuilder sb = new StringBuilder();
                            sb.append("Cannot place order to `%s`");
                            sb.append(" because this kit was ordered within 100 miles of it. (distance %s) %s");
                            throw new InvalidParameterException(
                                    String.format(sb.toString(), order.getShippingAddress(), distance, kit.toString()));
                        }
                    });
        }

        // Calculate total price
        order.setTotalPrice(kits.stream()
                .mapToDouble(this::calculateKitPrice)
                .sum());
        // Persist order
        Integer orderId = this.orderDao.insertOrder(order);
        // Persist kits
        kits.stream()
                .forEach((kit) -> {kit.setOrderId(orderId); kitDao.insertKit(kit);});

        List<Integer> partTypeIdsSold = kits.stream()
                .flatMap((k) -> k.getPartTypeIds().stream())
                .collect(Collectors.toList());
        // Notify part inventory of part type allocation
        this.partInventoryService.registerPartTypesSale(partTypeIdsSold);
        // Return order id
        return orderId;
    }

    private Double calculateKitPrice(Kit kit) {
        KitType kitType = this.kitTypeDao.findById(kit.getKitTypeId());
        List<Integer> nonDefaultPartTypes = kitType.getNonDefaultParts(kit);
        Double price = kitType.getBasePrice();
        Double partCost = nonDefaultPartTypes.stream()
                .mapToDouble((partTypeId) -> this.partTypeDao.findById(partTypeId).getSurcharge())
                .sum();
        return price + partCost;
    }

    private List<Kit> kitsFromRequest(List<Kit> requestKits) {
        return requestKits.stream()
                .map((requestKit) -> {
                    KitType kitType = this.kitTypeDao.findById(requestKit.getKitTypeId());
                    KitBuilder builder = new KitBuilder(kitType);
                    // Override kit values with request and use kit type as defaults
                    if (requestKit.getName() != null) {
                        builder = builder.setName(requestKit.getName());
                    }
                    if (requestKit.getBodyPartId() != null) {
                        builder = builder.setBodyPartId(requestKit.getBodyPartId());
                    }
                    if (requestKit.getWheelsPartId() != null) {
                        builder = builder.setWheelsPartId(requestKit.getWheelsPartId());
                    }
                    if (requestKit.getPowerSourcePartId() != null) {
                        builder = builder.setPowerSourcePartId(requestKit.getPowerSourcePartId());
                    }
                    if (requestKit.getColorPartId() != null) {
                        builder = builder.setColorPartId(requestKit.getColorPartId());
                    }
                    if (requestKit.getEnginePartId() != null) {
                        builder = builder.setEnginePartId(requestKit.getEnginePartId());
                    }
                    if (requestKit.getFinishPartId() != null) {
                        builder = builder.setFinishPartId(requestKit.getFinishPartId());
                    }
                    return builder.build();
                })
                .collect(Collectors.toList());
    }

    public void assertValidCreateOrderRequest(CreateOrderRequest request)
            throws InvalidParameterException {
        // Find valid customer
        if(customerDao.findById(request.getCustomerId()) == null) {
            throw new InvalidParameterException("The customer could not be found.");
        }

        // Check for any invalid part types
        for(Kit kit: request.getKits()) {
            // Check for any invalid kit types
            KitType kitType = kitTypeDao.findById(kit.getKitTypeId());
            if(kitType == null) {
                throw new InvalidParameterException(
                        String.format("Invalid kit type id %s", kit.getKitTypeId()));
            }

            // Check for valid body part type
            if(kit.getBodyPartId() != null) {
                if(!this.isValidPartType(kit.getBodyPartId(), "body")) {
                    throw new InvalidParameterException(
                            String.format("Invalid part type for `bodyPartId`: %s  %s",
                                    kit.getBodyPartId(), kit.toString()));
                }
            }

            // Check for valid engine part type
            if(kit.getEnginePartId() != null) {
                if(!this.isValidPartType(kit.getEnginePartId(), "engine")) {
                    throw new InvalidParameterException(
                            String.format("Invalid part type for `enginePartId`: %s  %s",
                                    kit.getEnginePartId(), kit.toString()));
                }
            }

            // Check for valid color part type
            if(kit.getColorPartId() != null) {
                if(!this.isValidPartType(kit.getColorPartId(), "color")) {
                    throw new InvalidParameterException(
                            String.format("Invalid part type for `colorPartId`: %s  %s",
                                    kit.getColorPartId(), kit.toString()));
                }
            }

            // Check for valid finish part type
            if(kit.getFinishPartId() != null) {
                if(!this.isValidPartType(kit.getFinishPartId(), "finish")) {
                    throw new InvalidParameterException(
                            String.format("Invalid part type for `finishPartId`: %s  %s",
                                    kit.getFinishPartId(), kit.toString()));
                }
            }

            // Check for valid power source part type
            if(kit.getPowerSourcePartId() != null) {
                if(!this.isValidPartType(kit.getPowerSourcePartId(), "power source")) {
                    throw new InvalidParameterException(
                            String.format("Invalid part type for `powerSourcePartId`: %s  %s",
                                    kit.getPowerSourcePartId(), kit.toString()));
                }
            }

            // Check for valid wheels part type
            if(kit.getWheelsPartId() != null) {
                if(!this.isValidPartType(kit.getWheelsPartId(), "wheels")) {
                    throw new InvalidParameterException(
                            String.format("Invalid part type for `wheelsPartId`: %s  %s",
                                    kit.getWheelsPartId(), kit.toString()));
                }
            }
        }
    }

    private boolean isValidPartType(final Integer partTypeId, final String category) {
        PartType bodyPartType = partTypeDao.findById(partTypeId);
        if(bodyPartType == null) {
            return false;
        } else {
            // Check for incompatible part type
            PartTypeCategory partTypeCategory =
                    partTypeCategoryDao.findById(bodyPartType.getCategoryId());
            if(!partTypeCategory.getCategory().equalsIgnoreCase(category)) {
                return false;
            }
        }
        return true;
    }
}
