package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
@Configuration
@ConfigurationProperties(prefix = "nacos")//需要增加的
public class NacosConfig {


    //需要自己增加的 Nacos 地址
    private String addr;

    //需要自己增加的 Nacos 地址
    private String namespace;

    //需要自己增加的
    public String getAddr() {
        return addr;
    }

    //需要自己增加的
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    //需要自己增加的
    public String getNamespace() {
        return namespace;
    }

    //需要自己增加的
    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    /*@Bean
    public ConfigService nacosConfigService() throws Exception {
        return ConfigFactory.createConfigService("localhost");
    }*/

    //修改后的注入 ConfigService 方法
    @Bean
    public ConfigService nacosConfigService() throws Exception {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, addr);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        return ConfigFactory.createConfigService(properties);
    }
}

