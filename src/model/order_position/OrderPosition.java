package model.order_position;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderPosition {
    private Product product;
    private int quantity;
    private BigDecimal discount;

    private OrderPosition(Product product, int quantity, BigDecimal discount) {
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal totalPrice() {
        return product
                .getPrice()
                .multiply(BigDecimal.valueOf(quantity))
                .multiply(BigDecimal.ONE.subtract(discount));
    }

    @Override
    public String toString() {
        return "%s <-> %d <-> %s".formatted(product, quantity, discount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPosition that = (OrderPosition) o;
        return quantity == that.quantity && product.equals(that.product) && discount.equals(that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, discount);
    }

    public static OrderPosition of(Product product, int quantity, BigDecimal discount) {
        return new OrderPosition(product, quantity, discount);
    }
}
