package com.tw.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tw.blog.pojo.TUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * by TanWei 2021/1/5
 **/
@Mapper
public interface UserMapper extends BaseMapper<TUser> {
}
