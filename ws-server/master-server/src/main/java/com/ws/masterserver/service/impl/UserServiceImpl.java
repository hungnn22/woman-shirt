package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.user.ChangeProfileDto;
import com.ws.masterserver.dto.customer.user.RegisterDto;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.service.UserService;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.validator.user.RegisterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final WsRepository repository;

    @Override
    public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {
        var userDto = repository.userRepository.findUserDtoByEmail(value, Boolean.TRUE);
        if (userDto == null) {
            throw new UsernameNotFoundException(String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.USER_VI.toLowerCase(Locale.ROOT)));
        }

        return User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(userDto.getRole().name())))
                .build();
    }

    /**
     * Khách hàng tự đăng ký tài khoản cho mình
     */
    @Override
    public Object registerCustomer(RegisterDto body) {
        RegisterValidator.validateRegisterDto(body);
        return null;
    }

    /**
     * KH tự thay đổi thông tin cá nhân
     * */
    @Override
    public Object changeCustomerProfile(CurrentUser currentUser, ChangeProfileDto body) {
        return null;
    }

}
