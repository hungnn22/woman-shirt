package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.user.ChangeProfileDto;
import com.ws.masterserver.dto.customer.user.RegisterDto;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.service.UserService;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.validate.ValidateUtils;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
        validateRegisterDto(body);
        return null;
    }

    /**
     * KH tự thay đổi thông tin cá nhân
     * */
    @Override
    public Object changeCustomerProfile(CurrentUser currentUser, ChangeProfileDto body) {
        return null;
    }


    private void validateRegisterDto(RegisterDto body) {
        if (StringUtils.isNullOrEmpty(body.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.NOT_BLANK, WsConst.Nouns.EMAIL.toLowerCase(Locale.ROOT)));
        } else if (!ValidateUtils.isValidEmail(body.getEmail().trim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.INVALID, WsConst.Nouns.EMAIL));
        }
        if (StringUtils.isNullOrEmpty(body.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.NOT_BLANK, WsConst.Nouns.PASSWORD_VI.toLowerCase(Locale.ROOT)));
        } else if (!ValidateUtils.isValidPassword(body.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.INVALID, WsConst.Nouns.PASSWORD_VI));
        }
        if (StringUtils.isNullOrEmpty(body.getFirstName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.NOT_BLANK, WsConst.Nouns.FIRST_NAME_VI.toLowerCase(Locale.ROOT)));
        } else if (!ValidateUtils.isValidFullName(body.getFirstName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.INVALID, WsConst.Nouns.FIRST_NAME_VI));
        }
        if (StringUtils.isNullOrEmpty(body.getLastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.NOT_BLANK, WsConst.Nouns.LAST_NAME_VI.toLowerCase(Locale.ROOT)));
        } else if (!ValidateUtils.isValidFullName(body.getFirstName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.INVALID, WsConst.Nouns.LAST_NAME_VI));
        }
        if (body.getDob() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.NOT_BLANK, WsConst.Nouns.DOB_VI.toLowerCase(Locale.ROOT)));
        } else if (!ValidateUtils.isValidCustomerAge(body.getDob())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.INVALID_CUSTOMER_AGE, WsConst.Values.CUSTOMER_AGE_MIN, WsConst.Values.CUSTOMER_AGE_MAX));
        }
        if (body.getGender() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.NOT_BLANK, WsConst.Nouns.GENDER_VI.toLowerCase(Locale.ROOT)));
        }

    }
}
