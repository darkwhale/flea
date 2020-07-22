package org.zxy.flea.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
