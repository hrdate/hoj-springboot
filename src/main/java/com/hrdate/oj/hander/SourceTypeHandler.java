package com.hrdate.oj.hander;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;

/**
 * 自定义处理source和TypeHandler
 *
 * @author jieguangzhi
 * @date 2021-10-16
 */
public class SourceTypeHandler extends JacksonTypeHandler {

    public SourceTypeHandler(Class<?> type) {
        super(type);
    }

    @Override
    protected Object parse(String json) {
        try {
            return getObjectMapper().readValue(json, new TypeReference<List<Source>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class  Source{
    Source(){};
}
