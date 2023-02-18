package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp { //할인&주문 테스트 : 위에서 했던 것처럼 psvm main을 만들어서 순수 JAVA로 직접 테스트한다. (비추하는 방식)

    public static void main(String[] args) {
        //MemberService와 OrderService 객체와 Member객체를 만들어서 'createOrder'을 테스트함.
        //MemberService memberService = new MemberServiceImpl();
        //OrderService orderService =new OrderServiceImpl();

        //AppConfig를 만들어 줬으므로, 여기서 꺼내 사용
       /* AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();*/

        //스프링 컨테이너 적용. 중복되는 내용이 많아 자세한 설명은 PASS.
        ApplicationContext applicationContext=
                new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService",OrderService.class);

        long memberId=1L;
        Member member=new Member(memberId,"memberA", Grade.VIP);
        memberService.join(member); //당연히 이건 잘 된다고 가정할 수 있어야 함

        //createOrder을 테스트할 것이기 때문임
        Order order= orderService.createOrder(memberId,"itemA",10000);
        //반환값은 Order 객체임. 이를 출력해서 눈으로 직접 확인한다. (앞서 설정해준 toString이 출력됨)

        System.out.println("order = "+order); //출력해서 확인.
        System.out.println("discountPrice = "+order.calculatePrice()); //당연히 특정 값만 따로 출력해서 확인할 수도 있다.
    }
}
