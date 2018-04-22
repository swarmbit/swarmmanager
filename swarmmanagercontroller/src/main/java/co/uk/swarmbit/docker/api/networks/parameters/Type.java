package co.uk.swarmbit.docker.api.networks.parameters;

public enum Type {
    CUSTOM,
    BUILTIN;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
