package org.zxy.flea.dataobject;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.zxy.flea.enums.GenderEnum;
import org.zxy.flea.enums.GradeEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class UserInfo {

    @Id
    private String userInfoId;

    private String userEmail;

    private Integer userCampusId;

    private Integer userGender = GenderEnum.Male.getCode();

    private String userMobile;

    private String userQq;

    private String userWx;

    private Integer userResideAddressId;

    private Integer userStudyAddressId;

    private Integer userGrade = GradeEnum.Grade_1.getCode();

    private Date createTime;

    private Date updateTime;


}
