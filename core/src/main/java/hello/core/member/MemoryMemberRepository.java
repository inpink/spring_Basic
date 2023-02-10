package hello.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository{
    // 내장 메모리에서만 저장하기에 test용도로만 사용 가능
    //아직 DB가 확정되지 않은 상태임. but, 개발은 진행해야 하기 때문에, 가장 단순한 내장 메모리 사용 저장소를 간단한 기능만 구현함

    private static Map<Long, Member> store = new HashMap<>(); //회원id, 회원객체를 묶어서 여러 개를 저장하는  맵을 생성
    //HashMap은 동시성 이슈가 있을 수 있어서 실무에서는 ConcurrentHashMap를 사용하긴 함.(여기서 설명하면 너무 딥해서 HashMap으로 사용)

    @Override
    public void save(Member member) {
        store.put(member.getId(),member); //id, member객체 형식으로 맵에 넣어줌
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId); //맵에서 id에 해당하는 '객체'를 찾아 객체 반환!
    }
}
