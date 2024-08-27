package com.delivery.api.domain.user.controller;

import com.delivery.api.common.annotation.UserSession;
import com.delivery.api.common.api.Api;
import com.delivery.api.domain.user.business.UserBusiness;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.api.domain.user.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(
            @Parameter(hidden = true)
            @UserSession User user
    ){

        var response = userBusiness.me(user);
        return Api.OK(response);
    }

}
