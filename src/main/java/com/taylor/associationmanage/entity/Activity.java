package com.taylor.associationmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 活动申请表
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    @TableId(value = "activity_id", type = IdType.ID_WORKER)
    private Long activityId;

    /**
     * 社团id
     */
    private Long associationId;

    /**
     * 活动名称
     */
    private String activityTitle;

    /**
     * 活动内容
     */
    private String activityContent;

    /**
     * 活动标签
     */
    private String activityLabel;

    /**
     * 活动人数
     */
    private Integer maxApply;

    /**
     * 状态(0待审批|1通过|2拒绝|撤销)
     */
    private Integer status;

    /**
     * 审批人id
     */
    private Long approvalId;

    /**
     * 审批意见
     */
    private String remark;

    /**
     * 申请人id
     */
    private Long userId;

    /**
     * 场地id
     */
    private Long locationId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 申请时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 审批时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;


}
