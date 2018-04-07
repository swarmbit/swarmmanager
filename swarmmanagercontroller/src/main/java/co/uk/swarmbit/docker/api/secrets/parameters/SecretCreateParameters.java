package co.uk.swarmbit.docker.api.secrets.parameters;

import co.uk.swarmbit.docker.api.common.parameters.RequestBodyParameter;
import co.uk.swarmbit.docker.api.common.json.SecretSpecJson;

public class SecretCreateParameters implements RequestBodyParameter {

    private SecretSpecJson secret;

    public SecretCreateParameters() {
        secret = new SecretSpecJson();
    }

    public SecretCreateParameters setSecret(SecretSpecJson secret) {
        this.secret = secret;
        return this;
    }

    @Override
    public Object getRequestBody() {
        return secret;
    }
}
