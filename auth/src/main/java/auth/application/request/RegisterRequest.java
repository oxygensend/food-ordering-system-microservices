package auth.application.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @Size(min = 2)
    @Size(max = 64)
    private  String firstName;
    @Size(min = 2)
    @Size(max = 64)
    private String lastName;

    @Email
    private  String email;
    @Size(min = 2)
    @Size(max = 200)
    private  String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String firstname, String lastName, String email, String password) {
        this.firstName = firstname;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
