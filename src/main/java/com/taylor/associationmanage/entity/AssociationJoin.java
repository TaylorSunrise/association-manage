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
 * 加入社团、退出社团信息
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AssociationJoin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 申请ID
     */
    @TableId(value = "apply_id", type = IdType.ID_WORKER)
    private Long applyId;

    /**
     * 社团ID
     */
    private String associationId;

    /**
     * 申请用户ID
     */
    private Long userId;

    /**
     * 申请类型(加入|退出)
     */
    private String type;

    /**
     * 部门
     */
    private String department;

    /**
     * 简要说明
     */
    private String description;

    /**
     * 状态(0待审批|1批准|2拒绝)
     */
    private Integer status;

    /**
     * 审批人ID
     */
    private Long approvalId;

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
     * 加入时间
     */
    private Date joinTime;

    /**
     * 退出时间
     */
    private Date outTime;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;


}
