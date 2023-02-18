package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    //AnnotationConfigApplicationContext 객체 ac를 만든다. (이는 스프링컨테이너의 구현체였음)
    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(AppConfig.class);

    //@Test 어노테이션을 달아주고, 모든 빈을 출력하는 테스트를 위해 findAllBean()메소드를 작성해준다.
    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean(){
        //ac.getBeanDefinitionNames()를 통해 '모든 빈을 가져온다.' String 배열에 담아준다.
        String[]  beanDefinitionNames = ac.getBeanDefinitionNames();

        //해당 String[] 배열(beanDefinitionNames)에서 하나씩 꺼내서  beanDefinitionName라는 String형 변수에 저장한다는 뜻
        for (String beanDefinitionName :  beanDefinitionNames){
            Object bean = ac.getBean(beanDefinitionName); //getBean은, 매개변수로 넘겨준 값을 통해서 'Bean 객체(인스턴스)'를 조회하여 반환해준다.
            //이 반환된 빈 객체를 Object형태에 저장하고,

            //'빈 이름(AppConfig에서 Bean으로 등록 시 내가 정해준 메소드 이름)'과,'빈 객체 이름(그 메소드가 반환한 구현체!!!★)'을 출력한다.
            System.out.println("name="+beanDefinitionName+", object="+bean);
        }
    } //스프링이 내부에서 사용하는 빈 + 내가 직접 등록해준 빈 =>두 가지가 동시에 출력됨


    //둘 중에 내가 원하는 것만 출력해보자.
    //나머지는 비슷하고,
    // 1) 빈 이름만 사용하는 게 아니라 빈 이름을 이용하여 '진짜 빈' 반환받아 사용
    // 2) if문으로 걸러서 출력
    //이 2가지만 추가된다.
    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean(){
        String[]  beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName :  beanDefinitionNames){
            //BeanDefinition 타입에 getBeanDefinition(빈이름)을 통해서 진짜 '★빈★'을 반환받아 담아둔다.
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            //if문과 getRole()을 이용한다.
            //해당 빈(beanDefinition)의 롤이 ROLE_APPLICATION => 내가 직접 등록해준 '애플리케이션 빈'
            //ROLE_INFRASTRUCTURE => 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole()==BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name="+beanDefinitionName+", object="+bean);
            }
        }
    }//내가 원하는 '애플리케이션 빈'만 출력하게 된다.

    /*
    (BeanDefinition이 Bean의 데이터 타입이다.
    BeanDefinition의 이름만 필요할 경우, String에 담아 사용했다.
    BeanDefinition이 여러개이기 때문에, 이 모든 이름을 위해 String[]를 사용했다.
    Object는, 말 그대로 구현체(인스턴스)를 담는 타입이다!!!) */
}
