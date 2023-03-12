package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac =
                new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); //스프링 컨테이너를 종료, ConfigurableApplicationContext 필요
    }

    @Configuration
    static class LifeCycleConfig { //내부에 설정 클래스로 사용할 LifeCycleConfig class
        @Bean
        public NetworkClient networkClient() { //수동으로 @Bean을 통해 이 package에서 만든 NetworkClient를 스프링 빈으로 등록해준다.
            NetworkClient networkClient = new NetworkClient(); //새로운 NetworkClient 객체를 만들어주고,
            networkClient.setUrl("http://hello-spring.dev"); //필요한 url 설정을 해준다.
            return networkClient; //설정된 객체 반환
        }
    }
}
