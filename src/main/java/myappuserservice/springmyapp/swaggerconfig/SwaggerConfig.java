package myappuserservice.springmyapp.swaggerconfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "User-Service API", version = "1.0.0", description = "User-Service API реализует CRUD-операции с БД"))
public class SwaggerConfig {
}
