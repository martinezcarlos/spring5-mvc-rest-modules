package guru.springframework.config;

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
 * Created by carlosmartinez on 2019-04-10 18:36
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/")
        .apiInfo(metaData());
  }

  /**
   * Configuration for the Swagger webpage header data API info.
   *
   * @return
   */
  private ApiInfo metaData() {
    //final Contact contact = new Contact("Carlos Martínez", "http://www.assistrain.co",
    //    "test@test.com");
    //return new ApiInfo("Spring MVC Rest", "Project for exposing REST services", "1.0",
    //    "Terms of service: bla", contact, "Apache License Version 2.0", "url", new ArrayList<>());
    return new ApiInfoBuilder().title("Spring Boot REST API")
        .description("\"Spring Boot REST API for Online Store\"")
        .version("1.0.0")
        .license("Apache License Version 2.0")
        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
        .contact(new Contact("Carlos Martínez", "http://www.assistrain.co/about/", "test@test.com"))
        .build();
  }
}
