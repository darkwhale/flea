package org.zxy.flea.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.UserVO;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.dataobject.Campus;
import org.zxy.flea.dataobject.User;
import org.zxy.flea.enums.GenderEnum;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.UserForm;
import org.zxy.flea.form.UserLoginForm;
import org.zxy.flea.form.UserRegisterForm;
import org.zxy.flea.mapper.UserRepository;
import org.zxy.flea.service.UserService;
import org.zxy.flea.util.EnumUtil;
import org.zxy.flea.util.KeyUtil;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private KeyUtil keyUtil;

    @Resource
    private AddressServiceImpl addressService;

    @Resource
    private CampusServiceImpl campusService;

    @Override
    public User register(UserRegisterForm userRegisterForm) {
        // 判断是否已存在
        User user = userRepository.findByEmail(userRegisterForm.getEmail());
        if (user != null) {
            throw new FleaException(ResponseEnum.USER_EXIST);
        }

        // 插入数据库中
        User newUser = new User();
        BeanUtils.copyProperties(userRegisterForm, newUser);
        newUser.setUserId(keyUtil.genUniqueKey());

        // 插入数据
        userRepository.save(newUser);

        return newUser;
    }

    @Override
    public User login(UserLoginForm userLoginForm) {
        // 查找数据库
        User user = userRepository.findByEmailAndPassword(userLoginForm.getEmail(), userLoginForm.getPassword());
        if (user == null) {
            throw new FleaException(ResponseEnum.USER_NOT_EXIST_OR_PASSWORD_WRONG);
        }

        return user;
    }

    @Override
    public User update(UserForm userForm, String userId) {

        // 查找是否存在user
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new FleaException(ResponseEnum.USER_NOT_EXIST);
        }

        BeanUtils.copyProperties(userForm, user);
        userRepository.save(user);

        return user;
    }

    @Override
    public Page<User> findByUsername(String username, Pageable pageable) {
        return userRepository.findByUsernameLike("%" + username + "%", pageable);
    }

    @Override
    public UserVO converter(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        // 获取地址列表
        Map<Integer, Address> addressMap = addressService.getAddressList();

        // 获取学院列表
        Map<Integer, Campus> campusMap = campusService.getCampusMap();

        if (user.getUserResideAddressId() != null) {
            userVO.setUserResideAddress(addressMap.get(user.getUserResideAddressId()).toString());
        }

        if (user.getUserStudyAddressId() != null) {
            userVO.setUserStudyAddress(addressMap.get(user.getUserStudyAddressId()).toString());
        }

        if (user.getUserCampusId() != null) {
            userVO.setUserCampus(campusMap.get(user.getUserCampusId()).getCampusName());
        }

        // 转换gender
        if (user.getUserGender() != null) {
            userVO.setGender(Objects.requireNonNull(EnumUtil.getByCode(user.getUserGender(), GenderEnum.class)).getMessage());
        }

        return userVO;
    }

    @Override
    public User findByUserId(String userId) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new FleaException(ResponseEnum.USER_NOT_EXIST);
        }

        return user;
    }
}
