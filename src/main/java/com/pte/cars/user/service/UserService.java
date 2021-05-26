package com.pte.cars.user.service;

import com.pte.cars.core.service.CoreCRUDService;
import com.pte.cars.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends CoreCRUDService<UserEntity>, UserDetailsService {
}
