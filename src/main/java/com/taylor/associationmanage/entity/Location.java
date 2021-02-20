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
 * 活动场地信息
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 场地ID
     */
    @TableId(value = "location_id", type = IdType.ID_WORKER)
    private Long locationId;

    /**
     * 场地名称
     */
    private String location;


}
