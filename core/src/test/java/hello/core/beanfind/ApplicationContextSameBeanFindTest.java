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

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로만 빈 조회할 때, 같은 타입의 스프링 빈이 두 개 이상이면 오류 발생")
    void findBeanByTypeDuplicate(){
        assertThrows(NoUniqueBeanDefinitionException.class,()->
                ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("같은 타입이 둘 이상 있으면, 빈 이름도 같이 지정")
    void findBeanByName(){
        MemberRepository memberRepository
                =ac.getBean("memberRepository1",MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("원하는 타입의 모든 빈을 조회")
    void findAllBeanByType(){
        Map<String,MemberRepository> beansOfType=
                ac.getBeansOfType(MemberRepository.class);

        for (String key : beansOfType.keySet()){
            System.out.println("key = "+key+" value = "+beansOfType.get(key));
        }
        System.out.println("beansOfType = "+beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2); //개수로 검증
    }

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
