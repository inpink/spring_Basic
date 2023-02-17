package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class OrderServiceTest { //JUnit 이용한 할인&주문 테스트

    // ApplConfig를 추가해 줬으므로, 여기서도 수정해줘야함.
    MemberService memberService; //선언만 하고
    OrderService orderService ;

    //@BeforeEach(테스트 실행하기 전에 매번 하는 것)를 이용해서, 마찬가지로 appConfig로부터 이를 주입받는다.
    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig= new AppConfig();
        memberService= appConfig.memberService();
        orderService= appConfig.orderService();
    }

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
