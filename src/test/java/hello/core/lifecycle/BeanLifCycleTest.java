package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifCycleTest {
    @Test
    public void lifCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        // 스프링 빈의 라이프 사이클
        // 객체 생성 -> 의존관계 주입

        // 스프링 빈의 이벤트 라이프 사이클
        // 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용
        // -> 소멸전 콜백 -> 스프링 종료

        // *참고: 객체의 생성과 초기화를 분리하자! => 유지보수 관점에서 좋다.
        // initMethod, destroyMethod: 외부 라이브러리에도 적용 가능
        // destroyMethod의 기본 값은 (inferred) => 'close', 'shutdown'라는 이름의 메서드를 자동으로 호출
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
