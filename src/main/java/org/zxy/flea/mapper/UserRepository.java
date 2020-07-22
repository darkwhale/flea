package org.zxy.flea.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
