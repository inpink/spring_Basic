package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@Configuration //☆자바 설정 파일도 스프링 빈에 올려줘야 하므로, @Configuration을 달아줘야 한다!
@ComponentScan(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION,classes = Configuration.class))
//보통은 AppConfig와 AutoAppConfig 두 개를 같이 쓰지 않기 때문에, @ComponentScan만 적어주면 되지만,
//지금은 이전에 사용했던 AppConfig를 살리기 위해 컴포넌트 스캔 시 exclueFilters라는 기능을 추가한다.
//ComponentScan에서도 스프링 빈에 올려주고, AppConfig에서도 ★★@Bean으로★★ 동일한 걸 스프링 빈에 올려버리기 때문이다!
//exclueFilters로 Configuration.class 어노테이션이 붙은 것은 ☆등록해주지 않는다고 알려줌☆ => ★ @Configuration이 붙은 AppCofig에 있는 것들은 빈에 등록되지 않음!★
//+ 참고로, @Component안에 @Configuration이 포함됨. 그래서 excludeFilters없이 @ComponentScan하면 AppConfig같이 @Configuration에 있는 @Bean이 달려는 메소드들이 빈으로 등록되고, OrderServiceImpl같이 @Component가 붙은 클래스가 또 한번 빈으로 등록되니까 중복 등록이 되어버린다. 이를 막기 위해 excludeFilters 쓴 것.
public class AutoAppConfig {
    //안에는 내용 적어주지 않아도 OK

    /*빈 이름 중복
    수동 빈 등록(@Bean) vs 자동 빈 등록(@ComponentScan)
    ☆스프링☆만 이용하면 에러 안뜨고 수동 빈이 우선순위 가짐.(=수동 빈이 자동 빈 '오버라이딩' 해버림)
    ☆스프링 부트☆ 이용하면 에러가 남.

     MemberRepository memberRepository()  생성자를 추가하고, @Bean을 달아준다.
      @Bean(name = "memoryMemberRepository")을 통해 이름을 수동으로 지정해준다!
    이 자바 설정 파일에는 @ComponentScan이 달려있기 때문에,
    @Component가 달려있는 MemoryMemberRepository.java를 자동으로 빈으로 등록해줬을 것이다!
     ☆ AutoAppConfig 안에서 또 수동으로 @Bean을 이름으로 지정해서 또 등록해버리면,
    수동 빈이 자동 빈을 덮어씌울 것이다.

    test 패키지의 AutoAppConfigTest를 실행해보자!
    ApplicationContext ac = new
                    AnnotationConfigApplicationContext(AutoAppConfig.class);
    ★★이 방식은 Spring을 직접!! 이용하는 것이다!★★
    따라서 overriding 됐다는 문구가 뜨면서 에러가 나진 않는다.

    ☆main으로 와서, @SpringBootApplication가 달려있는 CoreApplication의 main메소드를 실행해보자!
    아래와 같이 에러가 뜨는 것을 확인할 수 있다.
    CoreApplication는 어노테이션때문에 Spring Boot를 쓰고있기 때문이다!

    (+ AutoAppConfig에서 방금 추가한 코드는 에러 발생 가능성이 높기 때문에 주석처리했다.)
     */
   /* @Bean(name="memoryMemberRepository")
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }*/
}
