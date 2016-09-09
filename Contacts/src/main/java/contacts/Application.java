package contacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;

/**
 * Created by makisucruse on 16/9/10.
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//        return new EmbeddedServletContainerCustomizer() {
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer container) {
//                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//                container.addErrorPages(error401Page, error404Page, error500Page);
//            }
//        };
//    }

    public static void main(String[] args) {
        System.out.println("here?");
        SpringApplication.run(Application.class, args);
    }
}
