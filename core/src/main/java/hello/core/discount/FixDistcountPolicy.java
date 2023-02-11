package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class FixDistcountPolicy implements DiscountPolicy{ //구현체로 FixDistcountPolicy class를 만듦. DiscountPolicy 상속

    private int discountFixAmount=1000; //1000원 할인

    @Override
    public int discount(Member member, int price) { //discount 구현
        if (member.getGrade()== Grade.VIP){ //enum은 ==으로 비교할 수 있음
            return discountFixAmount; //1000원 할인
        }
        else{
            return 0; //0원 할인
        }
    }
}
