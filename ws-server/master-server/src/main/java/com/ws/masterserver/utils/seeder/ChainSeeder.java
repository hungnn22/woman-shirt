package com.ws.masterserver.utils.seeder;

import com.ws.masterserver.entity.*;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.constants.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Transactional
@Slf4j
public class ChainSeeder implements Seeder {

    private WsRepository repository = BeanUtils.getBean(WsRepository.class);

    private Random random = SecureRandom.getInstanceStrong();

    public ChainSeeder() throws NoSuchAlgorithmException {
        //add cmt vao k d' pass sonar
    }

    @Override
    public void seed() {
        var types = initTypes();
        var colors = initColors();
        var materials = initMaterials();
        var promotionTypes = initPromotionTypes();
        var promotions = initPromotions(promotionTypes);
        var category = initCategory(types);
        var product = initProduct(category, materials);
        var productOptions = initProductOptions(product, colors);
        var orders = initUsers();
        orders.forEach(order -> {
            initOrderDetails(order, productOptions);
        });

    }

    private List<OrderPromotionEntity> initOrderPromotions(OrderEntity order, List<PromotionEntity> promotions) {
        var orderPromotions = List.of(
                OrderPromotionEntity.builder()
                        .id(UidUtils.generateUid())
                        .orderId(order.getId())
                        .promotionId(promotions.get(getRandomIndex(promotions.size())).getId())
                        .build()
        );
        repository.orderPromotionRepository.saveAll(orderPromotions);

        return orderPromotions;
    }

    private List<PromotionEntity> initPromotions(List<PromotionTypeEntity> promotionTypes) {
        var promotions = List.of(
                PromotionEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(true)
                        .name("Free ship nội thành")
                        .expiredDate(new Date())
                        .percentDiscount(100.0)
                        .useLimit(1L)
                        .voucher(UidUtils.generateVoucher())
                        .promotionTypeId(promotionTypes.get(0).getId())
                        .build(),
                PromotionEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(true)
                        .name("Giảm 5% giá trị đơn hàng")
                        .expiredDate(new Date())
                        .percentDiscount(5.0)
                        .useLimit(1L)
                        .voucher(UidUtils.generateVoucher())
                        .promotionTypeId(promotionTypes.get(1).getId())
                        .build()
        );
        log.info("5. save promotion list: {}", JsonUtils.toJson(promotions));
        repository.promotionRepository.saveAll(promotions);
        return promotions;
    }

    private List<PromotionTypeEntity> initPromotionTypes() {
        var promotionTypes = List.of(
                PromotionTypeEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(true)
                        .name(PromotionTypeEnum.TYPE1.name())
                        .build(),
                PromotionTypeEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(true)
                        .name(PromotionTypeEnum.TYPE2.name())
                        .build());
        log.info("4. save promotion type list: {}", JsonUtils.toJson(promotionTypes));
        repository.promotionTypeRepository.saveAll(promotionTypes);
        return promotionTypes;
    }

    private List<TypeEntity> initTypes() {
        var types = List.of(
                TypeEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(Boolean.TRUE)
                        .name(TypeEnum.MALE.name())
                        .build(),
                TypeEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(Boolean.TRUE)
                        .name(TypeEnum.FEMALE.name())
                        .build(),
                TypeEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(Boolean.TRUE)
                        .name(TypeEnum.UNISEX.name())
                        .build()
        );
        log.info("1. save type list: {}", JsonUtils.toJson(types));
        return repository.typeRepository.saveAll(types);
    }

    private List<MaterialEntity> initMaterials() {
        var materials = List.of(
                MaterialEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(MaterialEnum.MTR01.getName())
                        .active(Boolean.TRUE)
                        .build(),
                MaterialEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(MaterialEnum.MTR02.getName())
                        .active(Boolean.TRUE)
                        .build(),
                MaterialEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(MaterialEnum.MTR03.getName())
                        .active(Boolean.TRUE)
                        .build()
        );
        log.info("3. save material list: {}", JsonUtils.toJson(materials));
        repository.materialRepository.saveAll(materials);
        return materials;
    }

