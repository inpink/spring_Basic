package hello.core.scan.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import static org.assertj.core.api.Assertions.assertThat;

public class ComponentFilterAppConfigTest {

    //BeanA와 BeanB가 스프링 컨테이너 빈으로 등록되었는지 확인
    @Test
    void filterScan(){
        ApplicationContext ac
                = new AnnotationConfigApplicationContext(
                        ComponentFilterAppConfig.class);

        BeanA beanA=ac.getBean("beanA", BeanA.class); //beanA는 getBean으로 가져와서
        assertThat(beanA).isNotNull(); //NULL이 아닌지 검증

        //beanB는 getBean을 했을 때, assertThrows로 NoSuchBeanDefinitionException 예외가 뜨는 지 확인한다!
        Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                ()->ac.getBean("beanB", BeanB.class));

    }

    //설정 파일로 사용할 class
    //자바 설정 파일로 사용될 것이기 때문에 @Configuration을 달음
    //또, 컴포넌트를 스캔하라는 명령으로 @ComponentScan을 달아준다.
    //☆이 때, filter기능도 추가해준다.
    //컴포넌트 스캔 하다가 ★ @MyIncludeComponent이 붙은 모든 클래스 ★를 컴포넌트로 등록해주고,
    //컴포넌트 스캔 하다가 ★ @MyExcludeComponent가 붙은 모든 클래스를 컴포넌트 스캔에서 제외한다.★
    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
            classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
            classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig{

    }
}
