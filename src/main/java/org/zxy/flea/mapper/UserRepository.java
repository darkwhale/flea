package org.zxy.flea.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserId(String userId);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    Page<User> findByUsernameLike(String username, Pageable pageable);
}
