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
 * 经费信息
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Expendture implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "expendture_id", type = IdType.ID_WORKER)
    private Long expendtureId;

    /**
     * 社团id
     */
    private Long associationId;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 经费
     */
    private Integer expendture;

    /**
     * 状态(0待审批|1批准|2拒绝)
     */
    private Integer status;

    /**
     * 审批人id
     */
    private Long approvalId;

    /**
     * 申请人id
     */
    private Long userId;

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
