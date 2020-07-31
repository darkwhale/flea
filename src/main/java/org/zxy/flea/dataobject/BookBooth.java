package org.zxy.flea.dataobject;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class BookBooth {

    @Id
    private String boothId;

    private String userId;

    private Integer addressId;

    private Integer campusId;

    private String icon;

    private String boothName;

    private String synopsis;

    private Integer rubTime = 0;

    private Date createTime;

    private Date updateTime;
}
