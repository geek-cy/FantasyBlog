package cn.fantasyblog.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Description Kryo序列化方式
 * @Author Cy
 * @Date 2021-05-02 19:52
 * */
@Slf4j
public class KryoRedisSerializer<T> implements RedisSerializer<T> {

    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(()->{
        Kryo kryo = new Kryo();
        // 关闭注册行为
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    public KryoRedisSerializer() {
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        Kryo kryo = kryoThreadLocal.get();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            kryo.writeClassAndObject(output, t);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new SerializationException("序列化失败");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        Kryo kryo = kryoThreadLocal.get();
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            T t = (T) kryo.readClassAndObject(input);
            kryoThreadLocal.remove();
            return t;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SerializationException("反序列化失败");
        }
    }
}
