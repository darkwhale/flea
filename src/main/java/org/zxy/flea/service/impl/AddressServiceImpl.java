package org.zxy.flea.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.AddressForm;
import org.zxy.flea.mapper.AddressRepository;
import org.zxy.flea.service.AddressService;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressRepository addressRepository;

    @Override
    public ResponseVO<Address> add(AddressForm addressForm) {
        // 构造address并存储；
        Address address = new Address();
        BeanUtils.copyProperties(addressForm, address);

        // 插入到数据库;
        addressRepository.save(address);

        return ResponseVOUtil.success(address);
    }

    @Override
    public ResponseVO<Address> delete(Integer addressId) {
        Address address = addressRepository.findById(addressId).orElse(null);

        if (address == null) {
            throw new FleaException(ResponseEnum.ADDRESS_NOT_EXIST);
        }

        addressRepository.delete(address);
        return ResponseVOUtil.success(address);
    }
}
