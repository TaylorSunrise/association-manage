package com.taylor.associationmanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 点赞信息
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Like implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 点赞ID
     */
    @TableId(value = "like_id", type = IdType.ID_WORKER)
    private Long likeId;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 点赞人Id
     */
    private Long userId;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;


}
