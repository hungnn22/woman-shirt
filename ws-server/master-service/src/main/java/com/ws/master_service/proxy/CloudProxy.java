package com.ws.master_service.proxy;

import com.ws.master_service.utils.base.WsException;
import com.ws.master_service.utils.common.CloudUtils;
import com.ws.master_service.utils.constants.WsCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;


@Component
@Slf4j
public class CloudProxy {

    @Transactional
    public String uploadImage(byte[] image) {
        log.info("CloudProxy uploadImage start...");
        try {
            Map res = CloudUtils.getCloudinary().uploader().upload(image, Collections.emptyMap());
            log.info("CloudProxy uploadImage res: {}", res);
            log.info("CloudProxy uploadImage finish...");
            return res.get("secure_url").toString();
        } catch (Exception e) {
            log.info("CloudProxy uploadImage error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return uploadImage(file.getBytes());
    }

    public String uploadImage(String base64String) {
        return uploadImage(Base64.getDecoder().decode(base64String));
    }
}
