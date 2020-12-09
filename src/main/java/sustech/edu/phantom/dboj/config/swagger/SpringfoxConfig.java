package sustech.edu.phantom.dboj.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 */
@Configuration
@EnableSwagger2
public class SpringfoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("sustech.edu.phantom.dboj.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("SUSTech Phantom DBOJ")
                .description("This is the backend APIs for the SUSTech Phantom DBOJ.")
                .termsOfServiceUrl("https://starsky.ink:12222")
                .contact(new Contact("Shilong Li", "https://www.github.com/lethal233", "leemdragon233@gmail.com"))
                .build();
    }
}
