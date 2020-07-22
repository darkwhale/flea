package org.zxy.flea.service;

import org.zxy.flea.VO.ResponseVO;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.form.AddressForm;

public interface AddressService {

    ResponseVO<Address> add(AddressForm addressForm);

    ResponseVO<Address> delete(Integer addressId);

}
