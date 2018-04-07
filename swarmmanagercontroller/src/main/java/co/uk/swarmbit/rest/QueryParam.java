package co.uk.swarmbit.rest;

public class QueryParam {

    private String name;

    private String value;

    public QueryParam(String name, Object value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
