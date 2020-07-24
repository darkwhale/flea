package org.zxy.flea.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.UserInfoVO;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.dataobject.Campus;
import org.zxy.flea.dataobject.UserInfo;
import org.zxy.flea.form.UserInfoForm;
import org.zxy.flea.mapper.UserInfoRepository;
import org.zxy.flea.service.UserInfoService;
import org.zxy.flea.util.KeyUtil;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private AddressServiceImpl addressService;

    @Resource
    private CampusServiceImpl campusService;

    @Resource
    private KeyUtil keyUtil;

    @Override
    public UserInfo findByUserEmail(String userEmail) {

        UserInfo userInfo = userInfoRepository.findByUserEmail(userEmail);

        // 若查找不到则表明未添加，则创建相关数据；
        if (userInfo ==  null) {
            userInfo = getEmpty(userEmail);
        }

        // 存入数据库中；
        userInfoRepository.save(userInfo);

        return userInfo;
    }

    @Override
    public UserInfo modify(UserInfoForm userInfoForm, String userEmail) {
        UserInfo userInfo = userInfoRepository.findByUserEmail(userEmail);

        // 未找到数据；
        if (userInfo == null) {
            userInfo = getEmpty(userEmail);
        }

        BeanUtils.copyProperties(userInfoForm, userInfo);
        userInfoRepository.save(userInfo);

        return userInfo;
    }

    /**
     * 创建空数据,赋id和email；
     * @param userEmail
     * @return
     */
    private UserInfo getEmpty(String userEmail) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserEmail(userEmail);
        userInfo.setUserInfoId(keyUtil.genUniqueKey());

        return userInfo;
    }

    public UserInfoVO converter(UserInfo userInfo) {
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);

        // 获取地址列表
        Map<Integer, Address> addressMap = addressService.getAddressList();

        // 获取学院列表
        Map<Integer, Campus> campusMap = campusService.getCampusMap();

        if (userInfo.getUserResideAddressId() != null) {
            userInfoVO.setUserResideAddress(addressMap.get(userInfo.getUserResideAddressId()).toString());
        }

        if (userInfo.getUserStudyAddressId() != null) {
            userInfoVO.setUserStudyAddress(addressMap.get(userInfo.getUserStudyAddressId()).toString());
        }

        if (userInfo.getUserCampusId() != null) {
            userInfoVO.setUserCampus(campusMap.get(userInfo.getUserCampusId()).getCampusName());
        }

        return userInfoVO;
    }

}
