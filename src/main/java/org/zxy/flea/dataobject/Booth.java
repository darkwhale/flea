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
public class Booth {

    @Id
    private String boothId;

    private String userId;

    private Integer boothType;

    private Integer addressId;

    private String boothName;

    private String synopsis;

    private Integer rubTime;

    private Date createTime;

    private Date updateTime;
}
