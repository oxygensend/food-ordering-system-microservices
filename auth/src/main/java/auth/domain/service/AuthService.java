package auth.domain.service;

import auth.application.request.AuthenticationRequest;
import auth.application.request.RefreshTokenRequest;
import auth.application.request.RegisterRequest;
import auth.application.response.AuthenticationResponse;
import auth.domain.entity.Session;
import auth.domain.enums.TokenTypeEnum;
import auth.domain.manager.SessionManager;
import auth.infrastructure.config.TokenConfiguration;
import auth.infrastructure.payload.RefreshTokenPayload;
import auth.infrastructure.security.TokenStorage;
import auth.domain.entity.User;
import auth.infrastructure.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return sessionManager.prepareSession(user);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("User with this email exists");
        }

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                true
        );
        userRepository.save(user);

        return sessionManager.prepareSession(user);

    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {

       RefreshTokenPayload payload = (RefreshTokenPayload) tokenStorage.validate(request.token(), TokenTypeEnum.REFRESH);

       if(payload.getExp().before(new Date())){
           throw new IllegalStateException("Refresh token expired");
       }

       Optional<Session> session = sessionManager.getSession(payload.getSessionId());
       if(session.isEmpty()){
           throw new RuntimeException("Session expired");
       }

       User user = userRepository.findById(payload.getSessionId())
               .orElseThrow(() -> new RuntimeException("User not found"));

       return sessionManager.prepareSession(user);
    }
}
