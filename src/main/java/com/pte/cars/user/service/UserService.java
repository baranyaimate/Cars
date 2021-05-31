package com.pte.cars.user.service;

import com.pte.cars.core.service.CoreCRUDService;
import com.pte.cars.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends CoreCRUDService<UserEntity>, UserDetailsService {

    List<UserEntity> getIdFiltered(String filter);

    List<UserEntity> getUsernameFiltered(String filter);

    List<UserEntity> getFirstNameFiltered(String filter);

    List<UserEntity> getLastNameFiltered(String filter);

    List<UserEntity> getRoleFiltered(String filter);

}
