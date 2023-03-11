package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //스프링은, @Component 어노테이션이 붙은 클래스를 스프링 빈으로 등록한다!
public class OrderServiceImpl implements OrderService { //구현체 OrderServiceImpl class를 만들고, OrderService 상속

    //MemberRepository, DiscountPolicy 두 가지가 필요하다. (여기서도 여전히 OCP, DIP 어기고 있긴 함)
    private final MemberRepository memberRepository; //마찬가지로 선언만 해둔다
    private final DiscountPolicy discountPolicy;
    //private final DiscountPolicy;

    /* 일반 메서드 주입
    @Autowired
    public void init222(MemberRepository memberRepository, DiscountPolicy
            discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }*/

  /*  수정자 주입
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy){
        this.discountPolicy=discountPolicy;
    }*/


    //'생성자'와 매개변수를 이용해서 AppConfig로부터 위의 두 개의 인터페이스의 구현체를 주입받는다.
    //(Alt+Insert하고 Constructor 이용)
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    //이렇게 하게되면 이제 구현체는 인터페이스에만 의존한다.
    //구현체 입장에서는 생성자를 통해서 어떤 구현 객체가 주입될지 알 수 없다.
    // 이를 온전히 외부에(AppConfig, 스프링 컨테이너)에 맡긴다.

    //@Configuration과 싱글톤 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) { //createOrder을 구현한다.
        Member member=memberRepository.findById(memberId); //저장소에서 findById로 객체 반환받고,
        int discountPrice=discountPolicy.discount(member,itemPrice);  //그 객체를 discountPolicy.discount로 넘겨서 할인된 가격을 반환받는다.
        //=> SRP (단일 책임 원칙)이 잘 지켜진 경우임. 할인은  discountPolicy가 온전히 담당하기 때문임.

        //마지막으로, new Order을 만들어서 새로운 Order 객체를 생성하여 반환해준다!
        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
}
