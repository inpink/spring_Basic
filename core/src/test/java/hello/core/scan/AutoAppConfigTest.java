package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {
    //컴포넌트 스캔, AutoWired가 잘 되고 있는지 테스트한다.
    @Test
    void basicScan() {
        //☆AutoAppConfig를 자바 설정 파일로 스프링 빈에 올려주고☆
        ApplicationContext ac = new
                AnnotationConfigApplicationContext(AutoAppConfig.class);

        //ac.getBean으로 memberService가 스프링 빈에 잘 등록되어있는지 확인한다.
        //( AutoAppConfig.java에서 @ComponentScan으로 인해, memberServiceImpl이 잘 등록된 것을 확인할 수 있다!)
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);

        //추가적으로 2개를 더 확인해보았다. OrderServiceImpl, MemberRepository.
        //@Component를 붙여준 OrderServiceImpl이 잘 등록되었는지 검증한다. =>OK
        OrderServiceImpl orderService = ac.getBean(OrderServiceImpl.class);
        Assertions.assertThat(orderService).isInstanceOf(OrderService.class);

        //orderService에서 사용되는 memberRepository가, 스프링 빈에 등록된 memberRepository와 같은지 검증한다!=>OK
        MemberRepository memberRepository1 = orderService.getMemberRepository();
        MemberRepository memberRepository2 = ac.getBean(MemberRepository.class);
        Assertions.assertThat(memberRepository1).isSameAs(memberRepository2);
        //★getMemberRepository()으로 직접 접근해주려면, 위에서 OrderService가 아닌 OrderServiceImpl을 써야 한다!
        //당연히 getMemberRepository() 안쓰면 OrderService로 해도 OK임!!!
    }
}
