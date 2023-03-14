package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test //test method
    public void prototypeBeanFind() {

        //PrototypeBean.class을 자바 설정 클래스로 스프링 빈에 등록

        ConfigurableApplicationContext ac
                = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        //ac.getBean을 통해 ☆PrototypeBean.class☆의 빈을 찾아온다!
        //이 클래스의 빈에는 @PostConstruct와 @PreDestroy가 달려있기 때문에, 출력값을 통해
        // 초기화 직후 시점, 소멸 직전 시점에 스프링 컨테이너가 관리하고 있는 지 아닌 지 알 수 있다!

        //☆두 번 불러와서, 싱글톤처럼 같은 객체를 사용하는 지 확인한다.
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        //프로토타입 빈은 매번 새로운 객체를 만들어 반환한다. isNotSameAs로 두 객체가 다른 지 검증한다.
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        //★이 프로토타입 빈을 닫아주고 싶다면, 그것은 '클라이언트'의 몫이라고 했다.
        // prototypeBean1.destroy()을 통해 클라이언트가 직접 닫아줄 수 있다.
        //prototypeBean1.destroy();
        //prototypeBean2.destroy();

        ac.close(); //해당 ac 컨테이너 자체를 닫아주는 close()를 적어준다.
        //당연한 말이지만, close는 컨테이너 자체를 닫아주는 것이니 잘 작동한다.
        //init()은 잘 작동하고, close로 컨테이너 자체가 닫혔음에도, destroy()는 호출되지 않는다.
        //'빈 초기화 콜백'인 @PostConstruct는 작동하지만,
        // 초기화 이후 스프링 컨테이너는 이 빈을 관리하지 않으므로 ☆@PreDestroy는 호출자체가 되지 않는 것이다!
    }


    /*자바 설정 클래스로 사용할 PrototypeBean class
     @Scope("prototype")을 달아서, ★PrototypeBean class를!! 스프링 '프로토타입 빈'으로 등록해준다!★
    위에서  new AnnotationConfigApplicationContext(PrototypeBean.class);을 통해
    자바 설정 클래스로 등록해주는 과정에서, ★여기에서 @Bean으로 메소드들을 수동 빈으로 등록해줄 수도 있었지만
    ☆이 클래스 자체도 빈으로 등록된다☆고 배웠다!
    원래는 default로 싱글톤 빈으로 등록이 되겠지만,@Scope("prototype")을 통해 프로토타입 빈으로 등록해준 것이다.
     */
    @Scope("prototype")
    static class PrototypeBean{

        // 내부 메소드 init()과 destroy()
        // @PostConstruct와 @PreDestroy를 달아준다.
        // 각각 "빈 초기화 콜백"과 "빈 소멸 콜백"

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
