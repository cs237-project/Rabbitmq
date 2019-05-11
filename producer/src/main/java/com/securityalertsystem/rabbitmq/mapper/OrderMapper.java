package com.securityalertsystem.rabbitmq.mapper;

import com.securityalertsystem.rabbitmq.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    int insert(Order record);
    int deleteByPrimaryKey(Integer id);
    int insertSelective(Order record);
    Order selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Order record);
    int updateByPrimaryKey(Order record);
}
