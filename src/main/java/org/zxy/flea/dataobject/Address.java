package org.zxy.flea.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Address {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    /**
     * 地址类型：0宿舍地址，1上课地址；
     */
    private Integer addressType;

    /**
     * 地址区域，比如：梅园
     */
    private String addressRegion;

    /**
     * 地址建筑标识，例如：梅园4/5舍
     */
    private String addressFloor;

}
