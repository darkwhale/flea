package org.zxy.flea.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.form.AddressForm;
import org.zxy.flea.form.AddressUpdateForm;
import org.zxy.flea.service.AddressService;
import org.zxy.flea.util.ResponseVOUtil;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController {

    @Resource
    private AddressService addressService;

    @PostMapping("/add")
    ResponseVO<Address> add(@Valid @RequestBody AddressForm addressForm) {
        Address address = addressService.add(addressForm);

        return ResponseVOUtil.success(address);
    }

    @PostMapping("/delete")
    ResponseVO<Address> delete(@RequestParam Integer addressId) {
        Address address = addressService.delete(addressId);

        return ResponseVOUtil.success(address);
    }

    @PostMapping("/update")
    ResponseVO<Address> update(@Valid @RequestBody AddressUpdateForm addressUpdateForm) {
        Address address = addressService.update(addressUpdateForm);

        return ResponseVOUtil.success(address);
    }

    @GetMapping("/floorList")
    ResponseVO<List<Address>> floorList() {
        List<Address> addressList = addressService.getAll();

        return ResponseVOUtil.success(addressList);
    }

    @GetMapping("/regionList")
    ResponseVO<Set<String>> regionList() {
        Set<String> addressList = addressService.getRegionList();

        return ResponseVOUtil.success(addressList);
    }
}
