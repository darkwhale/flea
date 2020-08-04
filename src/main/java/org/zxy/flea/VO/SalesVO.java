package org.zxy.flea.VO;

import lombok.Data;

import java.util.Date;

@Data
public class SalesVO {

    private String salesId;

    private String userId;

    private String salesName;

    private String synopsis;

    private String icon;

    private Integer salesType;

    private Integer newLevel = 10;

    private String items;

    private Date createTime;

    private Date updateTime;
}
