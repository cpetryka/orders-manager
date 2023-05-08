package service;

import data_reader.OrdersFileDataReader;
import model.Order;
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
}
