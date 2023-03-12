package hieu.nv.bytela;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BytelaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BytelaApplication.class, args);
	}

}
