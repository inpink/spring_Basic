package hello.core.scope;

import jakarta.annotation.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import jakarta.inject.Provider; //JSR-330 Provider 쓰려면 이 import 필요함

public class PrototypeProviderTest {
    //SingletonWithPrototypeTest2를 해결하는 방법이 3가지 있다.
    //1) 코드를 수정
    //2) 스프링 제공 기능 : ObjectProvider
    //3) 자바 표준 제공 기능 : JSR-330 Provider

    @Test
    void providerTest() {

        AnnotationConfigApplicationContext ac = new
                AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1); //count는 1이 나와야 정상

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1); //count는 1이 나와야 정상
    }

    static class ClientBean {

        /* //1) 코드를 수정한 방식 (비추)
        //SingletonWithPrototypeTest2 코드를 그대로 가져오고,  ClientBean 부분이랑 assertThat 부분만 조금 바꿔줌
        //우선 ClientBean에서, 당연하지만.. 위에서 한것처럼 프로로타입 빈을 자동 주입받으면 안된다. 싱글톤으로 관리되기 때문임.

        //ApplicationContext ac에 @Autowired해서 필드 주입 방식으로 스프링 컨테이너를 DI받아서 스프링 컨테이너 자체는 싱글톤으로 사용한다!
        @Autowired
        private ApplicationContext ac;


        public int logic() {
            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class); //☆매번 프토로타입 빈을 받아온다☆
            // (위에서도 설명했지만, getBean은 스프링 컨테이너에 있는 빈을 찾아오는데,
            //PrototypeBean 클래스는 @Scope("prototype")이 달려있기 때문에 매번 새로운 객체를 만들어 반환해주기 때문이다.)
            prototypeBean.addCount(); //각 클라이언트(사용자)마다 0->1, 0->1이 잘 된다.
            int count = prototypeBean.getCount();
            return count;
        }

        //이는 DI받는 것이 아니라, 'DL(Dependency Lookup,의존관계 탐색)'이다!
        //But, @Autowired ApplicationContext ac 때문에 스프링 컨테이너에 종속적이고, 너무 의존하는 코드가 되어버린다.
        // => 단위 테스트가 어려워진다. 비추천하는 방식.
         */


        /*//2)  스프링 제공 기능 :  ObjectProvider  => 지정한 빈을 스프링 컨테이너에서 대신 찾아주는 'DL 기능'을 제공한다.

        //ObjectProvider<프로토타입으로 사용할 빈Type> 를 @Autowired로 주입받고,
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;

        public int logic(){
        //prototypeBeanProvider.getObject()로 프로토타입 빈을 ☆매번 DL하여(=스프링 컨테이너에서 찾아서 새로운 객체로 만들어서) 반환받을 수 있다!☆
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }

        //=> 스프링에 의존하는 방식이다.
        //=> 스프링 컨테이너에 종속적인 방법이 아니라, 스프링 테스트 등으로 단위테스트를 만들기는 훨씬 쉬워진다.

        */


        //3) 자바 표준 제공 기능 : JSR-330 Provider => jakarta.inject.Provider 라이브러리를 이용하는 방식.

        //이를 사용하기 위한 환경설정이 필요하다.
        //스프링부트 3.0 이상 버전에서는 gradle에 dependencies 안에 implementation 'jakarta.inject:jakarta.inject-api:2.0.1'를 추가해야 한다.
        //코드 사용 시, import jakarta.inject.Provider;가 필요하다.


        //사용은 2번과 비슷한 방식
        @Autowired
        private Provider<PrototypeBean> provider;

        public int logic() {
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }

        //=> 스프링에 의존하지 않는 방식이다.
        //=> 단위 테스트를 만들기에 좋다.
        //=> But, 환경설정을 따로 해줘야 한다.


        /*

★결론 ====>
 - 실무에서는 프로토타입 빈을 사용할 일이 애초에 거의 없다. 대~부분 싱글톤 빈을 사용한다.
  하지만 가끔 사용해야할 경우 위의 방법 중, ObjectProvider나 JSR330 Provider를 사용하자.
  ☆가급적이면 스프링이 자바 표준보다는 더 편리한 기능들을 추가해서 제공해주기 때문에,
   둘 중 스프링에서 제공하는 ObjectProvider를 쓴다.
   하지만, 정말 거의 그럴 일은 없지만 다른 컨테이너에서도 이 코드를 사용해야 한다면 JSR330 Provider를 쓰자.
 - 프로토타입 빈을 사용하지 않더라도, 언제라도 DL이 필요한 경우 ObjectProvider나 JSR330 Provider를 써줄 수 있다.
         */
    }

    @Scope("prototype")
    static class PrototypeBean {

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
