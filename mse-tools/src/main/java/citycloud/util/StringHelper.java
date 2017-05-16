package citycloud.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by wojustme on 2017/2/8.
 */

public final class StringHelper {

  /**
   * 判断是否为空
   * @param str
   * @return
   */
  public static boolean isEmpty(String str) {
    if (str != null){
      str = str.trim();
    }
    return StringUtils.isEmpty(str);
  }

  /**
   * 判断是否为非空
   * @param str
   * @return
   */
  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  public static String[] splitString(String str, String splitStr) {
    return StringUtils.split(str, splitStr);
  }
}
