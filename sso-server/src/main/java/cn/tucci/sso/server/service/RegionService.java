package cn.tucci.sso.server.service;

import cn.tucci.sso.server.model.domain.Region;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface RegionService {

    List<Region> listByParentId(String parentId);
}
