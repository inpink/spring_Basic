package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig= new AppConfig();
        memberService= appConfig.memberService();
    }


    @Test //테스트를 위해 @Test 어노테이션을 달아준다.
    void join(){ //회원가입 기능 join()을 테스트한다.
        /*given-when-then 3가지 기법을 사용한다
        given : 주어진 데이터. Member객체 생성
        when : 테스트할 것. join을 테스트 할것이기 때문에, 이를 위해 사용되는 findMember은 맞다고 가정해야 할듯.
        then : 검증. assertThat을 이용한다. member객체와, findMember에서 반환한 객체가 같다면, join은 제기능을 하고 있다.
         */

        //given
        Member member=new Member(1L,"memberA",Grade.VIP);

        //when
        memberService.join(member);
        Member findMember= memberService.findMember(1L); //ID로 멤버 찾기

        //then
        //assertThat을 먼저 적어야 import됨
        Assertions.assertThat(member).isEqualTo(findMember);

        //실행해서 초록불이면 테스트 완료.
    }
}
