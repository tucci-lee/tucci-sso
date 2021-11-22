package cn.tucci.sso.server.config;

import cn.tucci.sso.server.core.support.SnowFlake;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tucci.lee
 */
@Configuration
@MapperScan("cn.tucci.sso.server.model.dao")
public class MybatisPlusConfig {
    @Value("${snow-flake.work-id:0}")
    private Long workId;

    /**
     * 雪花算法生成主键
     *
     * @return
     */
    @Bean
    public SnowFlake snowFlake() {
        return new SnowFlake(workId);
    }

    /**
     * 自定义主键生成
     *
     * @param snowFlake
     * @return
     */
    @Bean
    public IdentifierGenerator identifierGenerator(SnowFlake snowFlake) {
        return entity -> snowFlake.nextId();
    }

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 插入，修改自动对相应的字段填充
     *
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createTime", Long.class, System.currentTimeMillis());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updatedTime", Long.class, System.currentTimeMillis()); // 起始版本 3.3.0(推荐)
            }

        };
    }
}
