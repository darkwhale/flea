package org.zxy.flea.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zxy.flea.VO.WaresBoothVO;
import org.zxy.flea.consts.FleaConst;
import org.zxy.flea.dataobject.Address;
import org.zxy.flea.dataobject.WaresBooth;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.WaresBoothForm;
import org.zxy.flea.mapper.WaresBoothRepository;
import org.zxy.flea.service.WaresBoothService;
import org.zxy.flea.util.ImageUtil;
import org.zxy.flea.util.KeyUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WaresBoothServiceImpl implements WaresBoothService {

    @Resource
    private WaresBoothRepository waresBoothRepository;

    @Resource
    private KeyUtil keyUtil;

    @Resource
    private AddressServiceImpl addressService;

    @Resource
    private SalesServiceImpl salesService;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public WaresBooth getBooth(String userId) {
        return waresBoothRepository.findByUserId(userId);
    }

    @Override
    public WaresBooth modify(WaresBoothForm waresBoothForm, String userId) {

        WaresBooth waresBooth = waresBoothRepository.findByUserId(userId);

        if (waresBooth == null) {
            waresBooth = new WaresBooth();

            waresBooth.setBoothId(keyUtil.genUniqueKey());
            waresBooth.setUserId(userId);
        }

        BeanUtils.copyProperties(waresBoothForm, waresBooth);

        // 存储图像
        String imagePath = ImageUtil.saveImage("wares", userId, waresBoothForm.getImage_info());
        if(imagePath != null) {
            waresBooth.setIcon(imagePath);
        }

        return waresBoothRepository.save(waresBooth);
    }

    @Override
    public WaresBooth close(String userId) {
        WaresBooth waresBooth = waresBoothRepository.findByUserId(userId);
        if (waresBooth == null) {
            throw new FleaException(ResponseEnum.WARES_BOOTH_NOT_EXIST);
        }

        waresBoothRepository.delete(waresBooth);

        salesService.deleteAll(userId);

        String imagePath = FleaConst.IMAGE_DIR + waresBooth.getIcon();
        amqpTemplate.convertAndSend(FleaConst.AMQP_QUEUE, imagePath);

        return waresBooth;
    }

    @Override
    public WaresBooth rub(String userId) {

        WaresBooth waresBooth = waresBoothRepository.findByUserId(userId);
        if (waresBooth == null) {
            throw new FleaException(ResponseEnum.WARES_BOOTH_NOT_EXIST);
        }

        rubSelf(waresBooth);

        return waresBoothRepository.save(waresBooth);
    }

    @Override
    public Page<WaresBoothVO> converter(Page<WaresBooth> waresBoothPage, Pageable pageable) {
        List<WaresBoothVO> waresBoothVOList = waresBoothPage.getContent().stream().map(
                this::converter
        ).collect(Collectors.toList());
        return new PageImpl<>(waresBoothVOList, pageable, waresBoothPage.getTotalElements());
    }

    @Override
    public WaresBoothVO converter(WaresBooth waresBooth) {

        if (waresBooth == null) {
            return null;
        }

        WaresBoothVO waresBoothVO = new WaresBoothVO();
        BeanUtils.copyProperties(waresBooth, waresBoothVO);

        // 获取地址列表
        Map<Integer, Address> addressMap = addressService.getAddressList();

        if (waresBooth.getAddressId() != null) {
            waresBoothVO.setAddress(addressMap.get(waresBooth.getAddressId()).toString());
        }

        return waresBoothVO;

    }

    @Override
    public Page<WaresBooth> getBoothList(Pageable pageable) {
        return waresBoothRepository.findAllByOrderByUpdateTimeDesc(pageable);
    }

    @Override
    public Page<WaresBooth> getBoothListByCampus(Integer addressId, Pageable pageable) {
        return waresBoothRepository.findAllByAddressIdOrderByUpdateTimeDesc(addressId, pageable);
    }

    private void rubSelf(WaresBooth waresBooth) {
        waresBooth.setRubTime(waresBooth.getRubTime() + 1);
    }

}
