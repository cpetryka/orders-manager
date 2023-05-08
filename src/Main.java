import parser.OrderParser;
import service.OrderService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        final var PATH = "src\\data\\orders.txt";
        var ordersService = new OrderService(PATH, new OrderParser());

        System.out.println("--------------------------- A ---------------------------");
        var filteredOrders = ordersService.getOrders(order -> order.totalPrice().compareTo(BigDecimal.valueOf(1000)) > 0);
        filteredOrders.forEach(System.out::println);

        System.out.println("--------------------------- B ---------------------------");
        var cheapestOrders = ordersService.getCheapestOrders();
        cheapestOrders.forEach(System.out::println);

        System.out.println("--------------------------- C ---------------------------");
        var mostExpensiveOrders = ordersService.getMostExpensiveOrders();
        mostExpensiveOrders.forEach(System.out::println);

        System.out.println("--------------------------- D ---------------------------");
        var averagePrice = ordersService.getAveragePrice();
        System.out.println(averagePrice);

        System.out.println("--------------------------- E ---------------------------");
        var maxDiscount = ordersService.getMaxDiscount();
        System.out.println(maxDiscount);

        System.out.println("--------------------------- F ---------------------------");
        var averageDiscount = ordersService.getAverageDiscount();
        System.out.println(averageDiscount);

        System.out.println("--------------------------- G ---------------------------");
        var productsStatistics = ordersService.getProductsStatistics();
        System.out.println(productsStatistics);

        System.out.println("--------------------------- H ---------------------------");
        var categoriesStatistics = ordersService.getCategoriesStatistics();
        System.out.println(categoriesStatistics);

        System.out.println("--------------------------- I ---------------------------");
        var partitionByPrice = ordersService.partitionByAveragePrice();
        System.out.println(partitionByPrice);

        System.out.println("--------------------------- J ---------------------------");
        var totalSpentMoneyByCategory = ordersService.getTotalSpentMoneyByCategory();
        System.out.println(totalSpentMoneyByCategory);
    }
}