package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component //스프링은, @Component 어노테이션이 붙은 클래스를 스프링 빈으로 등록한다!
@Qualifier("fixDiscountPolicy") // DiscountPolicy Type의 스프링 빈으로 Fix, Rate 2개 둘 다 스프링 빈으로 등록시켜줬다!
//Rate에 @Primary를 붙여 우선권을 준 상태이긴 하다!
//☆ 둘 다 등록되어 있기 때문에, 클라이언트가 DiscountPolicy 정책 선택이 가능하도록 쉽게 구현할 수 있다!(AllBeanTest 참고)
public class FixDiscountPolicy implements DiscountPolicy{ //구현체로 FixDistcountPolicy class를 만듦. DiscountPolicy 상속

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
