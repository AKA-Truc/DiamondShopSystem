package goldiounes.com.vn.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Diamond Shop System",
                version = "1.0.0",
                description = "Ứng dụng quản lý cửa hàng bán kim cương trực tuyến"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Development Server"),
//                @Server(url = "http://172.18.147.204:8080", description = "Production Server"),
        }
)

@SecurityScheme(
        name = "bearer-key", // Can be any name, used to reference this scheme in the @SecurityRequirement annotation
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {

}