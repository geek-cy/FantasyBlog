### Spring
Spring 官网列出的 Spring 的 6 个特征
1、核心技术：依赖注入(DI)，AOP，事件(events)，资源，i18n，验证，数据绑定，类型转换，SpEL。
2、测试：模拟对象，TestContext框架，Spring MVC 测试，WebTestClient。
3、数据访问：事务，DAO支持，JDBC，ORM，编组XML。
4、Web支持: Spring MVC和Spring WebFlux Web框架。
5、集成：远程处理，JMS，JCA，JMX，电子邮件，任务，调度，缓存。
6、语言：Kotlin，Groovy，动态语言。

### 主要模块
Spring Core：基础,可以说 Spring 其他所有的功能都需要依赖于该类库。主要提供 IoC 依赖注入功能。
Spring Aspects：该模块为与AspectJ的集成提供支持。
Spring AOP：提供了面向切面的编程实现。
Spring JDBC: Java数据库连接。
Spring JMS：Java消息服务。
Spring ORM: 用于支持Hibernate等ORM工具。
Spring Web: 为创建Web应用程序提供支持。
Spring Test: 提供了对 JUnit 和 TestNG 测试的支持。

### SpringMVC 工作流程
对于前后端不分离的情况是单独使用Controller返回一个页面
![](https://cdn.jsdelivr.net/gh/geek-cy/img/Spring/b0f0310873a7dec2b7f433ff8094b919.png)

对于前后端分离使用RestController返回对象，对象数据以JSON或XML形式写入HTTP响应，这种情况属于RESTful Web服务
ResponseBody注解作用是将Controller的方法返回的对象通过适当的转换器转换为指定的格式后写入HTTP响应对象的body中，用来返回JSON或XML数据
![](https://cdn.jsdelivr.net/gh/geek-cy/img/Spring/1f40a6f48f8338ea25963a6dd368fded.png)

### Spring IOC
IOC是一种设计思想，将原本在程序中手动创建对象的控制权交由Spring框架来管理。IOC并非Spring特有。IOC容器是Spring用来实现IoC的载体，实际上就是个Map,Map中存放的是各种对象
将对象之间的相互依赖关系交给IOC容器来管理，并由IOC容器完成对象的注入。这样可以很大程度上将应用从复杂的依赖关系中解放出来。

IOC容器底层就是对象工厂
#### IOC流程
```
//第一步 xml配置文件，配置创建的对象
<bean id="dao" class = "com.atguigu.UserDao"></bean>
// 第二步 创建工厂类
class UserFactory{
    public static UserDao getDao(){
        String classValue = class属性值;// xml解析
        Class clazz = Class.forName(classValue);// 通过反射创建对象
        return (UserDao)clazz.newInstance();
    }
}
```
#### 实现方式
1、BeanFactory:是Spring内部使用的接口，开发人员一般不用
2、ApplicationContext:BeanFactory的子接口，一般由开发人员使用

![](https://cdn.jsdelivr.net/gh/geek-cy/img/Spring/57da0deca924d0e73dbb56501d2ec4be.png)

### AOP
将那些与业务无关却为业务模块所共同调用的逻辑或责任（事务处理、日志管理、权限控制）封装起来，减少系统的重复代码，降低耦合度

Spring AOP是基于动态代理的，若要代理的对象实现了某接口，就会使用JDK Proxy去创建代理对象，若要代理的对象没实现接口，就会使用CGLib Proxy生成一个被代理对象的子类作为代理

AspectJ 基于字节码操作，相比Spring AOP功能更加强大，但是相对复杂，当切面较多时候选择AspectJ,速度快于Spring AOP

### Spring Bean
Bean就是由IOC实例化、组装、管理的一个对象

Bean管理操作有两种方式：基于xml配置文件方式实现，基于注解方式实现

#### Bean的创建
在Spring配置文件中，使用bean标签，标签里面添加对应属性就可以实现对象创建（创建对象时候默认是执行无参构造方法创建）

#### 注入属性
DI:依赖注入，即用set和有参构造进行属性注入

而在Spring中可用xml方式进行属性注入
```
<bean id="book" class="...">
    <property name="类里的属性名称" value="注入的值"></property>
    <constructor-arg name="类里的属性名称" value="注入的值"></constructor-arg>
</bean>

<!--实现自动装配,byName根据属性名注入,byType根据类型注入-->
<bean id="..." class="" autowire="byName/byType">

//1、加载Spring配置文件
ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
//2、获取配置创建的对象
Book book = context.getBean("book",Book.class);
```

注解
#### Bean的作用域
1、singleton: Spring中的bean默认都是单例的
2、prototype: 每次请求都会创建一个新的bean实例
3、request: 每一次HTTP请求都会产生一个新的bean,该bean仅在当前HTTP request内有效
4、session: 每一次HTTP请求都会产生一个新的bean,该bean仅在当前HTTP session内有效
5、global-session: 全局session作用域，仅仅在基于portlet的web应用中才有意义，Spring5已经取消

#### Spring中单例Bean的线程安全问题
当多个线程操作同一个对象时，对这个对象的非静态成员变量的写操作会存在线程安全问题

解决办法：
1、在Bean对象中尽量避免定义可变的成员变量(不太现实)
2、在类中定义一个ThreadLocal成员变量，将需要的可变成员变量保存在ThreadLocal中

#### @Component 和 @Bean 的区别
1、作用对象不同：@Component 作用于类,而 @Bean 作用于方法  
2、@Component 通常是通过类路径扫描来自动侦测以及自动装配到Spring容器中；@Bean 通常是我们在标有该注解的方法中定义产生这个bean，告诉Spring这是某个类的实例，当我们需要用它的时候给我  
3、@Bean 比 @Component 自定义性更强，比如当我们引用第三方库中的类需要装配到 Spring 容器时，只能通过Bean注解

示例:
```
@Configuration
public class AppConfig{
    @Bean
    public TransferService transferService(){
        return new TransferServiceImpl();
    }
}

//相当于
<beans>
    <bean id="transferService" class="com.acme.TransferServiceImpl"/>
</beans>
```

#### 基于注解方式实现属性注入
首先要在xml中开启组件扫描或者类上有@Configuration即用配置类替代配置文件
1、@Autowired: 根据属性类型注入
2、@Qualifier(value = "name"): 根据名称注入
3、@Resource: 既可以根据类型也可以根据名称注入
4、@Value: 注入普通类型

#### Bean的生命周期
1、通过构造器创建bean实例
2、为bean属性设置值和对其他bean引用(调用set方法)
3、把bean实例传递给后置处理器
4、调用bean的初始化的方法(需要进行配置初始化方法)
5、把bean实例传递给后置处理器
6、bean获取到后就可以使用了
7、当容器关闭时，调用bean的销毁方法(需要进行配置销毁的方法)

### SpringMVC(Model、View、Controller)

### SpringMVC流程
![](https://cdn.jsdelivr.net/gh/geek-cy/img/Spring/093258b80bf44a737cdc3304ceea85d7.png)
1、客户端发送请求到DispatcherServlet
2、DispathcerServlet根据请求信息调用HandlerMapping，解析请求对应的handler
3、解析到对应的Handler(即Controller)后，开始由HandlerAdapter适配器处理
4、HandlerAdapter会根据Handler调用真正的处理器处理请求
5、处理完业务后会返回一个ModerAndView对象，Model是返回的数据对象，View是逻辑上的View
6、ViewResolver会根据逻辑View查找实际的View
7、DispaterServlet会返回的Model传给View(视图渲染)
8、把View返回给请求者

### Spring事务
Spring管理事务的方式有两种：编程式事务（在代码中编码，不推荐使用）和声明式事务（配置文件中配置，推荐使用）

声明式事务分为两种：基于XML和基于注解

#### Spring事务隔离级别
在TransactionDefinition接口中定义了五个表示隔离级别的常量:
1、ISOLATION_DEFAULT: 使用后端数据库默认的隔离级别
2、ISOLATION_READ_UNCOMMITTED: 最低的隔离级别，允许读取尚未提交的数据变更，容易导致脏读、幻读或不可重复读
3、ISOLATION_READ_COMMITTED: 允许读取已经提交的数据，可以阻止脏读
4、ISOLATION_REPEATABLE_READ: 对同一字段的多次读取结果是一致的，可以阻止脏读和不可重复读
5、ISOLATION_SERIALIZABLE: 最高隔离级别，所有事务依次逐个执行，但严重影响性能

#### Spring事务传播行为(外层事务中调用内层事务)
**支持当前事务的情况**
PROPAGATION_REQUIRED：单独执行内层方法是有事务；当调用外层方法时，内存方法也会加入到外层事务中
PROPAGATION_SUPPORTS：单独执行内层方法是无事务；当调用外层方法时,内层方法也会加入到外层事务中
PROPAGATION_MANDATORY：单独执行内层方法，因为当前没有一个活动的事务，则会抛出异常new IllegalTransactionStateException(“Transaction propagation ‘mandatory’ but no existing transaction found”);当调用外层方法时，内层方法会加入到外层事务中。

**不支持当前事务的情况**
PROPAGATION_REQUIRES_NEW：若外层方法在调用内层方法后的内层方法失败了，内层方法所做的结果依然被提交，而其他代码会回滚
PROPAGATION_NOT_SUPPORTED：单独执行内存方法是无事务；当调用外层方法时，会挂起外层事务并无事务的执行内层方法
PROPAGATION_NEVER：总是非事务地执行，如果存在一个活动事务，则抛出异常。

**其他**
PROPAGATION_NESTED：单独执行外层方法有事务；外层事务失败时，会回滚内层事务所做的动作。而内层事务操作失败并不会引起外层事务的回滚

#### Transactional(rollbackFor = Exception.class)
Exception分为运行时异常和非运行时异常
当@Transaction作用于类上时，该类所有的public方法将具有该类型的事务属性，同时也可以在方法级别使用该注解覆盖类级别的定义。若类或方法加上这个注解，那么当类里方法出现异常就会进行回滚，数据库里面的数据也会回滚
若@Transaction不配置rollbackeFor属性，那么事务在遇到RuntimeException的时候才会回滚


### Spring设计模式
工厂模式： Spring使用工厂模式通过BeanFactory、ApplicationContext创建bean对象
代理模式： SpringAOP功能实现
单例模式： Spring中的Bean默认是单例的
包装器模式： 当项目需要连接多个数据库且不同客户在每次访问中根据需要会访问不同的数据库，这种模式可根据客户的需求动态切换不同数据源
观察者模式： Spring事件驱动模型就是观察者模式很经典的一个应用
适配器模式： Spring AOP的增强或通知以及Spring MVC使用到了此模式

### SpringBoot
#### @ SpringBootApplication 注解
```
package org.springframework.boot.autoconfigure;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
        @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
   ......
}

package org.springframework.boot;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {

}
```
可以看出@SpringBootApplication看作是@Configuration、@EnableAutoConfiguration、@ComponentScan注解的集合
- @EnableAutoConfiguration: 启用SpringBoot的自动配置机制
- @ComponentScan: 扫描被@Component(@Service,@Controller)注解的bean，注解默认会扫描该类所在包下所有的类
- @Configuration: 允许在上下文中注册额外的bean或导入其他配置类

#### SpringBoot自动配置原理
首先是因为@SpringBootApplication注解的原因，@EnableAutoConfiguration是启动自动配置的关键
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
    String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

    Class<?>[] exclude() default {};

    String[] excludeName() default {};
}
```
@EnableAutoConfiguration注解通过Spring提供的@Import注解导入了AutoConfigurationImportSelector类，类中的getCandidateConfigurations方法会将所有自动配置类的信息以List形式返回，这些配置信息会被Spring容器作bean来管理
```
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
            getBeanClassLoader());
    Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
            + "are using a custom packaging, make sure that file is correct.");
    return configurations;
}
```
此时自动配置信息有了,还需要@Condition注解
* @ConditionalOnClass(指定的类必须存在于类路径下),@ConditionalOnBean(容器中是否有指定的 Bean)等等都是对@Conditional注解的扩展

例如在SpringSecurity中,SecurityAutoConfiguration中导入了WebSecurityEnablerConfiguration类
```
@Configuration
@ConditionalOnBean(WebSecurityConfigurerAdapter.class)
@ConditionalOnMissingBean(name = BeanIds.SPRING_SECURITY_FILTER_CHAIN)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
public class WebSecurityEnablerConfiguration {

}
```
WebSecurityEnablerConfiguration类中使用@ConditionalOnBean指定了容器中必须还有WebSecurityConfigurerAdapter 类或其实现类。所以，一般情况下 Spring Security 配置类都会去实现 WebSecurityConfigurerAdapter，这样自动将配置就完成了。

#### 常用读取配置文件方法
1、使用 @Value("${property}") 读取比较简单的配置信息（不推荐）
2、通过@ConfigurationProperties读取并与 bean 绑定，并且可以通过@EnableConfigurationProperties进行数据校验
3、@PropertySource读取指定的 properties 文件(yml不行)

#### 加载配置文件的优先级
![](https://cdn.jsdelivr.net/gh/geek-cy/img/SpringBoot/dddf4a3afe07876b3132bf9ddacb7ac0.jpg)

#### 校验参数
1、验证请求体(RequestBody)
* 在需要验证的参数上加上@Valid注解,若验证失败Spring会将异常转化为HTTP 400(错误请求)
2、验证请求参数(Path Variables和Request Parameters)
* 在类上加Validated注解告诉Spring去校验方法参数

#### 全局异常处理
@ControllerAdvice和@ExceptionHandler(处理Controller级别的异常)

### SpringSecurity
#### Cookie与Session
因为HTTP协议是无状态的，因此Cookie和Session都是用来跟踪浏览器用户身份的会话方式
1、Cookie能保存已经登录过的用户信息，用户首选项，主题和其他设置信息
2、Cookie保存Session或者token，向后端发送请求时候带上Cookie，这样后端就能取到session或token了

服务端可以使用Spring中的@CookieValue注解获取特定的cookie的值

#### Session进行身份验证流程
1、用户向服务器发送用户名和密码用于登录系统
2、服务器验证通过后，为用户创建一个Session并将Session存储起来
3、服务器向用户返回一个SessionID,写入用户的Cookie
4、当用户保持登录状态时，Cookie将与每个后续请求一起被发送出去
5、服务器可以将存储在Cookie上的SessionID与存储在内存或redis中的Session信息进行比较验证用户身份，返回给用户客户端响应信息时候会附带当前用户的状态

#### 若没有Cookie的话对Session的影响
若使用Cookie保存SessionID，此时客户端禁用了Cookie，那么Session就无法正常工作
但并不是没有Cookie后就不能用Session,可以将SessionID放在请求的url里，但是安全性和用户体验就降低了

#### Cookie和token
CSRF(Cross Site Request Forgery)是跨站请求伪造，黑客可以直接通过Cookie向服务器发送请求
但是若使用token就不会存在这个问题，在登录成功获得token后，一般会放在local storage中，在给服务器发送请求会加上这个token

需要注意无论是Cookie还是token都无法避免XSS(Cross Site Scripting)跨站脚本攻击
跨站脚本攻击会通过脚本盗用信息
