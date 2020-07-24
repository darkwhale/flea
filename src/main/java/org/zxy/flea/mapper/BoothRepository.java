package org.zxy.flea.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.Booth;

public interface BoothRepository extends JpaRepository<Booth, String> {

    Booth findByUserId(String userId);

    Page<Booth> findAllByOrderByUpdateTimeDesc(Pageable pageable);
}
