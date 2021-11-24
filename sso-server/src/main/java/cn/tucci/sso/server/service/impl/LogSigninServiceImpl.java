package cn.tucci.sso.server.service.impl;

import cn.tucci.sso.server.model.dao.LogSigninMapper;
import cn.tucci.sso.server.model.domain.LogSignin;
import cn.tucci.sso.server.service.LogSigninService;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class LogSigninServiceImpl implements LogSigninService {

    private final LogSigninMapper logSigninMapper;

    public LogSigninServiceImpl(LogSigninMapper logSigninMapper) {
        this.logSigninMapper = logSigninMapper;
    }

    @Override
    public void add(LogSignin log) {
        logSigninMapper.insert(log);
    }
}
