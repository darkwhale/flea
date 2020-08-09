package org.zxy.flea.service;

import org.zxy.flea.dataobject.Address;
import org.zxy.flea.enums.AddressTypeEnum;
import org.zxy.flea.form.AddressForm;
import org.zxy.flea.form.AddressUpdateForm;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AddressService {

    Address add(AddressForm addressForm);

    Address delete(Integer addressId);

    Address update(AddressUpdateForm addressUpdateForm);

    Map<Integer, Address> getAddressList();

    List<Address> getAll();

    List<Address> getFilter(AddressTypeEnum addressTypeEnum);

    Set<Address> getRegionList();

    Set<Address> getNonRegionList();
}
