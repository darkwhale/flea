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
public class Sales {

    @Id
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
