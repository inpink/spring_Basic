package hello.core.order;

public class Order { //Member처럼, Order '엔티티'를 만든다!
    // memberId, itemName, itemPrice, discountPrice 4가지 멤버 변수를 가짐.

    private Long memberId;
    private String itemName;
    private int itemPrice;
    private int discountPrice;

    //generate-> Constructor -> 모두선택 으로 생성자 만들고,
    public Order(Long memberId, String itemName, int itemPrice, int discountPrice) {
        this.memberId = memberId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
    }

    //calculatePrice() 메소드를 추가해준다. 할인된 금액을 return해준다.
    public int calculatePrice(){
        return itemPrice - discountPrice;
    }

    //generate-> getter and setter으로 게터 세터도 생성해준다..
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    //generate-> toString()-> 모두선택 으로 출력을 예쁘게 해주기 위해 toString()을 오버라이딩 해준다.
    // ※이는, 다른 곳에서 order 객체를 출력(print)했을 때, toString이 호출되기 때문이다!
    @Override
    public String toString() {
        return "Order{" +
                "memberId=" + memberId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
