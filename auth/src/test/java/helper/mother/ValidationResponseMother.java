package helper.mother;

import auth.application.response.ValidationResponse;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class ValidationResponseMother {

    public static ValidationResponse authorized() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        return new ValidationResponse(
                true,
                "test@test.com",
                grantedAuthorityList
        );
    }
    public static ValidationResponse unAuthorized() {
        return new ValidationResponse(
                false,
                null,
                null
        );
    }

}
