package hello.core.member;

public class MemberServiceImpl implements MemberService{
    //상속받는것이 하나일 경우 class 생성 시 이름에 Impl 붙이는 것이 관례

    //사용하는 메모리를 불러와야 한다 (내장 메모리 불러옴)
    private final MemberRepository memberRepository;
    //OCP와 DIC를 어기고 있는 부분을 수정해준다.
    // 선언만 하고

    //생성자를 생성하고, AppConfig를 이용하여 매개변수로 new MemoryMemberRepository()를 주입받는다!
    //=>스프링 컨테이너의 역할을 한다. ( 의존관계 MemberServiceImpl -> MemoryMemberRepository 주입)
    public  MemberServiceImpl(MemberRepository memberRepository){

        this.memberRepository=memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member); //구현
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId); //구현
    }
}
