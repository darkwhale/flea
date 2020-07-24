package org.zxy.flea.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.zxy.flea.dataobject.Campus;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.exception.FleaException;
import org.zxy.flea.form.CampusForm;
import org.zxy.flea.mapper.CampusRepository;
import org.zxy.flea.service.CampusService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CampusServiceImpl implements CampusService {

    @Resource
    private CampusRepository campusRepository;

    @Override
    @CacheEvict(cacheNames = "campus", key = "123")
    public Campus add(String campusName) {
        Campus campus = new Campus();
        campus.setCampusName(campusName);

        return campusRepository.save(campus);
    }

    @Override
    @CacheEvict(cacheNames = "campus", key = "123")
    public Campus update(CampusForm campusForm) {
        // 查询campus是否存在
        Campus campus = campusRepository.findById(campusForm.getCampusId()).orElse(null);

        if (campus == null) {
            throw new FleaException(ResponseEnum.CAPMUS_NOT_EXIST);
        }

        BeanUtils.copyProperties(campusForm, campus);

        // 这里不用查询campus是否存在；
        return campusRepository.save(campus);
    }

    @Override
    @CacheEvict(cacheNames = "campus", key = "123")
    public Campus delete(Integer campusId) {
        // 查询是否存在；
        Campus campus = campusRepository.findById(campusId).orElse(null);
        if (campus == null) {
            throw new FleaException(ResponseEnum.CAPMUS_NOT_EXIST);
        }

        campusRepository.delete(campus);

        return campus;
    }

    @Override
    @Cacheable(cacheNames = "campus", key = "123")
    public Map<Integer, Campus> getCampusMap() {
        List<Campus> campusList = campusRepository.findAll();

        return campusList.stream().collect(Collectors.toMap(Campus::getCampusId, campus -> campus));
    }
}
