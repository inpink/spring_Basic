package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

//[새로운 할인 정책 개발]
//'기존 VIP 1000원 할인 -> VIP 10%할인' 변경됨.
//이전 포스팅에서 '객체 지향 설계 원칙 SOLID'를 잘 지키면서 설계했다면, 이 변동에 유연하게 대처할 수 있어야 한다.
public class RateDiscountPolicy implements DiscountPolicy{

    private  int discountPercent=10;
    @Override
    public int discount(Member member, int price) { //discount를 구현한다. 10%할인으로 구현한다.
        if (member.getGrade()== Grade.VIP){ //☆인터페이스의 member객체 사용!! VIP grade면
            return price*discountPercent/100; //10%의 할인 금액 반환
        }else{
            return 0; //아니면 0원 할인
        }
    }
}
