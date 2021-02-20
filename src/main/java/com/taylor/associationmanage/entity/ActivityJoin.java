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
 * 参加活动
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ActivityJoin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动列表id
     */
    @TableId(value = "join_id", type = IdType.ID_WORKER)
    private Long joinId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 状态(0待审批|1批准|2拒绝)
     */
    private Integer status;

    /**
     * 申请时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 审批人ID
     */
    private Long approvalId;

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
