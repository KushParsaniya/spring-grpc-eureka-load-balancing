package dev.kush.order_service.order;

import dev.kush.order_service.grpc.TokenCallCredentials;
import dev.kush.order_service.product.ProductRequest;
import dev.kush.order_service.product.ProductResponse;
import dev.kush.order_service.product.ProductServiceGrpc;
import dev.kush.order_service.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    public long createOrder(OrderCreateRequest orderCreateRequest) {
        var userId = UserUtils.getCurrentUserId();
        ProductResponse product;
        try {
            product = productServiceBlockingStub
                    .withCallCredentials(new TokenCallCredentials(UserUtils.getJwtToken()))
                    .getProduct(ProductRequest.newBuilder()
                            .setId(orderCreateRequest.productId())
                            .setUserId(userId)
                            .build());
        } catch (Exception e) {
            throw new IllegalArgumentException("Product not found");
        }

        if (product.getQuantity() < orderCreateRequest.qty()) {
            throw new IllegalArgumentException("Insufficient product quantity");
        }
        Order order = new Order();
        order.setProductId(orderCreateRequest.productId());
        order.setQuantity(orderCreateRequest.qty());
        order.setTotalPrice(product.getPrice() * orderCreateRequest.qty());
        order.setUserId(userId);
        return orderRepository.save(order).getId();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
