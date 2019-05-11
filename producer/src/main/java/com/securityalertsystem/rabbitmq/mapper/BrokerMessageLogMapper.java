package com.securityalertsystem.rabbitmq.mapper;

import com.securityalertsystem.rabbitmq.entity.BrokerMessageLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BrokerMessageLogMapper {

    List<BrokerMessageLog> query4StatusAndTimeoutMessage();

    void update4ReSend(@Param("messageId")String messageId, @Param("updateTime") Date updateTime);

    void changeBrokerMessageLogStatus(@Param("messageId")String messageId, @Param("status")String status, @Param("updateTime")Date updateTime);

    int insertSelective(BrokerMessageLog record);
}