    private List<ColorEntity> initColors() {
        var colors = List.of(
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.BLUE.getViName())
                        .hex(ColorEnum.BLUE.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.WHITE.getViName())
                        .hex(ColorEnum.WHITE.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.BLACK.getViName())
                        .hex(ColorEnum.BLACK.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.GREEN.getViName())
                        .hex(ColorEnum.GREEN.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.GREY.getViName())
                        .hex(ColorEnum.GREY.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.VIOLET.getViName())
                        .hex(ColorEnum.VIOLET.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.RED.getViName())
                        .hex(ColorEnum.RED.getHex())
                        .active(Boolean.TRUE)
                        .build()
        );
        log.info("2. save color list: {}", JsonUtils.toJson(colors));
        repository.colorRepository.saveAll(colors);
        return colors;
    }

    private List<ProductOptionEntity> initProductOptions(ProductEntity product, List<ColorEntity> colors) {
        var productOptions = List.of(
                ProductOptionEntity.builder()
                        .id(UidUtils.generateUid())
                        .productId(product.getId())
                        .colorId(colors.get(getRandomIndex(colors.size())).getId())
                        .image(WsConst.Seeders.PRODUCT_OPTION_BLUE_IMG)
                        .size(SizeEnum.S)
                        .active(Boolean.TRUE)
                        .qty(10L)
                        .price(WsConst.Seeders.PRODUCT_OPTION_BLUE_PRICE)
                        .build(),
                ProductOptionEntity.builder()
                        .id(UidUtils.generateUid())
                        .productId(product.getId())
                        .colorId(colors.get(getRandomIndex(colors.size())).getId())
                        .image(WsConst.Seeders.PRODUCT_OPTION_GREY_IMG)
                        .size(SizeEnum.M)
                        .active(Boolean.TRUE)
                        .qty(5L)
                        .price(WsConst.Seeders.PRODUCT_OPTION_GREY_PRICE)
                        .build()
        );

        repository.productOptionRepository.saveAll(productOptions);
        log.info("8. save Product Option List: {}", JsonUtils.toJson(productOptions));
        return productOptions;
    }

    private ProductEntity initProduct(CategoryEntity category, List<MaterialEntity> materials) {
        var product = ProductEntity.builder()
                .id(WsConst.Seeders.PRODUCT_ID)
                .categoryId(category.getId())
                .name(WsConst.Seeders.PRODUCT_NAME)
                .des(WsConst.Seeders.PRODUCT_DES)
                .materialId(materials.get(getRandomIndex(materials.size())).getId())
                .active(Boolean.TRUE)
                .build();
        repository.productRepository.save(product);
        log.info("7. save Product: {}", product);
        return product;
    }

    private CategoryEntity initCategory(List<TypeEntity> types) {
        var category = CategoryEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(WsConst.Seeders.CATEGORY_NAME)
                .active(Boolean.TRUE)
                .des(WsConst.Seeders.CATEGORY_DES)
                .typeId(types.get(getRandomIndex(types.size())).getId())
                .build();
        repository.categoryRepository.save(category);
        log.info("6. save category: {}", JsonUtils.toJson(category));
        return category;

    }

    private void initOrderStatus(OrderEntity order, UserEntity customer, UserEntity staff) {
        var calendar = Calendar.getInstance();
        repository.orderStatusRepository.save(OrderStatusEntity.builder()
                .id(UUID.randomUUID().toString())
                .orderId(order.getId())
                .status(StatusEnum.PENDING)
                .createdDate(calendar.getTime())
                .createdBy(customer.getId())
                .build());
        calendar.add(Calendar.HOUR, 4);
        repository.orderStatusRepository.save(OrderStatusEntity.builder()
                .id(UUID.randomUUID().toString())
                .orderId(order.getId())
                .status(StatusEnum.ACCEPT)
                .createdDate(calendar.getTime())
                .createdBy(staff.getId())
                .build());
    }

    private void initOrderDetails(OrderEntity order, List<ProductOptionEntity> productOptions) {
        var ods = new ArrayList<OrderDetailEntity>();
        var total = 0L;
        for (var po : productOptions) {
            ods.add(OrderDetailEntity.builder()
                    .id(UidUtils.generateUid())
                    .orderId(order.getId())
                    .productOptionId(po.getId())
                    .price(po.getPrice())
                    .qty(2L)
                    .build());
            total += po.getPrice() * 2L;
        }
        log.info("12. Order Detail: {}", ods);
        repository.orderDetailRepository.saveAll(ods);

        order.setTotal(total);
        log.info("13: update order: ", JsonUtils.toJson(order));
        repository.orderRepository.save(order);
    }

    private List<OrderEntity> initUsers() {
        var passwordEncoder = BeanUtils.getBean(BCryptPasswordEncoder.class);
        var admin = UserEntity.builder()
                .id(UUID.fromString(WsConst.Seeders.ADMIN_ID).toString())
                .active(Boolean.TRUE)
                .email(WsConst.Seeders.ADMIN_EMAIL)
                .firstName(WsConst.Seeders.ADMIN_FIRST_NAME)
                .lastName(WsConst.Seeders.ADMIN_LAST_NAME)
                .password(passwordEncoder.encode(WsConst.Seeders.ADMIN_PASSWORD))
                .role(RoleEnum.ROLE_ADMIN)
                .gender(Boolean.TRUE)
                .build();
        var staff = UserEntity.builder()
                .id(UUID.fromString(WsConst.Seeders.STAFF_ID).toString())
                .active(Boolean.TRUE)
                .email(WsConst.Seeders.STAFF_EMAIL)
                .firstName(WsConst.Seeders.STAFF_FIRST_NAME)
                .lastName(WsConst.Seeders.STAFF_LAST_NAME)
                .password(passwordEncoder.encode(WsConst.Seeders.STAFF_PASSWORD))
                .role(RoleEnum.ROLE_STAFF)
                .gender(Boolean.TRUE)
                .build();
        var customer1 = UserEntity.builder()
                .id(UidUtils.generateUid())
                .active(Boolean.TRUE)
                .email(WsConst.Seeders.CUSTOMER_EMAIL1)
                .firstName(WsConst.Seeders.CUSTOMER_FIRST_NAME1)
                .lastName(WsConst.Seeders.CUSTOMER_LAST_NAME1)
                .password(passwordEncoder.encode(WsConst.Seeders.CUSTOMER_PASSWORD1))
                .role(RoleEnum.ROLE_CUSTOMER)
                .gender(Boolean.TRUE)
                .phone(WsConst.Seeders.CUSTOMER_PHONE1)
                .build();
        var customer2 = UserEntity.builder()
                .id(UidUtils.generateUid())
                .active(Boolean.TRUE)
                .email(WsConst.Seeders.CUSTOMER_EMAIL2)
                .firstName(WsConst.Seeders.CUSTOMER_FIRST_NAME2)
                .lastName(WsConst.Seeders.CUSTOMER_LAST_NAME2)
                .password(passwordEncoder.encode(WsConst.Seeders.CUSTOMER_PASSWORD2))
                .role(RoleEnum.ROLE_CUSTOMER)
                .gender(Boolean.TRUE)
                .phone(WsConst.Seeders.CUSTOMER_PHONE2)
                .build();

        var users = List.of(admin, staff, customer1, customer2);

        log.info("9. save User list: {}", JsonUtils.toJson(users));
        repository.userRepository.saveAll(users);

        var add1 = AddressEntity.builder()
                .id(UidUtils.generateUid())
                .provinceCode(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_CODE1)
                .provinceName(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_NAME1)
                .districtCode(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_CODE1)
                .districtName(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_NAME1)
                .wardCode(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_CODE1)
                .wardName(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_NAME1)
                .exact(WsConst.Seeders.CUSTOMER_ADDRESS_EXACT1)
                .combination(WsConst.Seeders.CUSTOMER_ADDRESS_COMBINATION1)
                .userId(customer1.getId())
                .active(Boolean.TRUE)
                .isDefault(Boolean.FALSE)
                .build();
        var add2 = AddressEntity.builder()
                .id(UidUtils.generateUid())
                .provinceCode(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_CODE2)
                .provinceName(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_NAME2)
                .districtCode(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_CODE2)
                .districtName(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_NAME2)
                .wardCode(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_CODE2)
                .wardName(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_NAME2)
                .exact(WsConst.Seeders.CUSTOMER_ADDRESS_EXACT2)
                .combination(WsConst.Seeders.CUSTOMER_ADDRESS_COMBINATION2)
                .userId(customer2.getId())
                .active(Boolean.TRUE)
                .isDefault(Boolean.FALSE)
                .build();
        var address = List.of(add1, add2);
        log.info("10. save address listL {}", JsonUtils.toJson(address));
        repository.addressRepository.saveAll(address);

        var order1 = OrderEntity.builder()
                .id(UUID.randomUUID().toString())
                .userId(customer1.getId())
                .addressId(add1.getId())
                .note("Giao hàng giờ hành chính")
                .payed(false)
                .type(OrderTypeEnum.CASH.name())
                .shipPrice(20000L)
                .code("DH260822KH01")
                .build();
        var order2 = OrderEntity.builder()
                .id(UUID.randomUUID().toString())
                .userId(customer2.getId())
                .addressId(add2.getId())
                .note("Giao cuối tuần")
                .payed(false)
                .type(OrderTypeEnum.CASH.name())
                .shipPrice(20000L)
                .code("DH260822KH02")
                .build();

        var orders = List.of(order1, order2);
        log.info("11. save Order list: {}", JsonUtils.toJson(orders));
        repository.orderRepository.saveAll(orders);

        return orders;
    }

    private int getRandomIndex(int size) {
        return random.nextInt(size);
    }
}
