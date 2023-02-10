package hello.core.member;

public class MemberServiceImpl implements MemberService{
    //상속받는것이 하나일 경우 class 생성 시 이름에 Impl 붙이는 것이 관례

    //사용하는 메모리를 불러와야 한다 (내장 메모리 불러옴)
    private final MemberRepository memberRepository= new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member); //구현
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId); //구현
    }
}
