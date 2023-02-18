package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp { //순수 자바 코드로 하는 TEST

    public static void main(String[] args) {
        //MemberService 상속받은 MemberServiceImple 생성. 이 안의 join 테스트 할 것임.
        //MemberService memberService=new MemberServiceImpl();

        //ApplConfig를 추가해 줬으므로, Test에서 썼던 MemberService와 OrderService도 바꿔줘야 함.

        //AppConfig를 만들어 줬으므로, 여기서 꺼내 사용
        //AppConfig appConfig = new AppConfig();
        //MemberService memberService = appConfig.memberService();

        //스프링 적용. (스프링으로 전환하기)
        //위에까지는 순수 JAVA를 이용하여 DI를 적용해줬다. (직접 JAVA를 통해 AppConfig class에 접근해서 직접 new Appconfig 객체를 만들어서 꺼내서 사용)
        // 이제부터는 '스프링 컨테이너'를 이용하는 스프링 사용으로 전환한다.

        //ApplicationContext : 스프링 컨테이너
        //이렇게 하면, AppConfig에서 직접 꺼내쓰는 게 아니라, '스프링 컨테이너'를 통해서 '스프링 빈(객체)'를 꺼내야 한다.
        //AppConfig를 @Configuration를 통해서 '설정 클래스'로 '스프링 컨테이너'에 등록해준 바 있다. 이를 설정 정보로서 가져오는 것이다.
        ApplicationContext applicationContext=
                new AnnotationConfigApplicationContext(AppConfig.class);

        //applicationContext.getBean() 메서드를 이용하여 꺼낼 수 있다.
        //매개변수는 (찾을 메소드 이름, 반환 타입)
        //(스프링 빈으로 등록할 때의 메소드 이름이 '☆스프링 빈의 이름☆'이다!)
        MemberService memberService=applicationContext.getBean("memberService",MemberService.class);

        //Member 객체 생성해서 join테스트 시 사용
        Member member=new Member(1L,"memberA", Grade.VIP); //Long id, String name, enum Grade. Long type이라 1L으로 L 붙여줘야 함
        memberService.join(member); //join 테스트 할 것

        Member findMember=memberService.findMember(1L); //join 테스트가 목적이기에, findMember는 맞다고 가정해야 할듯.
        System.out.println("new member : "+member.getName()); //비교 대상은, member 객체의 getName()메쏘드를 이용함. ( 당연히 이값은 맞다고 확신해야겠지)
        System.out.println("new member : "+findMember.getName()); //출력해서 확인
    }
}
