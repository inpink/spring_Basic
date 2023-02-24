package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionTest { //[스프링 빈 설정 메타 정보 - BeanDefinition]

    /*이전에 배운 내용이 많이 포함됨
    BeanDefinition : Bean의 데이터 타입
    BeanDefinition에도 '추상화(역할과 구현을 나눔)'이 적용된다.
    스프링 컨테이너는 '빈 설정 파일'이 JAVA인지, XML인지 몰라도 된다. BeanDefinition인 것만 알면 된다!
    (역할 : BeanDefinition, 구현 : AnnotationConfigApplicationContext, GenericXmlApplicationContext)
    BeanDefinition : 빈 설정 메타정보
    @Bean , <bean> 하나 당  각각 하나의 메타 정보가 생성된다.
     */

    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(AppConfig.class);

    //이전에 했던 것처럼, getBeanDefinitionNames()와 getRole()을 통해 Appconfig.java에 등록된 내가 등록해준 빈들의 '이름'만 모두 가져온다. for문을 이용해서 BeanDefinition Type에 실제로 빈을 담아준다!
    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames= ac.getBeanDefinitionNames();
        for (String beanDefinitionName: beanDefinitionNames){
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole()==BeanDefinition.ROLE_APPLICATION){
                System.out.println("beanDefinitionName = "+beanDefinitionName
                    +" beanDeifiniton"+beanDefinition);
                //"BeanDefinition을 출력해본다!" ☆빈의 정보인, BeanClassName, Scope, lazyInit등등이 출력된다!☆
                //☆BeanDefinition을 직접 정의하거나 사용할 일은 거의 없기 때문에, 추가적인 정보에 대해선 적당히 읽고 넘어가도 되겠다. 블로그 참고할 것.
            }
        }
    }
}
