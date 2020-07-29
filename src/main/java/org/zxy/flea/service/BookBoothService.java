package org.zxy.flea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zxy.flea.dataobject.BookBooth;
import org.zxy.flea.form.BookBoothForm;

import java.util.List;

public interface BookBoothService {

    BookBooth getBooth(String userId);

    BookBooth create(BookBoothForm bookBoothForm, String userId);

    BookBooth update(BookBoothForm bookBoothForm, String userId);

    BookBooth close(String userId);

    List<BookBooth> getBoothList(List<String> userIdList);

    Page<BookBooth> getBoothList(String boothName, Pageable pageable);

    Page<BookBooth> getBoothList(Pageable pageable);

    Page<BookBooth> getBoothListByCampus(Integer campusId, Pageable pageable);

    Page<BookBooth> getBoothListByAddress(Integer addressId, Pageable pageable);

    Page<BookBooth> getBoothList(Integer campusId, Integer addressId, Pageable pageable);
}