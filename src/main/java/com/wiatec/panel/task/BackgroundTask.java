package com.wiatec.panel.task;

import com.wiatec.panel.common.utils.ApplicationContextHelper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author patrick
 */
@Component
public abstract class BackgroundTask {

    private final Logger logger = LoggerFactory.getLogger(BackgroundTask.class);

    protected static SqlSession sqlSession;

    static {
        sqlSession = (SqlSession) ApplicationContextHelper.getApplicationContext().getBean("sqlSessionTemplate");
    }
}
