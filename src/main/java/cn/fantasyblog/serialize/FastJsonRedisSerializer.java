package cn.fantasyblog.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import java.nio.charset.StandardCharsets;

/**
 * @Description FastJson序列化方式
 * @Author Cy
 * @Date 2021-05-03 14:43
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    private final Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if(t == null) {
            return new byte[0];
        }
        // 将对象转化为Json字符串
        // 通过添加SerializerFeature.WriteClassName实现对多态的支持
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if(bytes == null || bytes.length <= 0){
            return null;
        }
        String s = new String(bytes, StandardCharsets.UTF_8);
        // 解析字符串得到具体实现类
        return JSON.parseObject(s,clazz);
    }
}
