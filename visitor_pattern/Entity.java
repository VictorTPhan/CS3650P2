package visitor_pattern;

public interface Entity {
    void accept(Visitor visitor);
}