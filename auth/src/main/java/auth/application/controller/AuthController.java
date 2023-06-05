package auth.application.controller;

import auth.application.request.RefreshTokenRequest;
import auth.domain.service.AuthService;
import auth.application.request.AuthenticationRequest;
import auth.application.request.RegisterRequest;
import auth.application.response.AuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(
            @RequestBody @Validated RegisterRequest request
    ) {
        return authService.register(request);

    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse register(
            @RequestBody @Validated AuthenticationRequest request
    ) {
        logger.info("przeszedlem walidacje");
        return authService.authenticate(request);
    }

    @PostMapping("/refresh_token")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse refreshToken(
            @RequestBody @Validated RefreshTokenRequest request
    ) {
        return authService.refreshToken(request);
    }
}
