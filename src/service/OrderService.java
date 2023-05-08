package service;

import data_reader.OrdersFileDataReader;
import model.Order;
import parser.Parser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Predicate;

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
}
