package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig { //Spring Container 역할을 함.
    //인터페이스(MemberService, OrderSerive)의 구현체(MemberServiceImpl,OrderServiceImpl 등)이
    // 다른 인터페이스의 구현체(MemoryMemberRepsitory, FixDiscountPolicy 등)를 직접 불러오지 않도록 하기 위함.
    //

    //memberService() 메쏘드를 만든다. 이는, MemberService Type의 MemberServiceImpl()을 반환해준다!
    public MemberService memberService(){
        // 이것이 필요한 이유는,
        // MemberService의 구현체인 MemberServiceImple이 MemberRepository의 구현체인 MemoryMemberRepository를 직접 불러오지 않도록 하는 것이다.
        return new MemberServiceImpl(new MemoryMemberRepository());
        //=>  ★MemberServiceImpl() 의 생성자와 매개변수를 이용해서, 구현체인 MemoryMemberRepository를 주입해준다!!
    }

    // OrderService() 메쏘드도 만들어준다.
    public OrderService orderService(){
        //마찬가지로, OrderService의 구현체인 OrderServiceImpl가 직접 MemoryMemberRepository와 FixDiscountPolicy를 불러오지 않게 해주기 위함이다.
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        //의존관계 OrderServiceImpl -> MemoryMemberRepository , FixDiscountPolicy 주입
    }
}
