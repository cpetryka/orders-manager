package model.order_position;

import model.order_position.product_category.ProductCategory;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String name;
    private ProductCategory category;
    private BigDecimal price;

    private Product(String name, ProductCategory category, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name) && category == product.category && price.equals(product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, price);
    }

    @Override
    public String toString() {
        return "PRODUCT [%s; %s; %s]".formatted(name, category, price);
    }

    public static Product of(String name, ProductCategory category, BigDecimal price) {
        return new Product(name, category, price);
    }
}
