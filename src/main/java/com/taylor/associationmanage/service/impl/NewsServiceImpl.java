package com.taylor.associationmanage.service.impl;

import com.taylor.associationmanage.entity.News;
import com.taylor.associationmanage.mapper.NewsMapper;
import com.taylor.associationmanage.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻公告信息 服务实现类
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

}
