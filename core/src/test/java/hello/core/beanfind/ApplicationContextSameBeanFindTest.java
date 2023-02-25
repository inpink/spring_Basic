package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.junit.jupiter.api.Assertions;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest { //[스프링 빈 조회 - 동일한 타입이 둘 이상]

    /*
        스프링 컨테이너에서 스프링 빈을 찾을 떄, getBean()을 썼다.
    이 때, 타입으로만 검색할 때, 같은 타입의 스프링 빈이 두 개 이상이면 오류가 발생한다.
    이를 테스트하고, 같은 타입의 모든 스프링 빈을 조회하는 것을 test한다.
     */

    //아래에서 만들어준 SameBeanConfig.class를 설정 클래스로 '스프링 컨테이너'에 등록해준다.
    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(SameBeanConfig.class);


    //- 총 3가지를 테스트한다.
    //1) assertThrows로 같은 타입의 스프링 빈이 있을 때, NoUniqueBeanDefinitionException 에러가 뜨는 지 확인해준다.
    @Test
    @DisplayName("타입으로만 빈 조회할 때, 같은 타입의 스프링 빈이 두 개 이상이면 오류 발생")
    void findBeanByTypeDuplicate(){
        assertThrows(NoUniqueBeanDefinitionException.class,()->
                ac.getBean(MemberRepository.class));
    }


    //기존에 했던 것처럼, getBean에서 빈 이름을 지정해주면 잘 된다. assertThat으로 확인한다.
    @Test
    @DisplayName("같은 타입이 둘 이상 있으면, 빈 이름도 같이 지정")
    void findBeanByName(){
        MemberRepository memberRepository
                =ac.getBean("memberRepository1",MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    // 3) 원하는 타입의 모든 빈을 조회하는 방법이다.
    @Test
    @DisplayName("원하는 타입의 모든 빈을 조회")
    void findAllBeanByType(){
        // getBeansOfType()을 이용해서 Map에 담아준다.
        Map<String,MemberRepository> beansOfType=
                ac.getBeansOfType(MemberRepository.class);

        // for문을 이용해서 map의 key에 담긴 '빈 이름'을 string 형태로 가져와서 출력한다.
        for (String key : beansOfType.keySet()){
            // map의 get(key)기능을 이용해서 value인  '☆인스턴스☆'도 같이 출력한다.
            System.out.println("key = "+key+" value = "+beansOfType.get(key));
        }
        System.out.println("beansOfType = "+beansOfType); //해당 Map을 출력한다.
        assertThat(beansOfType.size()).isEqualTo(2); //asserThat으로, 해당 맵의 사이즈로 검증한다. (총 개수로 검증)
    }

    //☆Test class 안에 SameBeanConfig class를 만든다☆
    //AnnotationConfigApplicationContext 구현체에 설정 클래스를 등록 시, 해당 SameBeanConfig class를 등록해줄 것이다.
    @Configuration
    static class SameBeanConfig{

        @Bean
        public MemberRepository memberRepository1(){
            return  new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2(){
            return  new MemoryMemberRepository();
        }
    }
}
