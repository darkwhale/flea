package org.zxy.flea.service;

import org.zxy.flea.dataobject.Campus;
import org.zxy.flea.form.CampusForm;

import java.util.List;
import java.util.Map;

public interface CampusService {

    Campus add(String campusName);

    Campus update(CampusForm campusForm);

    Campus delete(Integer campusId);

    List<Campus> getList();

    Map<Integer, Campus> getCampusMap();

}
