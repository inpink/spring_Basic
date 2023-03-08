package hello.core.scan.filter;

import java.lang.annotation.*;

//@MyExcludeComponent도 마찬가지
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
