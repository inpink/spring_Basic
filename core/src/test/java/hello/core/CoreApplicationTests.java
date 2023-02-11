package hello.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreApplicationTests {
	//test 폴더에 있는 CoreApplicationTests는 @SpringBootTest 어노테이션이 달려있다.
	//=> 스프링부트를 이용한 테스트는 비교적 순수 JAVA만 이용한 테스트보다 시간이 많이 걸린다
	//=> '단위 테스트'를 잘 만드는 것이 중요하다.
	// 이는 스프링 라이브러리 없이 순수 JAVA를 이용하여 잘 쪼개서 테스트하는 것을 의미함.

	@Test
	void contextLoads() {
	}

}
