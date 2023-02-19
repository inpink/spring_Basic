package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextBasicFindTest { //[스프링 빈 조회 - 기본]

    /*스프링 컨테이너에서 스프링 빈을 찾는 가장 기본적인 조회 방법
    ac.getBean(빈 이름, 빈 데이터 타입)
    ac.getBean(빈 데이터 타입)
    =>위에서 했던 것. '빈 이름'을 통해 '빈 객체(인스턴스)'를 가져오는 것이다!!!
    => 조회했는데 알맞는 스프링 빈이 없으면 다음과 같은 예외(에러)가 발생한다.
    NoSuchBeanDefinitionException: No bean named 'xxxxx' available

    ApplicationContextInfoTest에서 했던 것에 이어, 스프링 빈을 조회하는 방법을 더 추가해본다.
    test.hello.core 안의 beanfind package안에 ApplicationContextBasicFindTest.java를 추가한다.
    */


    AnnotationConfigApplicationContext ac
            =new AnnotationConfigApplicationContext(AppConfig.class);

    /*테스트해보는 것이다. assertThat을 이용한다.
    getBean을 이용해서 memberService 메소드의 '빈 객체(인스턴스)'가 MemberServiceImple.class인지 검증한다!
    (isInstanceOf(class)는, 해당 class에서 파생된 객체(인스턴스)인지 검사해주는 것이다!) */
    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService memberService = ac.getBean("memberService",MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    /*빈 이름으로 조회하기와 비슷하지만, getBean을 쓸 때 이름을 안적고 반환 데이터 타입만 적어준다.
    MemberService를 반환하는 빈은 memberService메소드밖에 없다. 이것의 반환 객체(빈 객체)가 MemberServiceImpl이 맞으므로 검증 성공한다.
    */
    @Test
    @DisplayName("빈 이름 없이 타입으로만 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }


    /*위에 것들과 비슷하다. 빈 이름은 동일하게 사용하고, 반환 타입을 인터페이스가 아닌 반환 객체 자체 class로 지정해준다.
    동일하게 잘 검증 성공한다.
    (But, 구체 타입으로 조회하면 코드 변경시 유연성이 떨어진다는 특징을 가진다.)
    */
    @Test
    @DisplayName("빈 이름, 구현체(인스턴스) 타입으로 조회")
    void findBeanByName2(){
        MemberService memberService = ac.getBean("memberService",MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    /*예외가 발생하는 경우도 테스트해준다. 없는 빈 이름으로 조회하면(조회된 값이 하나도 없으면) 에러가 뜬다고 했다.
    asserThrows를 이용하여 NoSuchBeanDefinitionException가 뜨는 지 확인해준다. (이 예외가 뜨면 성공으로 판정)
    xxxxx라는 빈 이름은 없다. 따라서! NoSuchBeanDefinitionException 예외가 발생하여 검증 성공한다.
    */
    @Test
    @DisplayName("없는 빈 이름으로 조회하기(예외 발생)")
    void findBeanByNameX(){
        Assertions.assertThrows(NoSuchBeanDefinitionException.class,()->
                ac.getBean("xxxxx", MemberService.class));
    }

    /*한 Test 파일에서 assertThat 과 assertThrows를 동시에 쓰고 싶다면, import를 잘 해줘야 한다.
    import org.junit.jupiter.api.Assertions;
    import static org.assertj.core.api.Assertions.*;
    이 둘을 import 하도록 하자. (각각 쓸거면 원래 하던 대로 편하게 하면 됨..)*/
}
