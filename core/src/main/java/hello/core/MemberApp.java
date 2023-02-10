package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp { //순수 자바 코드로 하는 TEST

    public static void main(String[] args) {
        //MemberService 상속받은 MemberServiceImple 생성. 이 안의 join 테스트 할 것임.
        MemberService memberService=new MemberServiceImpl();
        //Member 객체 생성해서 join테스트 시 사용
        Member member=new Member(1L,"memberA", Grade.VIP); //Long id, String name, enum Grade. Long type이라 1L으로 L 붙여줘야 함
        memberService.join(member); //join 테스트 할 것

        Member findMember=memberService.findMember(1L); //join 테스트가 목적이기에, findMember는 맞다고 가정해야 할듯.
        System.out.println("new member : "+member.getName()); //비교 대상은, member 객체의 getName()메쏘드를 이용함. ( 당연히 이값은 맞다고 확신해야겠지)
        System.out.println("new member : "+findMember.getName()); //출력해서 확인
    }
}
