package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class StatefulServiceTest {
    //StatefulService에서 공유 필드를 수정함으로써 발생하는 문제를 테스트해봄

    @Test
    void statefulServiceSingleton(){

        //방금 만든 자바 설정 클래스를 스프링 컨테이너에 올려주고,
        ApplicationContext ac
                = new AnnotationConfigApplicationContext(TestConfig.class);

        //ac.getBean을 이용해서 2개의 StatefulService 구현체를 받아온다!
        //☆여기서 ,당연히 2개의 구현체는 같을 것이다!☆
        StatefulService statefulService1
                = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2
                = ac.getBean("statefulService", StatefulService.class);

        //그래서, 1번 객체를 이용하여 order메소드를 이용하여 price값을 바꿔주고, 2번 객체도 이용하여 order메소드를 이용하여 price값을 바꿔주면,
        //★결국 1번객체에서 바꿔줬던 값은 사라지는 것이다★
        statefulService1.order("userA",10000); //ThreadA: 사용자A가 10000원 주문한 코드가 도착함
        statefulService2.order("userB",20000); //ThreadB: 사용자B가 20000원 주문한 코드가 도착함

        int price=statefulService1.getPrice();
        System.out.println("price = " + price);

        //2번 객체에서 바꿔준 값으로 바뀌었는지 검증함.
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

        //=> 실제 상황에서는 여러 개의 '쓰레드'가 이러한 방식으로 접근하게 된다.
        //ThreadA가 사용자 A의 코드를 호출하고, ThreadB가 사용자 B의 코드를 호출했을 때,
        // 공유 필드인 price때문에 문제가 발생할 수 있다.
        // (실무에서도 매우 큰 문제가 되기 때문에,)
        // ★스프링 빈은 항상 무상태(stateless)로 설계해야 한다!★
    }


    //이 클래스 내부에, 자바 설정 파일로 스프링 컨테이너에 올려줄 TestConfig class를 만든다!
    // (StatefulService를 담당하는 자바 설정 파일이 없기 때문에,
    // 스프링 컨테이너에 빈을 올려서 테스트하려면 당연히 만들어줘야 한다)
    @Configuration
    static  class TestConfig{

        //statefulService 빈은 StatefulService ★싱글톤 객체★를 반환한다.
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }


}
