package com.taylor.associationmanage.service.impl;

import com.taylor.associationmanage.entity.Like;
import com.taylor.associationmanage.mapper.LikeMapper;
import com.taylor.associationmanage.service.LikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 点赞信息 服务实现类
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {

}
