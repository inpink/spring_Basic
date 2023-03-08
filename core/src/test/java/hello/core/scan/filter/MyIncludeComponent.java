package hello.core.scan.filter;

import java.lang.annotation.*;

//@Component에 달려있는 어노테이션 3개를 가져온다.
//이 @MyIncludeComponent를 달아준 클래스는 컴포넌트와 비슷한 기능을 한다
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent { //직접 @MyIncludeComponent라는 Annotation을 생성
}
