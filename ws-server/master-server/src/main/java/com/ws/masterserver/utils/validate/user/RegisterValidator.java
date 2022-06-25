package com.ws.masterserver.utils.validate.user;

import com.ws.masterserver.dto.customer.user.RegisterDto;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.validate.ValidateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

/**
 * @author myname
 */
public class RegisterValidator {

    private RegisterValidator() {}

    public static void validateRegisterDto(RegisterDto body) {
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
