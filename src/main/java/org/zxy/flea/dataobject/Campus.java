package org.zxy.flea.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Campus implements Serializable {

    private static final long serialVersionUID = 4482519954067479494L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer campusId;

    private String campusName;

    private Date createTime;

    private Date updateTime;
}
