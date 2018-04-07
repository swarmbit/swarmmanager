package co.uk.swarmbit.docker.cli.model;

public class BindMountOptions {

    private String propagation;

    public String getPropagation() {
        return propagation;
    }

    public void setPropagation(String propagation) {
        this.propagation = propagation;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BindMountOptions{");
        sb.append("propagation='").append(propagation).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
