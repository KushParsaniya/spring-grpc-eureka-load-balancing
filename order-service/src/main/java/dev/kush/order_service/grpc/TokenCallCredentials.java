package dev.kush.order_service.grpc;

import dev.kush.order_service.utils.UserUtils;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

import java.util.concurrent.Executor;

public class TokenCallCredentials extends CallCredentials {

    private final String jwtToken;

    private static final Metadata.Key<String> AUTHORIZATION_HEADER =
            Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

    public TokenCallCredentials(String jwtToken) {
        this.jwtToken = jwtToken;
    }


    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        appExecutor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                headers.put(AUTHORIZATION_HEADER, jwtToken);
                applier.apply(headers);
            } catch (Exception e) {
                applier.fail(Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e));
            }
        });
    }
}
