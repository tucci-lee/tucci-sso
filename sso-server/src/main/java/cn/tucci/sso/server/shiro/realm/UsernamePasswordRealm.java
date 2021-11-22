package cn.tucci.sso.server.shiro.realm;

import cn.tucci.sso.server.model.domain.UserAccount;
import cn.tucci.sso.server.service.UserAccountService;
import cn.tucci.sso.server.shiro.credential.BCryptCredentialsMatcher;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * 使用账号、邮箱、手机号和密码登陆
 *
 * @author tucci.lee
 */
@Component
public class UsernamePasswordRealm extends AuthorizingRealm {

    private final UserAccountService userAccountService;

    public UsernamePasswordRealm(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
        // 凭证校验器
        super.setCredentialsMatcher(new BCryptCredentialsMatcher());
    }

    /**
     * 授权
     *
     * @param principalCollection principal
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证：用户登陆
     *
     * @param token token
     * @return AuthenticationInfo
     * @throws AuthenticationException exception
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Wrapper<UserAccount> wrapper = Wrappers.lambdaQuery(UserAccount.class)
                .eq(UserAccount::getIsDeleted, Boolean.FALSE)
                .and(w -> w.eq(UserAccount::getUsername, token.getPrincipal())
                        .or().eq(UserAccount::getEmail, token.getPrincipal())
                        .or().eq(UserAccount::getPhone, token.getPrincipal()));

        UserAccount account = userAccountService.getOne(wrapper);

        if (account == null) {
            throw new UnknownAccountException();
        }
        if (account.getIsLock()) {
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(account.getUid(), account.getPassword(), getName());
    }

    /**
     * 多种登陆方式：当前realm处理的token类型
     *
     * @return Class
     */
    @Override
    public Class<? extends AuthenticationToken> getAuthenticationTokenClass() {
        return UsernamePasswordToken.class;
    }

}
