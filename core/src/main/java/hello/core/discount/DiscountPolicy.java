package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy { //DiscountPolicy interface. 1가지 기능.

    /*
    얼마를 할인할 것인지를 return
     */
    int discount(Member member, int price);
}
