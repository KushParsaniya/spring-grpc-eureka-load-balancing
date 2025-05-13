package dev.kush.product_service.product;

import dev.kush.order_service.product.ProductRequest;
import dev.kush.order_service.product.ProductResponse;
import dev.kush.order_service.product.ProductServiceGrpc;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    @Override
    public void getProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        try {
            Product product = productService.findProductById(request.getId());

            if (product == null) {
                responseObserver.onError(new EntityNotFoundException("Product not found"));
                return;
            }

            responseObserver.onNext(product.toProductResponse());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new EntityNotFoundException("Product not found"));
        }

    }
}
