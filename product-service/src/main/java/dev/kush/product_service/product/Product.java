package dev.kush.product_service.product;

import dev.kush.order_service.product.ProductResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceCreator;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;

    private String name;

    private int quantity;

    private double price;

    ProductResponse toProductResponse() {
        return ProductResponse.newBuilder()
                .setQuantity(quantity)
                .setPrice(price)
                .build();
    }


    @PersistenceCreator
    public Product(long id, String userId, String name, int quantity, double price) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
