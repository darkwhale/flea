package org.zxy.flea.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zxy.flea.dataobject.BookBooth;

import java.util.List;

public interface BookBoothRepository extends JpaRepository<BookBooth, String> {

    BookBooth findByUserId(String userId);

    List<BookBooth> findAllByUserIdIn(List<String> userIdList);

    Page<BookBooth> findAllByOrderByUpdateTimeDesc(Pageable pageable);

    Page<BookBooth> findByBoothNameLike(String boothName, Pageable pageable);

    /**
     * 根据专业查询
     */
    Page<BookBooth> findAllByCampusIdOrderByUpdateTimeDesc(Integer campusId, Pageable pageable);

    /**
     * 根据地址查询
     */
    Page<BookBooth> findAllByAddressIdOrderByUpdateTimeDesc(Integer addressId, Pageable pageable);

    /**
     * 联合查询
     */
    Page<BookBooth> findAllByCampusIdAndAddressIdOrderByUpdateTimeDesc(Integer campusId, Integer addressId, Pageable pageable);

}
