package com.nest.admin.tools.produce;

import com.nest.admin.config.AutoDataJdbcConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wzp on 2018/5/19.
 */
@Configuration
@AutoConfigureAfter(AutoDataJdbcConfig.class)
public class AutoProduceConfig {

}
