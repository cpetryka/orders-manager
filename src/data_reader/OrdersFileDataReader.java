package data_reader;

import data_reader.exception.OrdersFileDataReaderException;
import model.Order;
import parser.Parser;
import validator.OrderValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public final class OrdersFileDataReader implements DataReader<Order> {
    @Override
    public List<Order> read(String path, Parser<Order> parser) {
        try(var lines = Files.lines(Path.of(path))) {
            return lines
                    .filter(OrderValidator::validate)
                    .map(parser::parse)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        catch(IOException e) {
            throw new OrdersFileDataReaderException(e.getMessage());
        }
    }
}
