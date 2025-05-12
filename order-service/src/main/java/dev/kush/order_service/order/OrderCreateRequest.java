package dev.kush.order_service.order;

public record OrderCreateRequest(long productId, int qty) {
}