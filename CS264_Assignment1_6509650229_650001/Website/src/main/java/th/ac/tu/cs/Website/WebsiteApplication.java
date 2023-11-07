/*
 * กาญจนพ บัวรอด
 * 6509650229
 * 25/10/2566
 * ตัว Application สำหรับ run website
 */

package th.ac.tu.cs.Website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "th.ac.tu.cs.Website")
public class WebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);
	}
}
