package com.taylor.associationmanage.service.impl;

import com.taylor.associationmanage.entity.Activity;
import com.taylor.associationmanage.mapper.ActivityMapper;
import com.taylor.associationmanage.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动申请表 服务实现类
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

}
