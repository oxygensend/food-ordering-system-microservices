package auth.infrastructure.payload;

import auth.domain.enums.TokenTypeEnum;
import auth.infrastructure.payload.factory.AccessTokenPayloadFactory;
import auth.infrastructure.payload.factory.RefreshTokenPayloadFactory;
import auth.infrastructure.payload.provider.TokenPayloadFactoryProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class TokenPayloadFactoryProviderTest {

    @Test
    public void test_GetAccessTokenPayloadFactoryInstance()  {

        var factory = TokenPayloadFactoryProvider.getFactory(TokenTypeEnum.ACCESS);
        assertInstanceOf(AccessTokenPayloadFactory.class, factory);
    }

    @Test
    public void test_GetRefreshTokenPayloadFactoryInstance()  {

        var factory = TokenPayloadFactoryProvider.getFactory(TokenTypeEnum.REFRESH);
        assertInstanceOf(RefreshTokenPayloadFactory.class, factory);
    }


}
