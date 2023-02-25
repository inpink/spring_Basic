package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {

    //test 폴더에서, ★싱글톤 없이 스프링 없이 순수한 DI 컨테이너★를 이용하여, 클래스를 호출할 때마다 객체가 계속해서 생성되고 있다는 것을 확인해보자.

    @Test
    @DisplayName("싱글톤 없는, 스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){

        //싱글톤 없이, 스프링 없이 해볼 것이므로 AppConfig.java를 그대로 가져와서 사용한다.
        // (이전 포스팅에서 했던것처럼 AnnotationConfig.. 안쓰고)
        AppConfig ac=new AppConfig();

        //appConfig에서 memberService를 두 번 꺼내보자.
        MemberService memberService1= ac.memberService();
        MemberService memberService2= ac.memberService();

        //출력해보면, 서로 다른 인스턴스가 출력되는 것을 확인할 수 있다.
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        //asserThat, isNotSameAs으로 이 두 객체가 다른 지 비교해보자!
        //당연히 서로 다른 객체가 반환되었다는 걸 확인할 수 있다!
        //=> 우리가 만들었던 싱글톤 없는, 스프링 없는 순수한 DI 컨테이너인 AppConfig는, 요청을 받을 때 마다 새로운 객체를 만들어서 반환한다.
        //=> 고객 트래픽이 초당 100개가 들어오면, 초당 100개의 객체가 생성되고 소멸된다! 메모리, 시간 낭비가 심하다.
        //===> 이를 해결하기 위해 나온 것이 '싱글톤'이다. 싱글톤 패턴은,
        // 동일한 객체가 여러 개 생성되지 않고, 단 하나만 생성해둔 뒤, 모든 클래스에서 같은 하나의 인스턴스를 공유할 수 있다!
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }


    //방금 만든 SingletonService 클래스를 사용하는 테스트 코드를 작성해보자.
    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 생성 테스트")
    public void singletonServiceTest(){

        //private를 이용하여 생성자 생성을 막아두었기 때문에 에러 발생
        //SingletonService singletonService = new SingletonService();

        //SingletonService에서 직접 getInstance()를 통해 2개의 객체를 받아온다.
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        //참조값이 같은 지 확인
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        //asserThat으로, ☆두 객체가 같은지(isSameAs)☆ 검증한다. 잘 성공한다.
        //=>호출할 때 마다 같은 객체 인스턴스를 반환하는 것을 확인할 수 있다.
        Assertions.assertThat(singletonService1).isSameAs(singletonService2);

        //그냥 출력
        singletonService1.logic();
    }
}
