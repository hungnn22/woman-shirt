package com.ws.masterserver.utils.validator.user;

import com.ws.masterserver.dto.admin.user.action.UserDto;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;

/**
 * @author myname
 */
public class AdminUserValidator {
    private static final String FIRST_NAME = "Họ";
    private static final String LAST_NAME = "Tên";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "Mật khẩu";
    private static final String PHONE = "SDT";
    private static final String ROLE = "Quyền";
    private static final String GENDER = "Giới tính";
    private static final String AVATAR = "Ảnh đại diện";
    private static final String DOB = "Ngày sinh";

    public static void validCreate(UserDto dto) {
        ValidatorUtils.validNullOrEmpty(FIRST_NAME, dto.getFirstName());
        ValidatorUtils.validLength(FIRST_NAME, dto.getFirstName(), 100, true);
        ValidatorUtils.validOnlyCharacter(FIRST_NAME, dto.getFirstName());
        ValidatorUtils.validNullOrEmpty(LAST_NAME, dto.getLastName());
        ValidatorUtils.validLength(LAST_NAME, dto.getLastName(), 100, true);
        ValidatorUtils.validOnlyCharacter(LAST_NAME, dto.getLastName());
        ValidatorUtils.validNullOrEmpty(EMAIL, dto.getEmail());
        ValidatorUtils.validLength(EMAIL, dto.getEmail(), 6, 250);
        ValidatorUtils.validEmail(EMAIL, dto.getEmail());
        ValidatorUtils.validNullOrEmpty(PASSWORD, dto.getPassword());
        ValidatorUtils.validLength(PASSWORD, dto.getPassword(), 6, 100);
        ValidatorUtils.validNullOrEmpty(PHONE, dto.getPhone());
        ValidatorUtils.validPhone(PHONE, dto.getPhone());
        ValidatorUtils.validRole(ROLE, dto.getRole());
        ValidatorUtils.validBooleanType(GENDER, dto.getGender());
        ValidatorUtils.validAgeBetween(DOB, dto.getDob(), 14, 115);

        var repository = BeanUtils.getBean(WsRepository.class);
        validEmailMustBeUnique(repository, dto.getEmail().trim());
        validPhoneMustBeUnique(repository, dto.getPhone().trim());
    }

    private static void validPhoneMustBeUnique(WsRepository repository, String phone) {
        if (repository.userRepository.existsByPhone(phone)) {
            throw new WsException(WsCode.PHONE_EXISTS);
        }
    }

    private static void validEmailMustBeUnique(WsRepository repository, String email) {
        if (repository.userRepository.existsByEmailIgnoreCase(email)) {
            throw new WsException(WsCode.EMAIL_EXISTS);
        }
    }
}
