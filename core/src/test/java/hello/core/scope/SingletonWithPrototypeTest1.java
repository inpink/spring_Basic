package hello.core.scope;

import jakarta.annotation.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.*;

public class SingletonWithPrototypeTest1 {
    //스프링 컨테이너에 프로토타입 빈만 있을 때, 이를 여러 사용자(클라이언트)가 사용했을 때

    @Test
    void prototypeFind() {
        //PrototypeTest과 비슷하게 테스트
        AnnotationConfigApplicationContext ac = new
                AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
        //각각 count가 0->1이 되는지 추가로 검증
    }

    @Scope("prototype")
    static class PrototypeBean { //PrototypeTest의 PrototypeBean 그대로 가져옴
        //각 프로토타입 빈이 호출된 횟수를 addCount()를 통해 count 변수에 담는 기능을 추가로 가지고 있음
        //count 변수를, addCount(), getCount() 추가
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
