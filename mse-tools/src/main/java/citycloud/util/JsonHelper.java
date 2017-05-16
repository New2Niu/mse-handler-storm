package citycloud.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wojustme on 2017/1/17.
 */
public final class JsonHelper {
  private static final Logger LOGGER = LoggerFactory.getLogger(JsonHelper.class);

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /**
   * 将一个POJO对象转换为Json
   * @param obj
   * @param <T>
   * @return
   */
  public static <T> String toJson(T obj) {
    String json;
    try {
      json = OBJECT_MAPPER.writeValueAsString(obj);
    } catch (Exception e) {
      LOGGER.error(obj.toString() + " convert POJO to Json failure", e);
      throw new RuntimeException(e);
    }
    return json;
  }

  /**
   * 将一个Json转换为POJO对象
   * @param json
   * @param type
   * @param <T>
   * @return
   */
  public static <T> T fromJson(String json, Class<T> type) {
    T pojo = null;
    try {
      pojo = OBJECT_MAPPER.readValue(json, type);
    } catch (Exception e) {
      LOGGER.error(json + " convert Json to POJO failure", e);
    }
    return pojo;
  }

  /**
   *
   * @param json
   * @param valueTypeRef
   * @param <T>
   * @return
   */
  public static <T> T fromJson(String json, TypeReference valueTypeRef) {
    T pojo = null;
    try {
      pojo = OBJECT_MAPPER.readValue(json, valueTypeRef);
    } catch (Exception e) {
      LOGGER.error(json + " convert Json to POJO failure", e);
    }
    return pojo;
  }
}
