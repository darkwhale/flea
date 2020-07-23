package org.zxy.flea.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo findByUserEmail(String userEmail);

}
