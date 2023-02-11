package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDistcountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService { //구현체 OrderServiceImpl class를 만들고, OrderService 상속

    //MemberRepository, DiscountPolicy 두 가지가 필요하다. (여기서도 여전히 OCP, DIP 어기고 있긴 함)
    private final MemberRepository memberRepository= new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy= new FixDistcountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) { //createOrder을 구현한다.
        Member member=memberRepository.findById(memberId); //저장소에서 findById로 객체 반환받고,
        int discountPrice=discountPolicy.discount(member,itemPrice);  //그 객체를 discountPolicy.discount로 넘겨서 할인된 가격을 반환받는다.
        //=> SRP (단일 책임 원칙)이 잘 지켜진 경우임. 할인은  discountPolicy가 온전히 담당하기 때문임.

        //마지막으로, new Order을 만들어서 새로운 Order 객체를 생성하여 반환해준다!
        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
}
