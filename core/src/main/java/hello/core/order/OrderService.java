package hello.core.order;

public interface OrderService {//OrderService interface로 주문 서비스 인터페이스 생성. 1개의 기능을 가짐(createOrder)

    Order createOrder(Long memberId, String itemName, int itemPrice);
}
