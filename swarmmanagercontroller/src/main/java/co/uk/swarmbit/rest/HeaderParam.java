package co.uk.swarmbit.rest;

public class HeaderParam {

    private String name;

    private String value;

    public HeaderParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
