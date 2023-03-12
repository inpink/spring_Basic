package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest { //스프링 빈 모두를 조회하기 : Map, List 이용
    //예) 클라이언트가 할인의 종류(rate, fix 등.. 중)를 선택할 수 있어야 한다 => 관련된 스프링 빈 모두(=DiscountPolicy 스프링 빈 모두)가 필요하다!
    //생성자 @Autowired를 이용하여 Map, List으로 자동 주입 받아서 쉽게 구현할 수 있다!
    @Test
    void findAllBean() {
        //스프링 컨테이너에! ★ 두 개의 설정 파일을 등록해준다!! ★ 이렇게 두 개도 등록할 수 있다.
        //하나는 AutoAppConfig.class=> ComponentScan을 통해 필요한 모든 스프링 빈을 등록해주는 main에 있는 자바 설정 파일이었다.
        //DiscountService.class=> 방금 만들어준 자바 설정 클래스로,  DiscountPolicy 정책을 선택하여 할인 금액을 뽑아낼 수 있었다.
        ApplicationContext ac
                =new AnnotationConfigApplicationContext(AutoAppConfig.class,
                DiscountService.class);

        //★getBean을 통해  등록해준(ac) discountService class의 싱글톤 객체(스프링 빈)를 가져온다.★
        DiscountService discountService = ac.getBean(DiscountService.class);

        //테스트를 위해 Member 객체를 하나 임의로 생성해주고,
        Member member = new Member(1L, "userA", Grade.VIP);

        //이 member를 이용하여 방금 만든  discountService.discount에 fixDiscountPolicy로 1만원을 보내본다!
        int discountPrice1= discountService.discount(member,
                15000,"fixDiscountPolicy"); //고정 1000원 할인이니 1000원이 나오면 성공이다.
        //추가적으로  rateDiscountPolicy도 테스트 해줬다.
        int discountPrice2= discountService.discount(member,
                25000,"rateDiscountPolicy"); //10%할인이니 2500원 할인이 나오면 된다.

        //ac.getBean으로 가져온 discountService가 DiscountService.class의 객체인지도 검증해준다.
        Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);

        //assertThat으로 고정 할인 금액 1000원이 나오는지 검증해준다.
        Assertions.assertThat(discountPrice1).isEqualTo(1000);
        //10%할인이니 2500원 할인이 나오면 된다.
        Assertions.assertThat(discountPrice2).isEqualTo(2500);


        //★ 이 test 메소드를 통해 다음 것도 자동으로 테스트할 수 있다!
        // DiscountPolicy Type의 스프링 빈으로 Fix, Rate 2개의 스프링 빈을 제대로 등록해줘야만 에러가 안 뜬다.
        //Rate 하나만 @Component로 스프링컨테이너에 스프링 빈으로 등록해주면, 당연히 Fix를 가져올 수 없으므로 에러 발생.
        //=> @Primary를 Rate에 달아주거나, @Qualifier를 Fix,Rate 둘 다에 달아주고 주입받을 때 @Qualifier를 쓰는 등..
        //둘 다 등록해주고 우선순위나 구분자를 잘 등록해줘야 문제없이 실행 된다 :)

        //(이렇게 총 3개를 테스트해볼 수 있었던  좋은 테스트 class!)
    }

    static class DiscountService { //자바 설정 클래스로 사용될 것

        //이 클래스에서 사용될 Map,List를 선언해준다.
        private final Map<String, DiscountPolicy> policyMap; //value type은 DiscountPolicy
        private final List<DiscountPolicy> policies;


        //해당 클래스의 '생성자'를 만든다!
        //Alt+Enter -> Constructor 쓰면 빠르게 생성 가능
        //생성자이기에 public 안달아줘도 문제 없음 :)
        //★클래스에 생성자가 딱 하나일 경우, @Autowired가 자동으로 붙는다고 했다!
        //★★그리고, Map과 List에 있더라도  DiscountPolicy 스프링 빈이 자동으로 DI된다!!!★★
        //모든 DiscountPolicy 스프링 빈이 Map과 List에 담긴 것이다!  (fixDiscountPolicy , rateDiscountPolicy가 주입된다)
        //☆Map의 key값에는 '스프링 빈 이름'이 담기고, value에는 '스프링 빈'이 담긴다!
        //☆List에는 '스프링 빈'이 담긴다!
        DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        //이 클래스에 discount 일반 메서드도 만든다
        //외부에서 discountservice.discount로 직접 쓸것이기 때문에 당연히 public 필수
        public int discount(Member member, int price, String discountCode){
            //매개변수로  String discountCode를 통해, 해당되는 스프링 빈 이름 정책을 이용하여 Map에서 스프링 빈을 찾아주고,
            //get은 맵에서 discountCode라는 key로 value찾아 반환하는 기능이었음
            DiscountPolicy discountPolicy = policyMap.get(discountCode);

            System.out.println("discountCode = " + discountCode);
            System.out.println("discountPolicy = " + discountPolicy);

            //iscountPolicy.discount를 통해 ★해당 '스프링 빈'을 이용하여!★ 할인 금액을 반환!
            return discountPolicy.discount(member, price);
            //=> 정책 선택 기능을 손쉽게 구현했다.
        }
    }


}
