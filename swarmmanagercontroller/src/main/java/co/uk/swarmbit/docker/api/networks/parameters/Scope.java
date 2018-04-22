package co.uk.swarmbit.docker.api.networks.parameters;

public enum Scope {
    SWARM,
    GLOBAL,
    LOCAL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
