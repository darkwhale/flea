package org.zxy.flea.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class User {

    @Id
    private String userId;

    private String username;

    private String password;

    private String email;

    private Integer role;

    private Date createTime;

    private Date updateTime;
}
