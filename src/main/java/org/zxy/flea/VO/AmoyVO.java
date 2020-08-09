package org.zxy.flea.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AmoyVO {

    private Integer salesType;

    private String salesId;

    private String userId;

    private String salesName;

    private String synopsis;

    private String icon;

    private String salesCampus;

    private String salesAddress;

    private BigDecimal price;

    private Integer newLevel;

    private Integer status;

    private String items;

    private Integer rubTime;

}
