package hello.core.member;

public interface MemberService { //MemberService 인터페이스. 기능 2가지. 가입, id로 멤버 조회
    void join(Member member);

    Member findMember(Long memberId);
}
