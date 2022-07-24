package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.user.register.RegisterDto;

public interface CustomerDetailService {
    Object register(RegisterDto payload);
}
