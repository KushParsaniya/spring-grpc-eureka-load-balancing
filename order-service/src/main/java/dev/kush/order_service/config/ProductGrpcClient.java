package dev.kush.order_service.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import dev.kush.order_service.product.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductGrpcClient {

    private final EurekaClient eurekaClient;
    private final GrpcChannelManager channelManager;

    public ProductServiceGrpc.ProductServiceBlockingStub getStub() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("product-service", false);
        String address = instance.getIPAddr() + ":" + instance.getPort();
        ManagedChannel channel = channelManager.getChannel(address);
        return ProductServiceGrpc.newBlockingStub(channel);
    }
}
