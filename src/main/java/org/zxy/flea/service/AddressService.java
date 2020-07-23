package org.zxy.flea.service;

import org.zxy.flea.dataobject.Address;
import org.zxy.flea.form.AddressForm;
import org.zxy.flea.form.AddressUpdateForm;

import java.util.Map;

public interface AddressService {

    Address add(AddressForm addressForm);

    Address delete(Integer addressId);

    Address update(AddressUpdateForm addressUpdateForm);

    Map<Integer, Address> getAddressList();


}
