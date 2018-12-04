package co.uk.swarmbit.docker.cli.model;

public class Constraint {

    private String name;

    private String value;

    private boolean different;

    public Constraint() {

    }

    public Constraint(String name, String value, boolean different) {
        this.name = name;
        this.value = value;
        this.different = different;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isDifferent() {
        return different;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDifferent(boolean different) {
        this.different = different;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Constraint{");
        sb.append("name='").append(name).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append(", different=").append(different);
        sb.append('}');
        return sb.toString();
    }
}
