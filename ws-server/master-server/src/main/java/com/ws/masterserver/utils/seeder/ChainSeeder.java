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
        var promotionTypes = initPromotionTypes();
        var promotions = initPromotions(promotionTypes);
        var materials = initMaterials();
        var category = initCategory(types);
        var product = initProduct(category, materials);
        var productOptions = initProductOptions(product, colors);
        var users = initUsers();
        var customer = users.get("customer@gmail.com");
        var staff = users.get("staff@gmail.com");
        var address = initAddress4Customer(customer);
        var orders = initOrders(customer, address);
        orders.forEach(order -> {
            initOrderDetails(order, productOptions);
            initOrderStatus(order, customer, staff);
            initOrderPromotions(order, promotions);
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
                        .promotionTypeId(promotionTypes.get(getRandomIndex(promotionTypes.size())).getId())
                        .build(),
                PromotionEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(true)
                        .name("Giảm 5% giá trị đơn hàng")
                        .expiredDate(new Date())
                        .percentDiscount(5.0)
                        .useLimit(1L)
                        .voucher(UidUtils.generateVoucher())
                        .promotionTypeId(promotionTypes.get(getRandomIndex(promotionTypes.size())).getId())
                        .build()
        );
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
        repository.promotionTypeRepository.saveAll(promotionTypes);
        return promotionTypes;
    }

    private List<TypeEntity> initTypes() {
        var typesCount = repository.typeRepository.count();
        if (typesCount != 3) {
            repository.typeRepository.deleteAll();
        }
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
        log.info("3. Product Option List: {}", JsonUtils.toJson(productOptions));
        return productOptions;
    }

    private ProductEntity initProduct(CategoryEntity category, List<MaterialEntity> materials) {
        var product = ProductEntity.builder()
                .id(WsConst.Seeders.PRODUCT_ID)
                .categoryId(category.getId())
                .name(WsConst.Seeders.PRODUCT_NAME)
                .des(WsConst.Seeders.PRODUCT_DES)
//                .price(WsConst.Seeders.PRODUCT_PRICE)
//                .thumbnail(WsConst.Seeders.PRODUCT_THUMBNAIL)
                .materialId(materials.get(getRandomIndex(materials.size())).getId())
                .active(Boolean.TRUE)
                .build();
        repository.productRepository.save(product);
        log.info("2. Product: {}", product);
        return product;
    }

    private CategoryEntity initCategory(List<TypeEntity> types) {
        var type = types.get(getRandomIndex(types.size()));
        var category = CategoryEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(WsConst.Seeders.CATEGORY_NAME)
                .active(Boolean.TRUE)
                .des(WsConst.Seeders.CATEGORY_DES)
                .typeId(type.getId())
                .build();
        repository.categoryRepository.save(category);
        log.info("1. Category: {}", JsonUtils.toJson(category));
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
        for (var po : productOptions) {
            ods.add(OrderDetailEntity.builder()
                    .id(UidUtils.generateUid())
                    .orderId(order.getId())
                    .productOptionId(po.getId())
                    .price(po.getPrice())
                    .qty(2L)
                    .build());
        }

        if (!ods.isEmpty()) {
            repository.orderDetailRepository.saveAll(ods);
        }
        log.info("8. Order Detail: {}", ods);
    }

    private List<OrderEntity> initOrders(UserEntity customer, List<AddressEntity> address) {
        var orders = List.of(
                OrderEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .userId(customer.getId())
                        .addressId(address.get(0) == null ? null : address.get(0).getId())
                        .note("Giao hàng giờ hành chính")
                        .payed(false)
                        .type(OrderTypeEnum.CASH.name())
                        .shipPrice(20000L)
                        .build(),
                OrderEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .userId(customer.getId())
                        .addressId(address.get(1) == null ? null : address.get(1).getId())
                        .note("Giao cuối tuần")
                        .payed(false)
                        .type(OrderTypeEnum.CASH.name())
                        .shipPrice(20000L)
                        .build()
        );
        repository.orderRepository.saveAll(orders);
        log.info("6. Order: {}", JsonUtils.toJson(orders));
        return orders;
    }

    private List<AddressEntity> initAddress4Customer(UserEntity customer) {
        var addressList = List.of(
                AddressEntity.builder()
                        .id(UidUtils.generateUid())
                        .provinceCode(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_CODE1)
                        .provinceName(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_NAME1)
                        .districtCode(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_CODE1)
                        .districtName(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_NAME1)
                        .wardCode(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_CODE1)
                        .wardName(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_NAME1)
                        .exact(WsConst.Seeders.CUSTOMER_ADDRESS_EXACT1)
                        .combination(WsConst.Seeders.CUSTOMER_ADDRESS_COMBINATION1)
                        .userId(customer.getId())
                        .active(Boolean.TRUE)
                        .isDefault(Boolean.FALSE)
                        .build(),
                AddressEntity.builder()
                        .id(UidUtils.generateUid())
                        .provinceCode(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_CODE2)
                        .provinceName(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_NAME2)
                        .districtCode(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_CODE2)
                        .districtName(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_NAME2)
                        .wardCode(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_CODE2)
                        .wardName(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_NAME2)
                        .exact(WsConst.Seeders.CUSTOMER_ADDRESS_EXACT2)
                        .combination(WsConst.Seeders.CUSTOMER_ADDRESS_COMBINATION2)
                        .userId(customer.getId())
                        .active(Boolean.TRUE)
                        .isDefault(Boolean.FALSE)
                        .build()
        );
        addressList.forEach(address -> {
            if (Boolean.FALSE.equals(repository.addressRepository.existsByWardCodeAndExactIgnoreCase(address.getWardCode(), address.getExact().trim()))) {
                repository.addressRepository.save(address);
            }
        });
        log.info("5. Address List: {}", addressList);
        return addressList;


    }

    private Map<String, UserEntity> initUsers() {
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
        var customer = UserEntity.builder()
                .id(UUID.fromString(WsConst.Seeders.CUSTOMER_ID).toString())
                .active(Boolean.TRUE)
                .email(WsConst.Seeders.CUSTOMER_EMAIL)
                .firstName(WsConst.Seeders.CUSTOMER_FIRST_NAME)
                .lastName(WsConst.Seeders.CUSTOMER_LAST_NAME)
                .password(passwordEncoder.encode(WsConst.Seeders.CUSTOMER_PASSWORD))
                .role(RoleEnum.ROLE_CUSTOMER)
                .gender(Boolean.TRUE)
                .phone(WsConst.Seeders.CUSTOMER_PHONE)
                .build();
        Map<String, UserEntity> userMaps = new HashMap<>();
        userMaps.put(admin.getEmail(), admin);
        userMaps.put(staff.getEmail(), staff);
        userMaps.put(customer.getEmail(), customer);

        userMaps.values().forEach(user -> {
            var userExistsByEmail = repository.userRepository.findByEmailIgnoreCaseAndActive(user.getEmail(), Boolean.TRUE);
            if (userExistsByEmail != null) {
                repository.userRepository.delete(userExistsByEmail);
            }
            repository.userRepository.save(user);
        });
        log.info("4. User: {}", JsonUtils.toJson(userMaps));
        return userMaps;
    }

    private int getRandomIndex(int size) {
        return random.nextInt(size);
    }
}
