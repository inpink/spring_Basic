package hello.core.singleton;

public class StatefulService {
    //싱글톤 패턴을 적용할 때, 필드 때문에 문제가 발생하는 class를 직접 작성해봄

    private int price; //상태를 유지하는(stateful한) 필드(변수).
    //고정된 값으로 둔다면 이 자체만으론 문제가 되지 않을 수 있지만..애초에 그럴거면 final을 두는게 맞긴 하다.
    // 외부에서 이 price값을 바꿔줄 수 있는 것이 큰 문제가 될 수 있음

    //order 메소드를 만들고, 여기서 this.price를 변경할 수 있도록 해준다.
    public void order(String name,int price){
        System.out.println("name = " + name + " price = " + price);
        this.price=price; //=> 여기가 문제임!!! 뭐가 문제인지는 StatefulServiceTest에서 더 자세히 볼 것.
    }

    public int getPrice(){
        return  price;
    }
}
