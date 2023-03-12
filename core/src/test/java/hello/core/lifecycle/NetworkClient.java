package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient { // implements InitializingBean, DisposableBean {
    //이 클래스에서는 실제로 네트워크에 연결하는 것은 아니지만, 시작/종료 시점에 print를 통해 이를 확인하는 용도로 쓰인다.
    //시작 시 호출, 종료 시 호출 등.. 필요한 메서드들을 만들어준다.

    private String url; //사용할 변수 url

    public NetworkClient() { //생성자
        System.out.println("생성자 호출, url = " + url);
        //connect(); //생성자 시점에서 connect()를 호출해주면,
        // 아래 setUrl은 나중에 호출되기 떄문에 url이 null인 상태로 가기 때문에 문제가 있다.
        //call("초기화 연결 메시지"); //call도 마찬가지이다.
    }

    public void setUrl(String url) { //setter
        this.url = url;
    }

    //서비스 시작시 호출되어야 함
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) { //출력용
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출되어야 함
    public void disConnect() {
        System.out.println("close: " + url);
    }


    /*
    //스프링에서  지원하는 '빈 생명주기 콜백 - 초기화/소멸 ' 시점 호출
    // : 인터페이스(InitializingBean, DisposableBean)를 이용

    //NetworkClient class에서, 생성자 시점에 connect()와 call()을 해주는 것은 문제가 있었다. 이 두 메서드를 여기에서 치워버리고,
    //afterPropertiesSet(), destroy() 메서드를 만들어서 각각  connect()와 call() / disConnect();를 넣어준다.
    //( 이 두 메서드는 throws Exception가 필요하다.)
    @Override
    public void afterPropertiesSet() throws Exception {
        //InitializingBean 인터페이스는 afterPropertiesSet() 메서드를 통해
        // '빈 초기화 콜백 시점'을 알려준다!
        connect();
        call("초기화 연결 메시지");
    }
    @Override
    public void destroy() throws Exception {
        //DisposableBean 인터페이스는 destroy() 메서드를 통해
        // '빈 소멸 콜백 시점'을 알려준다!
        disConnect();
    }*/

    /*
    //@Configuration 설정 파일에서 @Bean으로 NetworkClient를 스프링 빈으로 수동 등록해줄 때,
    // @Bean(initMethod = "init", destroyMethod = "close") 같은 방법으로
    // '빈 초기화/소멸' 지점에 내가 만든 init, close메서드를 실행시켜주게 할 수 있다!
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    public void close() {
        System.out.println("NetworkClient.close");
        disConnect();
    }*/


    //@PostConstruct : 빈 초기화 콜백 시점에 실행할 메소드에 달아주면 됨
    //@PreDestroy : 빈 소멸 콜백 시점에 실행할 메소드에 달아주면 됨
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }
    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disConnect();
    }

}
