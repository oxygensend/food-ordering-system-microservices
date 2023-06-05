package auth.infrastructure.payload.provider;

import auth.domain.enums.TokenTypeEnum;
import auth.infrastructure.payload.factory.AccessTokenPayloadFactory;
import auth.infrastructure.payload.factory.RefreshTokenPayloadFactory;
import auth.infrastructure.payload.factory.TokenPayloadFactory;

public class TokenPayloadFactoryProvider {

    public static TokenPayloadFactory getFactory(TokenTypeEnum tokenType){
       return switch (tokenType){
            case ACCESS -> new AccessTokenPayloadFactory();
            case REFRESH -> new RefreshTokenPayloadFactory();
        };
    }
}
