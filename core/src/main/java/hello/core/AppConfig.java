package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig { //Spring Container 역할을 함.
    //인터페이스(MemberService, OrderSerive)의 구현체(MemberServiceImpl,OrderServiceImpl 등)이
    // 다른 인터페이스의 구현체(MemoryMemberRepsitory, FixDiscountPolicy 등)를 직접 불러오지 않도록 하기 위함.

    //memberService() 메쏘드를 만든다. 이는, MemberService Type의 MemberServiceImpl()을 반환해준다!
    public MemberService memberService(){
        // 이것이 필요한 이유는,
        // MemberService의 구현체인 MemberServiceImple이 MemberRepository의 구현체인 MemoryMemberRepository를 직접 불러오지 않도록 하는 것이다.
        //=>  ★MemberServiceImpl() 의 생성자와 매개변수를 이용해서, 구현체인 MemoryMemberRepository를 주입해준다!!
        return new MemberServiceImpl(memberRepository());
        //★아래에서도 new MemoryMemberRepository가 중복 사용되고 있으며(매번 만들어줄 필요 없다)
        //여기서 매번 new로 구현체를 만들어주면 전체적인 역할-구현이 잘 보이지 않는다!
        //아래에서 '★각 인터페이스가 담당하는 각 구현체★'를 각 메쏘드로 만들어준다.
        //총 4개의 인터페이스-구현체 가 만들어졌다.
        //=>역할-구현이 한 눈에 보여서 애플리케이션의 전체 구성을 빠르게 파악할 수 있다.
    }

    // OrderService() 메쏘드도 만들어준다.
    public OrderService orderService(){
        //마찬가지로, OrderService의 구현체인 OrderServiceImpl가 직접 MemoryMemberRepository와 FixDiscountPolicy를 불러오지 않게 해주기 위함이다.
        return new OrderServiceImpl(memberRepository(), discountPolicy());
        //의존관계 OrderServiceImpl -> MemoryMemberRepository , FixDiscountPolicy 주입
    }

    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

    public DiscountPolicy discountPolicy(){
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
