package org.zxy.flea.service.impl;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.BookBoothVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.dataobject.BookBooth;
import org.zxy.flea.dataobject.Campus;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.BookBoothForm;
import org.zxy.flea.mapper.BookBoothRepository;
import org.zxy.flea.service.BookBoothService;
import org.zxy.flea.util.ImageUtil;
import org.zxy.flea.util.KeyUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookBoothServiceImpl implements BookBoothService {

    @Resource
    private BookBoothRepository bookBoothRepository;

    @Resource
    private KeyUtil keyUtil;

    @Resource
    private AddressServiceImpl addressService;

    @Resource
    private CampusServiceImpl campusService;

    @Resource
    private SalesServiceImpl salesService;

    @Resource
    private AmqpTemplate amqpTemplate;


    @Override
    public BookBooth getBooth(String userId) {
        return bookBoothRepository.findByUserId(userId);
    }

    @Override
    public BookBooth modify(BookBoothForm bookBoothForm, String userId) {

        BookBooth bookBooth = bookBoothRepository.findByUserId(userId);

        // 为空则设置id和用户id;
        if (bookBooth == null) {
            bookBooth = new BookBooth();
            bookBooth.setBoothId(keyUtil.genUniqueKey());
            bookBooth.setUserId(userId);
        }

        BeanUtils.copyProperties(bookBoothForm, bookBooth);

        String imagePath = ImageUtil.saveImage("book", userId, bookBoothForm.getImage_info());


        if(imagePath != null) {
            bookBooth.setIcon(imagePath);
        }

        return bookBoothRepository.save(bookBooth);
    }

    @Override
    public BookBooth close(String userId) {
        BookBooth bookBooth = bookBoothRepository.findByUserId(userId);
        if (bookBooth == null) {
            throw new FleaException(ResponseEnum.BOOK_BOOTH_NOT_EXIST);
        }

        bookBoothRepository.delete(bookBooth);

        salesService.deleteAll(userId);

        // 消息队列，删除图像；
        String imagePath = FleaConst.IMAGE_DIR + bookBooth.getIcon();
        amqpTemplate.convertAndSend(FleaConst.AMQP_QUEUE, imagePath);

        return bookBooth;
    }

    @Override
    public BookBooth rub(String userId) {
        BookBooth bookBooth = bookBoothRepository.findByUserId(userId);
        if (bookBooth == null) {
            throw new FleaException(ResponseEnum.BOOK_BOOTH_NOT_EXIST);
        }

        rubSelf(bookBooth);
        return bookBoothRepository.save(bookBooth);
    }

//    @Override
//    public List<BookBooth> getBoothList(List<String> userIdList) {
//        return bookBoothRepository.findAllByUserIdIn(userIdList);
//    }
//
//    @Override
//    public Page<BookBooth> getBoothList(String boothName, Pageable pageable) {
//        return bookBoothRepository.findByBoothNameLike("%" + boothName + "%", pageable);
//    }

    @Override
    public Page<BookBooth> getBoothList(Pageable pageable) {
        return bookBoothRepository.findAllByOrderByUpdateTimeDesc(pageable);
    }

    @Override
    public Page<BookBooth> getBoothListByCampus(Integer campusId, Pageable pageable) {
        return bookBoothRepository.findAllByCampusIdOrderByUpdateTimeDesc(campusId, pageable);
    }

    @Override
    public Page<BookBooth> getBoothListByAddress(Integer addressId, Pageable pageable) {
        return bookBoothRepository.findAllByAddressIdOrderByUpdateTimeDesc(addressId, pageable);
    }

    @Override
    public Page<BookBooth> getBoothList(Integer campusId, Integer addressId, Pageable pageable) {
        return bookBoothRepository.findAllByCampusIdAndAddressIdOrderByUpdateTimeDesc(campusId, addressId, pageable);
    }

    @Override
    public BookBoothVO converter(BookBooth bookBooth) {

        if (bookBooth == null) {
            return null;
        }

        BookBoothVO bookBoothVO = new BookBoothVO();
        BeanUtils.copyProperties(bookBooth, bookBoothVO);

        // 获取地址列表
        Map<Integer, Address> addressMap = addressService.getAddressList();

        // 获取学院列表
        Map<Integer, Campus> campusMap = campusService.getCampusMap();

        if (bookBooth.getCampusId() != null) {
            bookBoothVO.setBoothCampus(campusMap.get(bookBooth.getCampusId()).getCampusName());
        }

        if (bookBooth.getAddressId() != null) {
            bookBoothVO.setAddress(addressMap.get(bookBooth.getAddressId()).toString());
        }

        return bookBoothVO;
    }

    @Override
    public Page<BookBoothVO> converter(Page<BookBooth> bookBoothPage, Pageable pageable) {
        List<BookBoothVO> bookBoothVOList = bookBoothPage.getContent().stream().map(
                this::converter
        ).collect(Collectors.toList());

        return new PageImpl<>(bookBoothVOList, pageable, bookBoothPage.getTotalElements());
    }

    private void rubSelf(BookBooth bookBooth) {
        bookBooth.setRubTime(bookBooth.getRubTime() + 1);
    }
}
