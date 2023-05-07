package parser;

import model.Order;
import model.order_position.OrderPosition;
import model.order_position.Product;
import model.order_position.product_category.ProductCategory;

import java.math.BigDecimal;

public final class OrderParser implements Parser<Order> {
    @Override
    public Order parse(String expressions) {
        var order = Order.of();

        var positions = expressions.split("\\|");

        for(var position : positions) {
            var positionData = position.split(";");

            order.addPosition(OrderPosition.of(
                    Product.of(
                            positionData[0],
                            ProductCategory.of(positionData[1]),
                            new BigDecimal(positionData[2])
                    ),
                    Integer.parseInt(positionData[3]),
                    new BigDecimal(positionData[4])
            ));
        }

        return order;
    }
}