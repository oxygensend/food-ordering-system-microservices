package auth.domain.service;

import auth.application.request.AuthenticationRequest;
import auth.application.request.RefreshTokenRequest;
import auth.application.request.RegisterRequest;
import auth.application.response.AuthenticationResponse;
import auth.application.response.ValidationResponse;
import auth.domain.entity.Session;
import auth.domain.enums.TokenTypeEnum;
import auth.domain.exception.InvalidCredentialsException;
import auth.infrastructure.exception.TokenException;
import auth.domain.exception.SessionExpiredException;
import auth.domain.exception.UserAlreadyExistsException;
import auth.domain.manager.SessionManager;
import auth.infrastructure.payload.RefreshTokenPayload;
import auth.infrastructure.security.TokenStorage;
import auth.domain.entity.User;
import auth.infrastructure.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenStorage tokenStorage;
    private final SessionManager sessionManager;


    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            TokenStorage tokenStorage,
            SessionManager sessionManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenStorage = tokenStorage;
        this.sessionManager = sessionManager;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
            return sessionManager.prepareSession((User) authentication.getPrincipal());
        } catch (BadCredentialsException exception) {
            throw new InvalidCredentialsException();
        }
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.email());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = new User(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password()),
                true
        );
        userRepository.save(user);

        return sessionManager.prepareSession(user);

    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {

        RefreshTokenPayload payload = (RefreshTokenPayload) tokenStorage.validate(request.token(), TokenTypeEnum.REFRESH);

        if (payload.getExp().before(new Date())) {
            throw new TokenException("Token expired");
        }

        Optional<Session> session = sessionManager.getSession(payload.getSessionId());
        if (session.isEmpty()) {
            throw new SessionExpiredException("Session expired");
        }

        User user = userRepository.findById(payload.getSessionId())
                .orElseThrow(() -> new SessionExpiredException("User not found by session id"));

        return sessionManager.prepareSession(user);
    }

    public ValidationResponse validateToken(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) request.getAttribute("authorities");
        boolean isAuthorized = username != null && authorities != null;

        return new ValidationResponse(isAuthorized, username, authorities);
    }
}
