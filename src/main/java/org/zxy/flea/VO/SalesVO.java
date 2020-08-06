package org.zxy.flea.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SalesVO {

    private String salesId;

    private String userId;

    private String salesName;

    private String synopsis;

    private String icon;

    private BigDecimal price;

    private Integer salesType;

    private Integer status;

    private Integer newLevel;

    private String items;

    private Date createTime;

    private Date updateTime;
}
