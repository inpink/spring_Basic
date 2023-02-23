package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest { //[스프링 빈 조회 - 상속 관계 이용]

    /*
    부모의 type으로 빈을 조회하면, ☆그 부모의 모든 자식의 type의 빈☆이 다 출력된다.
    =>그래서 모~든 자바 객체의 최고 부모인 'Object 타입'으로 조회하면, '모든 스프링 빈'을 조회할 수 있다.

     */


    //- AnnotationConfigApplicationContext로 스프링 컨테이너에 TestConfig.class를 등록해주고,
    AnnotationConfigApplicationContext ac = new
            AnnotationConfigApplicationContext(TestConfig.class);


    //총 5개를 테스트해준다.

    //DiscountPolicy의 '자식 빈'은 RateDiscountPolicy()와 FixDiscountPolicy() 두 가지가 있다.
    // 따라서 에러가 발생한다. assertThrows로 에러가 발생하는지 검증한다.
    @Test
    @DisplayName("부모 '타입으로만' 조회 시, 자식이 둘 이상 있으면 중복 오류가 발생")
    void findBeanByParentTypeDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class, () ->
                ac.getBean(DiscountPolicy.class));
    }

    //위에서 했던 것과 마찬가지로 이름을 지정해주면 아무 문제 없다.
    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면 빈 이름을 지정하면 된다")
    void findBeanByParentTypeBeanName() {
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy",
                DiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    //당연히 등록된 RateDiscountPolicy타입의 빈은 하나밖에 없으니 문제없이 잘 실행된다
    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType() {
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    //위에서 했던 것과 마찬가지로 getBeansOfType, Map을 이용한다. 중복되 부분이 너무 많아서 간단히 PASS
    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType() {
        Map<String, DiscountPolicy> beansOfType =
                ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value=" +
                    beansOfType.get(key));
        }
    }

    //'스프링에 등록된 모든 빈'이 출력된다!
    // 스프링 내부에서 쓰는 빈과, 내가 등록해준 RateDiscountPolicy()와 FixDiscountPolicy() 빈이 출력된다.
    @Test
    @DisplayName("Object라는 모든 클래스의 부모 타입으로 모든 빈 조회하기 ")
    void findAllBeanByObjectType() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        System.out.println("Object라는 모든 클래스의 부모 타입으로 모든 빈 조회하기 ");
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value=" +
                    beansOfType.get(key));
            //(*이렇게 System.out.println으로 출력하는 것은, 테스트하고 바로바로 주석처리 해주는 것이 좋다.
            // 이것들이 쌓기에 되면 나중에 너무 많아져서 개발이 힘들어짐.)
        } System.out.println("\n");
    }

    //위에서 했던 것과 동일하게, 이 class 내부에 설정 클래스로 사용할 TestConfig class를 만들어준다. '빈'은 2개 등록해주는데,
    // DiscountPolicy타입의 자식인 RateDiscountPolicy()와 FixDiscountPolicy()를 각각 반환한다.
    @Configuration
    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }
        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
