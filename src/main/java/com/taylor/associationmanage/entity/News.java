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
 * 新闻公告信息
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 新闻ID
     */
    @TableId(value = "news_id", type = IdType.ID_WORKER)
    private Long newsId;

    /**
     * 社团ID
     */
    private Long associationId;

    /**
     * 新闻标题
     */
    private String newsTitle;

    /**
     * 摘要
     */
    private String newsDigest;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 作者
     */
    private String newsAuthor;

    /**
     * 内容
     */
    private String newsContent;

    /**
     * 状态
     */
    private Integer newsStatus;

    /**
     * 发布时间
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
