# Development manual
```python
├─main
│  ├─java
│  │  └─sustech
│  │      └─edu
│  │          └─phantom
│  │              └─dboj
│  │                  ├─basicJudge
│  │                  │  └─JudgeTestJson
│  │                  ├─config
│  │                  │  ├─security # spring security配置
│  │                  │  └─swagger # swagger 配置
│  │                  ├─controller # controller 层
│  │                  ├─entity # 实体类
│  │                  │  ├─dto # 
│  │                  │  └─vo # 视图层
│  │                  ├─form # 表单
│  │                  │  ├─advanced # 分级表单
│  │                  │  ├─home # 基础表单
│  │                  │  ├─modification # 修改表单
│  │                  │  └─stat # 数据表单
│  │                  ├─mapper # mybatis mapper
│  │                  ├─response # response 类
│  │                  │  └─serializer
│  │                  ├─service # 服务层
│  │                  └─utils # 实用类
│  └─resources
│      └─mapping # mybatis xml 
└─test
    └─java
        └─sustech
            └─edu
                └─phantom
                    └─dboj
                        └─service
```
## FINISHED

- 统一的响应对象

- 登录、注册2020/12/02


## TODO
   
2. 多权限

3. **消息队列**

4. 分组显示

5. 根据user中的权限来控制

6. 查询record的时候，要控制好代码的显示

7. 判题机独立出一个服务

8. 轮询

9. 定期刷新数据库

10. nosql redis 部署