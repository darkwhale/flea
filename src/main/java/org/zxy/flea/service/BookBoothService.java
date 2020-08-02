package org.zxy.flea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zxy.flea.VO.BookBoothVO;
import org.zxy.flea.dataobject.BookBooth;
import org.zxy.flea.form.BookBoothForm;

import java.util.List;

public interface BookBoothService {

    BookBooth getBooth(String userId);

    BookBooth modify(BookBoothForm bookBoothForm, String userId);

    BookBooth close(String userId);

    BookBooth rub(String userId);

    List<BookBooth> getBoothList(List<String> userIdList);

    Page<BookBooth> getBoothList(String boothName, Pageable pageable);

    Page<BookBooth> getBoothList(Pageable pageable);

    Page<BookBooth> getBoothListByCampus(Integer campusId, Pageable pageable);

    Page<BookBooth> getBoothListByAddress(Integer addressId, Pageable pageable);

    Page<BookBooth> getBoothList(Integer campusId, Integer addressId, Pageable pageable);

    BookBoothVO converter(BookBooth bookBooth);

    Page<BookBoothVO> converter(Page<BookBooth> bookBoothPage, Pageable pageable);
}
