package hello.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonService { //싱글톤을 직접 코드로 구현해보자.

    //instance라는 ★이 클래스의 유일한 인스턴스★를 생성한다.
    private static final SingletonService instance=new SingletonService();

    //☆생성자도 private로 해둬서 외부에서 new를 통해 이 클래스의 인스턴스를 만들 수 없도록 한다!
    // 생성자 내부 내용은 비워놔도 괜찮다.
    private SingletonService(){

    }

    //이 클래스의 인스턴스를 가져갈 수 있게 하기 위해, public으로 getInstance 메소드만 만들어둔다. return instance;
    //이 메서드를 호출하면, 당연히 항상 같은 인스턴스를 반환한다!
    //(static을 반드시 해줘야하는데, static과 관련된 내용은 나중에 자세히 배운다)
    public static SingletonService getInstance(){
        return instance;
    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출"); //그냥 출력해주는 메소드.
    }

    //(싱글톤 패턴을 구현하는 방법에는 여러 가지 방법이 있다. )
    //지금 만든 코드 테스트는 SingletonTest에서 함
}
