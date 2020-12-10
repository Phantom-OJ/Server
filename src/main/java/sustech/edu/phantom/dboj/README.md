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

- 显示该题是否已经AC/WA/NO submission 2020/12/08


## TODO
   
2. 多权限

3. **消息队列**

4. 分组显示

5. 根据user中的权限来控制

6. 查询record的时候，要控制好代码的显示

7. 判题机独立出一个服务 -- mzy

8. 轮询

9. 定期刷新数据库 [resolved]

10. nosql redis 部署

11. 头像更改 存储路径

12. 枚举类，permission 常量， response message [Solved]

13. 用户界面返回用户自己的数据 [resolved]

14. 针对老师，要显示所有的内容，包含private的内容 [Solved]

15. 针对上传的文件，进行安全检查

16. 检查上传文件的后缀名

17. 编辑作业 问题 返回全部信息 

18. group

19. 单点登录 这里可能要用到redis
    
20. 管理员单独的接口 [没必要]

21. problem和assignment的public private closed 权限[算是solved]

22. profile 里面放得分

23. assignment 数据 每一个题有多少人ac了，一共要ac多少人，交了没ac，ac了，没交，总数 


judge script: text

judge database: json text