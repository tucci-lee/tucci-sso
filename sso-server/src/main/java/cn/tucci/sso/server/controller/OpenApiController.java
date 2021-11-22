package cn.tucci.sso.server.controller;

import cn.tucci.sso.server.core.response.Result;
import cn.tucci.sso.server.model.domain.Region;
import cn.tucci.sso.server.model.query.RegionQuery;
import cn.tucci.sso.server.service.RegionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("/open")
public class OpenApiController {

    private final RegionService regionService;

    public OpenApiController(RegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * 获取地区信息
     *
     * @param query 查询条件
     * @return 地区信息列表
     */
    @GetMapping("region")
    public Result<?> list(@Validated RegionQuery query) {
        Wrapper<Region> wrapper = Wrappers.lambdaQuery(Region.class)
                .eq(Region::getParentId, query.getParentId());
        List<Region> regions = regionService.list(wrapper);
        return Result.ok(regions);
    }
}
