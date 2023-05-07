package data_reader;

import parser.Parser;

import java.util.List;

public sealed interface DataReader<T> permits OrdersFileDataReader {
    List<T> read(String path, Parser<T> parser);
}
