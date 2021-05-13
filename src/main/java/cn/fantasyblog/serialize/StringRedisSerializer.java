package cn.fantasyblog.config.serializerUtils;

import cn.fantasyblog.utils.StringUtils;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Description 重写StringRedisSerializer序列化,否则会报类型转换错误
 * @Author Cy
 * @Date 2021-05-03 15:18
 */
public class StringRedisSerializer implements RedisSerializer<Object> {

    // 处理字节序列和字符序列（字符串）的转换关系
    private final Charset charset;

    public StringRedisSerializer(){
        this(StandardCharsets.UTF_8);
    }
    private StringRedisSerializer(Charset charset){
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    // 这里形参重写为Object
    public byte[] serialize(Object o) throws SerializationException {
        String s = JSON.toJSONString(o);
        if(StringUtils.isBlank(s)){
            return null;
        }
        s = s.replace("\"","");
        return s.getBytes(charset);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes, charset));
    }
}
