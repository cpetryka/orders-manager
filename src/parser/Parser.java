package parser;

public sealed interface Parser<T> permits OrderParser {
    T parse(String expressions);
}