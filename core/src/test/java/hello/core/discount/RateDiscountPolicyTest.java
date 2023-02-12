package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RateDiscountPolicyTest {

    //방금 만든 RateDiscountPolicy 객체를 생성해준다.
    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    //vip인 경우 할인 가격 테스트
    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    void vip_o(){
        //given
        Member member = new Member(1l,"memberVIP", Grade.VIP);

        //when
        int discount= discountPolicy.discount(member,10000);

        //then
        //두 번째 Assertions 선택해야 함
        Assertions.assertThat(discount).isEqualTo(1000);
    }

    //테스트를 할 때는, 성공 테스트만 하면 50%만 만든 것이다.
    // 실패한 경우에 대해서도 만들어줘야 한다. (vip_x)
    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다.")
    void vip_x(){
        //given
        //Grade.Basic이면, asserThat에서 discount값이 0으로 나오는 지 검증한다.
        Member member = new Member(1l,"memberVIP", Grade.BASIC);

        //when
        int discount= discountPolicy.discount(member,10000);

        //then
        //두 번째 Assertions 선택해야 함
        Assertions.assertThat(discount).isEqualTo(0);
    }

}
