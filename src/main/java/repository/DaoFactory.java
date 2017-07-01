package repository;

import java.security.InvalidParameterException;

public class DaoFactory {
    public enum DAO_TYPES {
        MOCK
    }

    public static OrderDao getOrderDao(DAO_TYPES type) {
        switch (type) {
            case MOCK:
                return new MockOrderDao();
            default:
                throw new InvalidParameterException(
                        String.format("Unrecognized dao type creating order dao: %s",
                                type.toString()));
        }
    }

    public static CustomerDao getCustomerDao(DAO_TYPES type) {
        switch (type) {
            case MOCK:
                return new MockCustomerDao();
            default:
                throw new InvalidParameterException(
                        String.format("Unrecognized dao type creating customer dao: %s",
                                type.toString()));
        }
    }

    public static KitDao getKitDao(DAO_TYPES type) {
        switch (type) {
            case MOCK:
                return new MockKitDao();
            default:
                throw new InvalidParameterException(
                        String.format("Unrecognized dao type creating kit dao: %s",
                                type.toString()));
        }
    }

    public static PartTypeDao getPartTypeDao(DAO_TYPES type) {
        switch (type) {
            case MOCK:
                return new MockPartTypeDao();
            default:
                throw new InvalidParameterException(
                        String.format("Unrecognized dao type creating part type dao: %s",
                                type.toString()));
        }
    }

    public static KitTypeDao getKitTypeDao(DAO_TYPES type) {
        switch (type) {
            case MOCK:
                return new MockKitTypeDao();
            default:
                throw new InvalidParameterException(
                        String.format("Unrecognized dao type creating kit type dao: %s",
                                type.toString()));
        }
    }

    public static PartTypeCategoryDao getPartTypeCategory(DAO_TYPES type) {
        switch (type) {
            case MOCK:
                return new MockPartTypeCategoryDao();
            default:
                throw new InvalidParameterException(
                        String.format("Unrecognized dao type  creating part type category dao: %s",
                                type.toString()));
        }
    }

    public static SessionDao getSessioDao(DAO_TYPES type) {
        switch (type) {
            case MOCK:
                return new MockSessionDao();
            default:
                throw new InvalidParameterException(
                        String.format("Unrecognized dao type creating session dao: %s",
                                type.toString()));
        }
    }

    public static PartInventoryDao getPartInventoryDao(DAO_TYPES type) {
        switch (type) {
            case MOCK:
                return new MockPartInventoryDao();
            default:
                throw new InvalidParameterException(
                        String.format("Unrecognized dao type creating part inventory dao: %s",
                                type.toString()));
        }
    }
}
