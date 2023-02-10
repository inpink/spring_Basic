package hello.core.member;

public interface MemberRepository { //Member저장소 interface
    //두 가지 기능이 있다. 저장, id로 찾기

    void save(Member member);

    Member findById(Long memberId);
}
