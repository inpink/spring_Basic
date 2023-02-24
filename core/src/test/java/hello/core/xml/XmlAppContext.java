package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class XmlAppContext {
    // 방금 만든 appConfig XML을 스프링컨테이너에 올리고, 잘 사용되는 지 확인하기 위해 테스트 폴더를 이용할 것

    /*
    기존에 JAVA 파일은 AnnotationConfigApplicationContext을 사용했다.
    XML로 만드는 스프링 컨테이너에 올릴  설정 파일은.
    ☆GenericXmlApplicationContext 를 사용하면서 'xml 설정 파일'을 스프링 컨테이너로 넘겨주면 된다!☆
     */
    @Test
    void xmlAppContext(){
        //GenericXmlApplicationContext로
        //이전에 만들어준 appConfig.xml파일을 설정 정보로 스프링 컨테이너에 올려준다.
        ApplicationContext ac
                =new GenericXmlApplicationContext("appConfig.xml");

        //getBean과 assertThat을 이용하여 검증해본다. 잘 된다.
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
