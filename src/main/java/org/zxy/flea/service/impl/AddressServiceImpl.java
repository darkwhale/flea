package org.zxy.flea.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.enums.AddressTypeEnum;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.AddressForm;
import org.zxy.flea.form.AddressUpdateForm;
import org.zxy.flea.mapper.AddressRepository;
import org.zxy.flea.service.AddressService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressRepository addressRepository;

    @Override
    @CacheEvict(cacheNames = "addressList", allEntries = true)
    public Address add(AddressForm addressForm) {
        // 构造address并存储；
        Address address = new Address();
        BeanUtils.copyProperties(addressForm, address);

        // 插入到数据库;
        addressRepository.save(address);

        return address;
    }

    @Override
    @CacheEvict(cacheNames = "addressList", allEntries = true)
    public Address delete(Integer addressId) {
        Address address = addressRepository.findById(addressId).orElse(null);

        if (address == null) {
            throw new FleaException(ResponseEnum.ADDRESS_NOT_EXIST);
        }

        addressRepository.delete(address);
        return address;
    }

    @Override
    @CacheEvict(cacheNames = "addressList", allEntries = true)
    public Address update(AddressUpdateForm addressUpdateForm) {

        Address address = addressRepository.findById(addressUpdateForm.getAddressId()).orElse(null);
        if (address == null) {
            throw new FleaException(ResponseEnum.ADDRESS_NOT_EXIST);
        }

        BeanUtils.copyProperties(addressUpdateForm, address);

        addressRepository.save(address);

        return address;
    }

    /**
     * 构造地址的id -> 地址映射
     * @return id -> 地址的map
     */
    @Override
    @Cacheable(cacheNames = "addressList", key = "123")
    public Map<Integer, Address> getAddressList() {
        List<Address> addressList = addressRepository.findAll();

        return addressList.stream()
                .collect(Collectors.toMap(Address::getAddressId, address -> address));
    }

    @Override
    @Cacheable(cacheNames = "addressList", key = "124")
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> getFilter(AddressTypeEnum addressTypeEnum) {

        return addressRepository.findByAddressType(addressTypeEnum.getCode());
    }

    @Override
    @Cacheable(cacheNames = "addressList", key = "125")
    public Set<Address> getRegionList() {
        List<Address> addressList = addressRepository.findAll();

        return addressList.stream()
                .filter(e -> e.getAddressFloor() == null).collect(Collectors.toSet());
    }

}
