package com.pte.cars.user.service.impl;

import com.pte.cars.core.service.impl.CoreCRUDServiceImpl;
import com.pte.cars.user.entity.UserEntity;
import com.pte.cars.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class UserServiceImpl extends CoreCRUDServiceImpl<UserEntity> implements UserService {
    @Override
    protected void updateCore(UserEntity persistedEntity, UserEntity entity) {
        persistedEntity.setAuthorities(entity.getAuthorities());
        persistedEntity.setUsername(entity.getUsername());
    }

    @Override
    protected Class<UserEntity> getManagedClass() {
        return UserEntity.class;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TypedQuery<UserEntity> query = entityManager.createNamedQuery(UserEntity.FIND_USER_BY_USERNAME, UserEntity.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public List<UserEntity> getIdFiltered(String filter) {
        return entityManager.createQuery("SELECT u FROM " + getManagedClass().getSimpleName() + " u WHERE CAST(u.id AS string) LIKE '%" + filter + "%'", getManagedClass())
                .getResultList();
    }

    public List<UserEntity> getUsernameFiltered(String filter) {
        return entityManager.createQuery("SELECT u FROM " + getManagedClass().getSimpleName() + " u WHERE UPPER(u.username) LIKE '%" + filter.toUpperCase() + "%'", getManagedClass())
                .getResultList();
    }

    public List<UserEntity> getFirstNameFiltered(String filter) {
        return entityManager.createQuery("SELECT u FROM " + getManagedClass().getSimpleName() + " u WHERE UPPER(u.firstname) LIKE '%" + filter.toUpperCase() + "%'", getManagedClass())
                .getResultList();
    }


    public List<UserEntity> getLastNameFiltered(String filter) {
        return entityManager.createQuery("SELECT u FROM " + getManagedClass().getSimpleName() + " u WHERE UPPER(u.lastname) LIKE '%" + filter.toUpperCase() + "%'", getManagedClass())
                .getResultList();
    }

    public List<UserEntity> getRoleFiltered(String filter) {
        List<UserEntity> resultList = entityManager.createQuery("SELECT u FROM " + getManagedClass().getSimpleName() + " u JOIN u.authorities a", getManagedClass())
                .getResultList();
        if (!resultList.isEmpty()) {
            resultList.removeIf(item -> !item.getAuthorities().toString().contains(filter));
        }
        return resultList;
    }

}
