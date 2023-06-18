package apigateway.filters;

import apigateway.AuthValidationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Component
public class AuthenticationPrefilter extends AbstractGatewayFilterFactory<AuthenticationPrefilter.Config> {

    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    @Qualifier("excludedUrls")
    List<String> excludedUrls;

    public AuthenticationPrefilter(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String bearerToken = request.getHeaders().getFirst("Authorization");
            System.out.println(bearerToken);

            if (isSecured(request)){
                return webClientBuilder.build().get()
                        .uri("lb://AUTH/api/auth/validate-token")
                        .header("Authorization", bearerToken)
                        .retrieve().bodyToMono(AuthValidationResponse.class)
                        .map(response -> {
                            exchange.getRequest().mutate().header("username", response.username());
                            exchange.getRequest().mutate().header("authorities", response.authorities()
                                    .stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .reduce("", (a,b) ->  a + "," + b)
                            );

                            return exchange;
                        })
                        .flatMap(chain::filter);
            }

            return chain.filter(exchange);

        });

    }

    private boolean isSecured(ServerHttpRequest request){
        return excludedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
    }


    public static class Config {

        public Config() {
        }
    }

}
