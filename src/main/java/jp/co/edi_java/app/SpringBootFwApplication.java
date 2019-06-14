package jp.co.edi_java.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"jp.co.keepalive.springbootfw","jp.co.edi_java.app"})
public class SpringBootFwApplication {

	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(SpringBootFwApplication.class, args);
	}
}
