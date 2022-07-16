package com.ws.masterserver.utils.validator.user;

import com.ws.masterserver.dto.admin.user.action.UserDto;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("AdminUserValidator validCreate start");
        validCreateOrUpdate(dto);
        ValidatorUtils.validNullOrEmpty(EMAIL, dto.getEmail());
        ValidatorUtils.validLength(EMAIL, dto.getEmail(), 6, 250);
        ValidatorUtils.validEmail(EMAIL, dto.getEmail());
        ValidatorUtils.validNullOrEmpty(PASSWORD, dto.getPassword());
        ValidatorUtils.validLength(PASSWORD, dto.getPassword(), 6, 100);
        var repository = BeanUtils.getBean(WsRepository.class);
        validEmailMustBeUnique(repository, dto.getEmail().trim());
        validPhoneMustBeUnique(repository, dto.getPhone().trim());
    }

    private static void validPhoneMustBeUnique(WsRepository repository, String phone) {
        log.info("AdminUserValidator validPhoneMustBeUnique start");
        if (repository.userRepository.existsByPhone(phone)) {
            throw new WsException(WsCode.PHONE_EXISTS);
        }
    }

    private static void validEmailMustBeUnique(WsRepository repository, String email) {
        log.info("AdminUserValidator validEmailMustBeUnique start");
        if (repository.userRepository.existsByEmailIgnoreCase(email)) {
            throw new WsException(WsCode.EMAIL_EXISTS);
        }
    }

    /**
     * khi update chỉ được thay đổi 1 số trường. các trường khác phải giữ nguyên
     */
    public static void validUpdate(UserDto dto) {
        log.info("AdminUserValidator validUpdate start");
        var repository = BeanUtils.getBean(WsRepository.class);
        validExists(repository, dto.getId());
        validCreateOrUpdate(dto);
        validPhoneMustBeUnique(repository, dto.getPhone(), dto.getId());
    }

    private static void validPhoneMustBeUnique(WsRepository repository, String phone, String id) {
        log.info("AdminUserValidator validPhoneMustBeUnique start");
        if (repository.userRepository.existsByPhoneAndIdNot(phone, id)) {
            throw new WsException(WsCode.PHONE_EXISTS);
        }
    }

    private static void validCreateOrUpdate(UserDto dto) {
        ValidatorUtils.validNullOrEmpty(FIRST_NAME, dto.getFirstName());
        ValidatorUtils.validLength(FIRST_NAME, dto.getFirstName(), 100, true);
        ValidatorUtils.validOnlyCharacter(FIRST_NAME, dto.getFirstName());
        ValidatorUtils.validNullOrEmpty(LAST_NAME, dto.getLastName());
        ValidatorUtils.validLength(LAST_NAME, dto.getLastName(), 100, true);
        ValidatorUtils.validOnlyCharacter(LAST_NAME, dto.getLastName());
        ValidatorUtils.validNullOrEmpty(PHONE, dto.getPhone());
        ValidatorUtils.validPhone(PHONE, dto.getPhone());
        ValidatorUtils.validRole(ROLE, dto.getRole());
        ValidatorUtils.validBooleanType(GENDER, dto.getGender());
        ValidatorUtils.validAgeBetween(DOB, dto.getDob(), 14, 115);
    }

    private static void validExists(WsRepository repository, String id) {
        if (!repository.userRepository.existsByIdAndActive(id, true)) {
            throw new WsException(WsCode.USER_NOT_FOUND);
        }
    }
}
