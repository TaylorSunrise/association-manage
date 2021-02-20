package com.taylor.associationmanage.service.impl;

import com.taylor.associationmanage.entity.Location;
import com.taylor.associationmanage.mapper.LocationMapper;
import com.taylor.associationmanage.service.LocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动场地信息 服务实现类
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Service
public class LocationServiceImpl extends ServiceImpl<LocationMapper, Location> implements LocationService {

}
