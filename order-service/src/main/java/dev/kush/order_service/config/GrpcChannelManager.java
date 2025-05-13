package dev.kush.order_service.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GrpcChannelManager {

    // for multiple services use redis or any other distributed cache
    private final Map<String, ManagedChannel> channelCache = new ConcurrentHashMap<>();

    public ManagedChannel getChannel(String target) {
        return channelCache.computeIfAbsent(target, t ->
            ManagedChannelBuilder.forTarget(t)
                                 .usePlaintext() // Or TLS
                                 .build()
        );
    }
}
