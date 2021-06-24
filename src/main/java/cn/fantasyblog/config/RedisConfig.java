package cn.fantasyblog.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import cn.fantasyblog.serialize.FastJsonRedisSerializer;
import cn.fantasyblog.serialize.StringRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Description 缓存配置类
 * @Author Cy
 * @Date 2021-05-01 16:36

/**
 * 问题：1、kryo序列化是乱码
 * 2、先生成key再kryo序列化保存到redis,但是显示字符串,这个字符串咋显示的
 * 3、菜单查询缓存失效
 * 4、Cache用了Kryo序列化缓存到哪了
 */
@Slf4j
@Configuration
@EnableCaching
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 设置 redis 数据默认过期时间
     * key集中失效容易引起缓存雪崩因此需要多设置几个
     * 设置@cacheable 序列化方式
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory  redisConnectionFactory) {
        Random random = new Random();
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfigurationWithTtl(3600+random.nextInt(10000)),//默认策略，未配置的key会使用随机整数
                this.getRedisCacheConfigurationMap() //指定key策略
        );
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        // 需要作缓存在这里加上就加一个put即可
        redisCacheConfigurationMap.put("article", this.getRedisCacheConfigurationWithTtl(3600));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Object.class))
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }

    /**
     * 设置redisTemplate序列化方式
     */
    @Bean("redisTemplate")
    @ConditionalOnMissingBean(name = "redisTemplate")// 一般加在自定义配置里避免多个配置同时注入的风险
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(redisConnectionFactory);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        template.setHashValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        // 全局开启AutoType，这里方便开发，使用全局的方式(白名单检查)
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // 初始化template
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 自定义缓存key生成策略，默认将使用该策略
     * 此处的默认实现是SimpleKeyGenerator –它使用提供的方法参数来生成密钥。
     * 这意味着，如果我们有两个使用相同的缓存名称和参数类型集的方法，则很有可能会导致冲突
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            Map<String, Object> container = new HashMap<>(3);
            // 通过反射获取对象
            Class<?> targetClassClass = target.getClass();
            // 类地址(public class 包名)
            container.put("class", targetClassClass.toGenericString());
            // 方法名称
            container.put("methodName", method.getName());
            // 包名称
            container.put("package", targetClassClass.getPackage());
            // 参数列表
            for (int i = 0; i < params.length; i++) {
                container.put(String.valueOf(i), params[i]);
            }
            // 转为JSON字符串
            String jsonString = JSON.toJSONString(container);
            // 做SHA256 Hash计算，得到一个SHA256摘要作为Key
            String s = DigestUtils.sha256Hex(jsonString);
            return s;
        };
    }

    /**
     * 异常处理
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 异常处理，当Redis发生异常时，打印日志此时程序正常运行
        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handlerCacheGetError:key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handlerCacheGetError:key -> [{}];value -> [{}]", key, value, e);
            }

            // 指定缓冲对象清除错误
            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            // 除了操作中对象其他清除错误
            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
    }
}
