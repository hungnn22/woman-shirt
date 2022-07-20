package com.ws.master_service.utils.validator.product;

import com.ws.master_service.dto.admin.product.create_update.OptionDto;
import com.ws.master_service.dto.admin.product.create_update.ProductDto;
import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.base.WsRepository;
import com.ws.master_service.utils.common.BeanUtils;
import com.ws.master_service.utils.common.ValidatorUtils;
import com.ws.master_service.utils.constants.WsCode;

import java.util.Collections;
import java.util.List;

/**
 * @author myname
 */
public class AdminProductValidator {

    private static final String NAME = "Tên";
    private static final String DES = "Mô tả";
    private static final String OPTION = "Tùy chọn";
    private static final String PRICE = "Giá";
    private static final String QTY = "Số lượng";

    private AdminProductValidator() {
        super();
    }

    public static void validCreate(ProductDto dto) {
        var repository = BeanUtils.getBean(WsRepository.class);
        ValidatorUtils.validNullOrEmpty(NAME, dto.getName());
        ValidatorUtils.validOnlyCharacterAndNumber(NAME, dto.getName());
        ValidatorUtils.validLength(NAME, dto.getName(), 6, 100);
        ValidatorUtils.validNullOrEmpty(DES, dto.getDes());
        ValidatorUtils.validOnlyCharacterAndNumber(DES, dto.getDes());
        ValidatorUtils.validLength(DES, dto.getDes(), 6, 255);
        ValidatorUtils.validNullOrEmptyList(OPTION, Collections.singletonList(dto.getOptions()));
        validOptionDto(dto.getOptions(), repository);
        validExist(dto, repository);
    }

    private static void validExist(ProductDto dto, WsRepository repository) {
        if (!repository.categoryRepository.existsByIdAndActive(dto.getCategoryId(), true)) {
            throw new WsException(WsCode.INTERNAL_SERVER, "Danh mục không hợp lệ");
        }
        if (!repository.materialRepository.existsByIdAndActive(dto.getMaterialId(), true)) {
            throw new WsException(WsCode.INTERNAL_SERVER, "Chất liệu không hợp lệ");
        }
        if (!repository.typeRepository.existsByIdAndActive(dto.getTypeId(), true)) {
            throw new WsException(WsCode.INTERNAL_SERVER, "Loại sản phẩm không hợp lệ");
        }
    }

    private static void validOptionDto(List<OptionDto> options, WsRepository repository) {
        for (var option : options) {
            ValidatorUtils.validNullOrEmpty(PRICE, option.getPrice());
            ValidatorUtils.validOnlyNumber(PRICE, option.getPrice());
            ValidatorUtils.validPrice(PRICE, option.getPrice());
            ValidatorUtils.validNullOrEmpty(QTY, option.getQty());
            ValidatorUtils.validOnlyNumber(QTY, option.getQty());
            ValidatorUtils.validPrice(QTY, option.getQty());
            validExist(option, repository);
        }
    }

    private static void validExist(OptionDto option, WsRepository repository) {
        if (!repository.sizeRepository.existsById(option.getSizeId())) {
            throw new WsException(WsCode.INTERNAL_SERVER, "Size không hợp lệ");
        }
        if (!repository.colorRepository.existsByIdAndActive(option.getColorId(), true)) {
            throw new WsException(WsCode.INTERNAL_SERVER, "Màu không hợp lệ");
        }
    }
}
