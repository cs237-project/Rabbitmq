package com.securityalertsystem.rabbitmq.config.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling //启动定时任务
public class TaskSchedulerConfig  implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler());
    }

    /**
     * 定时任务线程池
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public Executor taskScheduler(){
        return Executors.newScheduledThreadPool(100);
    }
}
