package org.zxy.flea.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.zxy.flea.enums.SalesStatusEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class WaresSales {

    @Id
    private String salesId;

    private String userId;

    private String salesName;

    private String synopsis;

    private String icon;

    private Integer salesAddressId;

    private BigDecimal price;

    private Integer newLevel = 10;

    private Integer status = SalesStatusEnum.ON_SALE.getCode();

    private String items;

    private Integer rubTime = 0;

    private Date createTime;

    private Date updateTime;


}
