package com.ws.masterserver.proxy;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.CloudUtils;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Component
@Slf4j
public class CloudProxy {

    @Transactional
    public String uploadImage(byte[] image) throws IOException {
        log.info("CloudProxy uploadImage start...");

        Map res = CloudUtils.getCloudinary().uploader().upload(image, Collections.emptyMap());
        log.info("CloudProxy uploadImage res: {}", res);
        log.info("CloudProxy uploadImage finish...");
        return res.get("secure_url").toString();

    }

    public String uploadImage(MultipartFile file) throws IOException {
        return uploadImage(file.getBytes());
    }
}
