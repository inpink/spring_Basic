package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new
                AnnotationConfigApplicationContext(AppConfig.class);

        //★ac.getBean()으로, MemberServiceImpl,  OrderServiceImpl, MemberRepository 3가지의 각 객체를 가져온다!
        MemberServiceImpl memberService = ac.getBean("memberService",
                MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService",
                OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository",
                MemberRepository.class);

        System.out.println("memberService -> memberRepository = " +
                memberService.getMemberRepository());
        System.out.println("orderService -> memberRepository = " +
                orderService.getMemberRepository());
        System.out.println("memberRepository = " + memberRepository);

        //각 3가지 객체에서, getMemberRepository()를 써본다.
        //=> assertThat으로 검증하면,
        // 3가지 객체에서 사용되는 memberRepository는 완전히 같은, 동일한 인스턴스라는 것을 확인할 수 있다!
        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new
                AnnotationConfigApplicationContext(AppConfig.class);

        //AppConfig도 스프링 빈으로 등록된다.
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        //출력: bean = class hello.core.AppConfig$$SpringCGLIB$$0
    }
}
