package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/* 자동 DI 시 중복되는 스프링 빈에 대해 구분자를 달기 위해
@Qualifier("mainDiscountPolicy")를 쓰면,
'☆컴파일☆'시 타입 체크가 어렵다.  mainnDiscountPolicy 이렇게 오타를 내도 체크가 안된다는 것이다.
mainDiscountPolicy는 '문자'이기 때문이다!!
☆직접 어노테이션을 만들어서☆ 이 문제를 해결할 수 있다.
(어노테이션은 문자가 아니기에 컴파일 시 체크가 가능함!)

★기존 Qualifier Annotation class에 있는 어노테이션 4개를 모두 가져온다!★
그리고, @Qualifier("mainDiscountPolicy")를 달아서 @Qualifier 기능도 추가해준다!*/
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {

}
