### FantasyBlog个人博客系统说明

#### 项目介绍

- 项目借鉴于https://github.com/AlanLiang1998的GeekBlog
- 使用当前流行的框架组合SpringBoot+Mybatis，同时整合MybatisPlus插件取代常用的CRUD，简化开发。
- 基于RBAC模型构建权限管理模块，并集成安全框架SpringSecurity，实现用户的认证和授权。
- 使用Spring Data集成缓存中间件Redis，加快访问速度。
- 使用Spring集成Quartz实现定时任务
- 使用SpringBoot的mail完成邮件发送
- 前台前端使用HTTP客户端Axios进行异步请求，使用Vue完成数据的绑定和渲染。
- 前台静态页面来自：Hexo博客Matery主题（项目地址：https://github.com/blinkfox/hexo-theme-matery)，后台模板来自layuimini（主页：http://layuimini.99php.cn/）。
- 页面使用响应式框架：Materialize（前台）和Layui（后台），支持电脑、平板、手机等所有主流设备访问。

#### 技术栈

##### 前端主要技术栈

- CSS框架：Materialize（前台）、Layui（后台）
- JS框架：Vue（前台）
- HTTP客户端：Axios
- 主要插件：Echarts（数据图表）、SweetAlert2（页面弹出层）、Editormd（Markdown编辑器）、Jqcloud（标签云）、moment（日期处理类库）、zyupload（文件上传）、Lazyload(图片懒加载)、aos（页面滚动动画）等等

##### 后端主要技术栈

- 主体框架：SpringBoot
- 持久层框架：Mybatis（整合MybatisPlus插件）
- 安全框架：SpringSecurity
- 缓存中间件：Redis
- 数据库：MySQL
- 数据库连接池：Druid
- 接口文档生成：Swagger2
- 模板引擎：Thymeleaf
- 日志框架：Logback
- 主要工具、插件：Lombok（简化POJO代码）、fastjson（JSON工具）、ip2region（IP查询地理位置）、hutool（Java工具集）、userAgentUtils（浏览器信息获取）、easy-captcha（图形验证码）等等

#### 演示地址

- 前台：http://www.fantasyblog.cn
- 后台：http://www.fantasyblog.admin.cn

#### 主要功能与模块

本博客系统分为前台和后台两个大模块，其中后台为博客系统管理页面，前台为用户界面。

##### 后台功能

后台主要包括以下几个功能：

- 登录功能：用户进入后台时要登录，需要输入用户名、密码和验证码，可以选择记住密码。

- 仪表盘页面：用户在仪表盘页面可以查看浏览量、评论量等各类数据的统计，系统公告，最近的访问日志和操作日志，最新的评论、留言和文章，最近7天的前后台浏览统计等。

- 用户管理：用户可以查看系统的用户信息，根据特定列进行排序，并可以输入用户名等信息进行搜索，可以添加新的用户、修改用户信息和用户状态、删除用户，可以导出和打印用户数据。

- 角色管理：用户可以查看系统的角色信息，根据特定列进行排序，并可以输入角色名等信息进行搜索，可以添加新的角色、修改角色信息和角色状态、删除角色，可以导出和打印角色数据。

- 菜单管理：用户可以查看系统的菜单信息，根据特定列进行排序，可以添加新的菜单、修改菜单信息、删除菜单。

- 访客管理：用户可以查看系统的访客信息，根据特定列进行排序，并可以输入用户名等信息进行搜索、可以修改访客状态、删除访客，可以导出和打印访客数据。

- 公告管理：用户可以查看系统的公告信息，根据特定列进行排序，并可以输入标题等信息进行搜索、可以修改公告信息、删除公告，可以导出和打印公告数据。

- 访问日志管理：用户可以查看系统的访问日志信息，根据特定列进行排序，并可以输入日期范围等信息进行搜索，可以删除访问日志，可以导出和打印访问日志数据。

- 操作日志管理：用户可以查看系统的操作日志信息，根据特定列进行排序，并可以输入日期范围等信息进行搜索，可以删除操作日志，可以导出和打印操作日志数据。

- 数据源监控功能：用户需要输入数据源的用户名和密码，可以查看数据源的相关信息。

- 本地存储管理：用户可以查看系统的本地存储的文件信息，根据特定列进行排序，并可以输入文件名等信息进行搜索，可以上传文件、修改文件信息、删除文件，可以导出和打印本地文件数据。

- 接口文档功能：开发人员可以查看系统的接口开发文档并对接口进行测试。

- 文章管理：用户可以查看系统的文章信息，根据特定列进行排序，并可以输入文章标题等信息进行搜索，可以发布新的文章、修改文章信息和发布状态、审核文章、删除文章，可以导出和打印文章数据。

- 分类管理：用户可以查看系统的分类信息，根据特定列进行排序，并可以输入分类名称等信息进行搜索，可以添加新的分类、修改分类信息、删除分类，可以导出和打印分类数据。

- 标签管理：用户可以查看系统的标签信息，根据特定列进行排序，并可以输入标签名称等信息进行搜索，可以添加新的标签、修改标签信息、删除标签，可以导出和打印标签数据。

- 评论管理：用户可以查看系统的评论信息，根据特定列进行排序，并可以输入日期范围等信息进行搜索，可以审核评论、回复评论、删除评论，可以导出和打印评论数据。

- 留言管理：用户可以查看系统的留言信息，根据特定列进行排序，并可以输入日期范围等信息进行搜索，可以审核留言、回复留言、删除留言，可以导出和打印留言数据。

- 友链管理：用户可以查看系统的友情链接信息，根据特定列进行排序，并可以输入日期范围等信息进行搜索，可以添加新的友链、修改友链信息、审核友链、删除留言，可以导出和打印友链数据。

- 相册管理：用户可以查看系统的照片信息，根据特定列进行排序，并可以输入日期范围等信息进行搜索，可以添加新的照片、修改照片信息、删除照片，可以导出和打印照片数据。

##### 前台功能

前台主要包括以下功能：

- 首页：访客可以查看博主的文章概览，包括置顶文章、推荐文章。

- 文章详情页：访客可以查看文章的详细内容，文章内容有目录，代码有高亮显示和语言显示并可点击复制。访客可以对文章进行点赞、分享和评论。

- 分类展示页：访客可以查看博主的文章分类和文章分类的雷达统计图，并按照文章分类查看文章。

- 标签展示页：访客可以查看博主的文章标签和文章标签生成的标签云统计图，并按照文章标签查看文章。

- 归档展示页：访客可以查看博主的文章日历统计图，并按照时间轴查看文章。

- 关于我展示页：访客可以查看博主的个人信息和联系方式，可以查看文章的统计信息、博主的技能展示和相册。

- 友情链接页：访客可以查看博客的友情链接，可以查看留言和对博客进行留言。

- 网站统计功能：网站底部显示访问量、访问人数和运行时间。

- 访客注册登录功能：访客评论文章前需要注册，评论文章时需要登录。

#### 数据库设计

数据库模型图如下：

![数据库模型](http://cdn.alanliang.site/datebase_model.jpg)

#### 设计模式

#### 联系方式

遇到问题可以添加联系本人，本人将热情为你解答。

QQ邮箱：geek_cy@qq.com