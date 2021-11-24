package cn.tucci.sso.server.service.impl;

import cn.tucci.sso.server.model.dao.RegionMapper;
import cn.tucci.sso.server.model.domain.Region;
import cn.tucci.sso.server.service.RegionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tucci.lee
 */
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionMapper regionMapper;

    public RegionServiceImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public List<Region> listByParentId(String parentId) {
        Wrapper<Region> wrapper = Wrappers.lambdaQuery(Region.class)
                .eq(Region::getParentId, parentId);
        return regionMapper.selectList(wrapper);
    }
}
