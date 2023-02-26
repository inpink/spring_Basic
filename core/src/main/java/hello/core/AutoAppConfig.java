package hello.core;

import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

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
}
