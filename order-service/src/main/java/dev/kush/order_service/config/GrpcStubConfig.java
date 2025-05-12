package dev.kush.order_service.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import dev.kush.order_service.product.ProductServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
@RequiredArgsConstructor
public class GrpcStubConfig {

    private final EurekaClient eurekaClient;

    @Bean
    ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub(GrpcChannelFactory factory) {
        return ProductServiceGrpc.newBlockingStub(factory.createChannel(getNextProductHost()));
    }


    String getNextProductHost() {
        final InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka("product-service", false);
        return nextServerFromEureka.getIPAddr() + ":" + nextServerFromEureka.getPort();
    }
}
