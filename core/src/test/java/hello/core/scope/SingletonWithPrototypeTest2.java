package hello.core.scope;

import jakarta.annotation.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

public class SingletonWithPrototypeTest2 {
    /*싱글톤 빈과 프로토타입을 함께 써봄. 여기는 잘못 사용하는 예시임.
    싱글톤 빈 : clientBean
    프로토타입 빈 : PrototypeBean

    전체, 가장 큰 단위는 clientBean이다. 클라이언트(사용자)는 clientBean에 접근하고, clientBean은 여기 내부에서 여러 가지 기능을 처리해야 한다 (구조를 생각하면 당연함.. ).
그 기능들 중에 프토토타입 빈의 기능도 일부로 들어있는 것이다.
 => ClientBean(싱글톤)에서 PrototypeBean을 불러와서 관리해야 하는데,
  ★가져와서 계속 가지고 있으면서 걔(가지고 있는 프로토타입 빈)를 꺼내다만주면★
   프토토타입의 기능을 하지 못한다. 싱글톤과 계속 함께하기에, 싱글톤과 특성이 완전히 같아지는 것이다.
   (당연히 그러지 않도록 코드를 잘 짜주거나, 스프링에서 제공하는 기능을 사용하거나, 자바에서 제공하는 기능을 사용하면 된다.)

     */
    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new
                AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(2);
    }

    //싱글톤 빈(클라이언트 빈)으로 사용할 ClientBean class 추가
    static class ClientBean {

        private final PrototypeBean prototypeBean; //이 싱글톤 빈이 관리할 prototypeBean 필드

        // 생성자 주입(@Autowired)으로 prototypeBean를 자동 DI받는다.
        // => ☆문제! (★하나를 받아놓고, 계속 이것만 쓰기 때문에!)
        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        //logic()을 호출하여 프로토타입 빈을 사용하는 용도의 메소드
        //=> 위에서 문제가 있는 프로토타입빈을 사용하기 때문에, 당연히 자동으로 여기서도 문제가 연쇄적으로 발생.
        //  두 사용자(클라이언트)에 대해 count가 0->1, 0->1이 되어야 하는데, 0->1,  1->2가 되어버리는 것이다!
        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean { //SingletonWithPrototypeTest1에서 했던 것 그대로 가져옴

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
