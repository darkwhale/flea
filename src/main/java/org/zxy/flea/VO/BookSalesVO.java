package org.zxy.flea.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookSalesVO {

    private String salesId;

    private String userId;

    private String salesName;

    private String synopsis;

    private String icon;

    private String salesCampus;

    private BigDecimal price;

    private Integer newLevel;

    private Integer status;

    private String items;

    private Integer rubTime;

    private Date createTime;

    private Date updateTime;
}
