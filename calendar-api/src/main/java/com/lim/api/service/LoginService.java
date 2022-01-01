package com.lim.api.service;

import com.lim.api.dto.LoginReq;
import com.lim.api.dto.SignUpReq;
import com.lim.core.domain.entity.User;
import com.lim.core.dto.UserCreateReq;
import com.lim.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    public final static String LOGIN_SESSION_KEY = "USER_ID";
    private final UserService userService;

    @Transactional
    public void signUp(SignUpReq signUpReq, HttpSession httpSession) {

        final User user = userService.create(new UserCreateReq(
                signUpReq.getName(),
                signUpReq.getEmail(),
                signUpReq.getPassword(),
                signUpReq.getBirthday()
        ));
        httpSession.setAttribute(LOGIN_SESSION_KEY, user.getId());

    }

    @Transactional
    public void login(LoginReq loginReq, HttpSession httpSession) {
        /**
         * 세션 값이 있으면 return
         * 세션 값이 없으면 비밀번호 체크 후 로그인 & 세션에 담고 리턴
         */
        final Long userId = (Long) httpSession.getAttribute(LOGIN_SESSION_KEY);
        if (userId != null) {
            return;
        }
        final Optional<User> user =
                userService.findPwMatchUser(loginReq.getEmail(), loginReq.getPassword());

        if (user.isPresent()) {
            httpSession.setAttribute(LOGIN_SESSION_KEY, user.get().getId());
        } else {
            throw new RuntimeException("password or email not match");
        }

    }

    public void logout(HttpSession httpSession) {

        /**
         * 세션 제거 하고 끝
         */
        httpSession.removeAttribute(LOGIN_SESSION_KEY);
    }

}
