package apigateway.auth.filters;

import apigateway.auth.AuthValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthPrefilter extends AbstractGatewayFilterFactory<AuthPrefilter.Config> {

    private final WebClient.Builder webClientBuilder;
    private final Logger logger;

    // TODO
    List<String> excludedUrls = new ArrayList<>();

    public AuthPrefilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
        this.logger = LoggerFactory.getLogger(AuthPrefilter.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String bearerToken = request.getHeaders().getFirst("Authorization");
            System.out.println(bearerToken);

            if (isSecured(request)){
                return webClientBuilder.build().post()
                        .uri("lb://AUTH/api/auth/validate_token")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)

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
                        .flatMap(chain::filter)
                        .onErrorResume(error -> {
                            // TODO handle unauthrized exception
                            logger.info(error.getMessage());

                            return Mono.error(error);
                        });
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
