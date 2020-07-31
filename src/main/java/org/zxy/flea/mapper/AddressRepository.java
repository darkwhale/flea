package org.zxy.flea.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findByAddressType(Integer addressType);
}
