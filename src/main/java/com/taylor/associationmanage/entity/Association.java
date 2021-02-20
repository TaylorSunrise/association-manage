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
 * 社团信息
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Association implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 社团ID
     */
    @TableId(value = "association_id", type = IdType.ID_WORKER)
    private Long associationId;

    /**
     * 社团名称
     */
    private String associationName;

    /**
     * 社团描述
     */
    private String associationDescription;

    /**
     * 社团类别ID
     */
    private Long associationCategory;

    /**
     * 成立用户ID
     */
    private Long userId;

    /**
     * 申请类型(0成立|1解散)
     */
    private Integer type;

    /**
     * 状态(0待审批|1同过|2拒绝)
     */
    private Integer status;

    /**
     * 审批时间
     */
    private Date approvalTime;

    /**
     * 审批人ID
     */
    private Long approvalId;

    /**
     * 社团负责人ID
     */
    private Long leaderId;

    /**
     * 报名状态
     */
    private Integer applyEnable;

    /**
     * 评优等级
     */
    private String appraising;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;


}
