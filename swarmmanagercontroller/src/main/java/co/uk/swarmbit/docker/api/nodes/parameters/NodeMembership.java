package co.uk.swarmbit.docker.api.nodes.parameters;

public enum  NodeMembership {
    ACCEPTED,
    PENDING;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
