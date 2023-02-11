package hello.core.order;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class OrderServiceTest { //JUnit 이용한 할인&주문 테스트

    //중복되는 내용이 많아서 자세한 설명은 PASS
    MemberService memberService = new MemberServiceImpl();
    OrderService orderService =new OrderServiceImpl();

    @Test
    void createOrder(){
        //given
        long memberId=1L;
        Member member=new Member(memberId,"memberA", Grade.VIP);
        memberService.join(member);

        //when
        Order order= orderService.createOrder(memberId,"itemA",10000);

        //then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}
