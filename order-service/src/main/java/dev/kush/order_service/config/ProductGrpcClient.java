package dev.kush.order_service.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import dev.kush.order_service.product.ProductServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductGrpcClient {

    private final EurekaClient eurekaClient;
    private final GrpcChannelFactory grpcChannelFactory;

    public ProductServiceGrpc.ProductServiceBlockingStub getStub() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("product-service", false);
        String address = instance.getIPAddr() + ":" + instance.getPort();
        return ProductServiceGrpc.newBlockingStub(grpcChannelFactory.createChannel(address));
    }
}
