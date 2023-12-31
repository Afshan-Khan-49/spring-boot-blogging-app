package com.example.bloggingapp.config;

import com.example.bloggingapp.user.entity.User;
import com.example.bloggingapp.user.enums.UserLoginType;
import com.example.bloggingapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;

    //On user login this method will be executed to load user details into db
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        try {
            return processOidcUser(userRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oidcUser.getAttributes());

        Optional<User> userOptional = userRepository.findByEmail(googleUserInfo.getEmail());
        if (userOptional.isEmpty()) {
            User user = User.builder()
                    .email(googleUserInfo.getEmail())
                    .firstName(googleUserInfo.getFirstName())
                    .lastName(googleUserInfo.getLastNameName())
                    .loginType(UserLoginType.GOOGLE.name())
                    .build();
            userRepository.save(user);
        }

        return oidcUser;
    }
}
