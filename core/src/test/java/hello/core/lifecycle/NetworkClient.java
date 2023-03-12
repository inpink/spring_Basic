package hello.core.lifecycle;

public class NetworkClient {
    //이 클래스에서는 실제로 네트워크에 연결하는 것은 아니지만, 시작/종료 시점에 print를 통해 이를 확인하는 용도로 쓰인다.
    //시작 시 호출, 종료 시 호출 등.. 필요한 메서드들을 만들어준다.

    private String url; //사용할 변수 url

    public NetworkClient() { //생성자
        System.out.println("생성자 호출, url = " + url);
        connect(); //생성자 시점에서 connect()를 호출해주면,
        // 아래 setUrl은 나중에 호출되기 떄문에 url이 null인 상태로 가기 때문에 문제가 있다.
        call("초기화 연결 메시지"); //call도 마찬가지이다.
    }

    public void setUrl(String url) { //setter
        this.url = url;
    }

    //서비스 시작시 호출되어야 함
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) { //출력용
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출되어야 함
    public void disconnect() {
        System.out.println("close: " + url);
    }

}
