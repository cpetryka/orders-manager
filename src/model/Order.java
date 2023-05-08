package model;

import model.order_position.OrderPosition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Order {
    private List<OrderPosition> positions;

    private Order(List<OrderPosition> positions) {
        this.positions = positions;
    }

    public void addPosition(OrderPosition position) {
        positions.add(position);
    }

    public List<OrderPosition> getPositions() {
        return positions;
    }

    public BigDecimal totalPrice() {
        return positions
                .stream()
                .map(OrderPosition::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal totalDiscount() {
        return positions
                .stream()
                .map(position -> position.getProduct().getPrice().multiply(position.getDiscount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return positions.equals(order.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions);
    }

    @Override
    public String toString() {
        var sj = new StringJoiner("\n");
        sj.add("------> ORDER (total price: " + totalPrice() + ")");

        for (var position : positions) {
            sj.add(position.toString());
        }

        return sj.toString();
    }

    public static Order of() {
        return new Order(new LinkedList<>());
    }

    public static Order of(List<OrderPosition> positions) {
        return new Order(positions);
    }
}
