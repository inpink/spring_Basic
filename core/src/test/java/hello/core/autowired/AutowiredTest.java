package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import javax.swing.text.html.Option;
import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption(){
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean{

        //매개변수에 주입받을 스프링 빈이 없으면 아래 수정자가 호출자체가 안됨!
        //여기서도 호출이 되지 않고 있는 것을 알 수 있다!
        @Autowired(required = false)
        public void setNoBean1(Member member) { //수정자 방식으로 자동 DI받기
            //Member는 @Component 등으로 스프링 빈에 등록되어있지 않으므로
            // 원래는 @Autowired를 했을 때 주입받을 빈이 없어 오류가 떠야 하지만 required=false를 통해 오류 없이 호출자체를 안해버림
            System.out.println("setNoBean1 = " + member); //호출 X
        }

        //여기서도 수정자 방식, @Nullable을 통해 자동으로 DI받을 빈이 없으면, null이 주입된다.
        @Autowired
        public void setNoBean2(@Nullable Member member){
            System.out.println("setNoBean2 = " + member); //null 출력됨
        }

        //여기서도 수정자 방식, Optional을 통해 자동으로 DI받을 빈이 없으면,  Optional.empty가 주입된다.
        @Autowired
        public void setNoBean3(Optional<Member> member){
            System.out.println("setNoBean3= " + member);  // Optional.empty 출력됨
        }

    }
}
