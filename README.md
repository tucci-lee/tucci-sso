此项目为同域名的sso（*.2cci.cn），多域名可自己扩展，如：携带token重定向、登陆成功后携带token访问其他域名，让其他域名存储cookie等\
[线上地址](http://sso.2cci.cn) \
client web演示地址：http://example.sso.2cci.cn/web \
client app演示地址：http://example.sso.2cci.cn/app 

**使用教程**
1. 编译sso-web项目
```bash
npm install
npm build
```

2. 将生成的前端项目放到sso-server下 
    - js css放到static目录下。由于线上java访问过慢，我放到了阿里云oss中，放到oss改一下index.html中的引入即可
    - index.html放到templates目录下

3. sso-server \
server端大部分配置都在配置文件中，修改一下即可。
为了方便文件存储、短信、邮箱都是用的阿里云服务

4. sso-core \
client端如果不使用springboot引入此依赖

5. sso-client-spring-boot-starter \
client端使用springboot引入此依赖自动装配（详细参数配置看example）

