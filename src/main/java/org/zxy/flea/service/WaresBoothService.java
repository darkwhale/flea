package org.zxy.flea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zxy.flea.VO.WaresBoothVO;
import org.zxy.flea.dataobject.WaresBooth;
import org.zxy.flea.form.WaresBoothForm;

public interface WaresBoothService {

    WaresBooth getBooth(String userId);

    WaresBooth modify(WaresBoothForm waresBoothForm, String userId);

    WaresBooth close(String userId);

    WaresBooth rub(String userId);

    WaresBoothVO converter(WaresBooth waresBooth);

    Page<WaresBoothVO> converter(Page<WaresBooth> waresBoothPage, Pageable pageable);


    Page<WaresBooth> getBoothList(Pageable pageable);

    Page<WaresBooth> getBoothListByCampus(Integer addressId, Pageable pageable);

}
