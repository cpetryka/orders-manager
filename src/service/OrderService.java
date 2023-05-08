package service;

import data_reader.OrdersFileDataReader;
import model.Order;
import model.order_position.OrderPosition;
import model.order_position.Product;
import model.order_position.product_category.ProductCategory;
import parser.Parser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrderService {
    private List<Order> orders;

    public OrderService(String path, Parser<Order> parser) {
        orders = new OrdersFileDataReader().read(path, parser);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Order> getOrders(Predicate<Order> predicate) {
        return orders.stream()
                .filter(predicate)
                .toList();
    }

    public List<Order> getCheapestOrders() {
        return orders
                .stream()
                .collect(Collectors.groupingBy(Order::totalPrice))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByKey())
                .orElseThrow()
                .getValue();
    }

    public List<Order> getMostExpensiveOrders() {
        return orders
                .stream()
                .collect(Collectors.groupingBy(Order::totalPrice))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByKey())
                .orElseThrow()
                .getValue();
    }

    public BigDecimal getAveragePrice() {
        return orders.stream()
                .map(Order::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(orders.size()), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMaxDiscount() {
        return orders.stream()
                .map(Order::totalDiscount)
                .max(BigDecimal::compareTo)
                .orElseThrow();
    }

    public BigDecimal getAverageDiscount() {
        return orders.stream()
                .map(Order::totalDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(orders.size()), 2, RoundingMode.HALF_UP);
    }

    public Map<Product, Long> getProductsStatistics() {
        return orders.stream()
                .flatMap(order -> order.getPositions().stream())
                .collect(Collectors.groupingBy(
                        OrderPosition::getProduct,
                        Collectors.counting()
                ));
    }

    public Map<ProductCategory, Long> getCategoriesStatistics() {
        return orders.stream()
                .flatMap(order -> order.getPositions().stream())
                .collect(Collectors.groupingBy(
                        orderPosition -> orderPosition.getProduct().getCategory(),
                        Collectors.counting()
                ));
    }

    public Map<Boolean, List<Order>> partitionByAveragePrice() {
        var averagePrice = getAveragePrice();

        return orders
                .stream()
                .collect(Collectors.partitioningBy(order -> order.totalPrice().compareTo(averagePrice) > 0));
    }

    public Map<ProductCategory, BigDecimal> getTotalSpentMoneyByCategory() {
        return orders
                .stream()
                .flatMap(order -> order.getPositions().stream())
                .collect(Collectors.groupingBy(
                        orderPosition -> orderPosition.getProduct().getCategory(),
                        Collectors.mapping(
                                orderPosition -> orderPosition.getProduct().getPrice().multiply(BigDecimal.ONE.subtract(orderPosition.getDiscount())),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
    }
}
