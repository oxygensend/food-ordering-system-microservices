package auth.application.controller;

import auth.application.request.AuthenticationRequest;
import auth.application.request.RefreshTokenRequest;
import auth.application.request.RegisterRequest;
import auth.application.response.AuthenticationResponse;
import auth.domain.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    private final AuthController authController;

    private final MockMvc mockMvc;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    public void testRegister_Successful() throws Exception {
        // Arrange
        AuthenticationResponse response = new AuthenticationResponse("accessToken", "refreshToken");
        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\":\"John\"," +
                                "\"lastName\":\"Doe\"," +
                                "\"email\":\"test@example.com\"," +
                                "\"password\":\"password\"" +
                                "}"
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("accessToken"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").value("refreshToken"))
                .andDo(print());
    }


    @Test
    public void testAuthenticate_Successful() throws Exception {
        // Arrange
        AuthenticationResponse response = new AuthenticationResponse("accessToken", "refreshToken");

        when(authService.authenticate(any(AuthenticationRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"email\":\"test@example.com\"," +
                                "\"password\":\"password\"}"
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("accessToken")).andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").value("refreshToken"))
                .andDo(print());

    }

    @Test
    public void testRefreshToken_Successful() throws Exception {
        // Arrange
        AuthenticationResponse response = new AuthenticationResponse("accessToken", "refreshToken");
        when(authService.refreshToken(any(RefreshTokenRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"refresh_token\"}"
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("accessToken"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").value("refreshToken"))
                .andDo(print());
    }
}
