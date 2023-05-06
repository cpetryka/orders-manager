package model.order_position.product_category;

import model.order_position.product_category.exception.ProductCategoryException;

public enum ProductCategory {
    FOOD,
    DRINKS,
    ELECTRONICS,
    CLOTHES,
    BOOKS,
    OTHER;

    public static ProductCategory of(String productCategoryStr) {
        return switch (productCategoryStr) {
            case "FOOD" -> FOOD;
            case "DRINKS" -> DRINKS;
            case "ELECTRONICS" -> ELECTRONICS;
            case "CLOTHES" -> CLOTHES;
            case "BOOKS" -> BOOKS;
            default -> throw new ProductCategoryException("No product category with value %s".formatted(productCategoryStr));
        };
    }
}