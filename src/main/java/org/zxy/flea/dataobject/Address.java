package org.zxy.flea.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.zxy.flea.enums.ResponseEnum;
import org.zxy.flea.exception.FleaException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Address implements Serializable, Comparable {

    private static final long serialVersionUID = 5645228902910371151L;

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

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        if (addressFloor != null) {
            return addressRegion + " " + addressFloor;
        }else {
            return addressRegion;
        }
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) {
            return 0;
        }
        if (o instanceof Address) {
            Address other = (Address) o;
            if(this.addressRegion.compareTo(other.addressRegion) == 0) {
                if (this.addressFloor == null) {
                    return -1;
                }else if (other.addressFloor == null) {
                    return 1;
                }else {
                    return this.addressFloor.compareTo(other.addressFloor);
                }
            }
            return this.addressRegion.compareTo(other.addressRegion);
        }
        throw new FleaException(ResponseEnum.TYPE_ERROR);
    }
}
