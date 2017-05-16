package citycloud.util;

/**
 * Created by wojustme on 2017/2/8.
 */
public final class CastHelper {

  /**
   * 转换成String类型（默认值为空串）
   * @param obj
   * @return
   */
  public static String castString(Object obj) {
    return CastHelper.castString(obj, "");
  }

  /**
   * 转换成String（可以指定默认值）
   * @param obj
   * @param defaultValue
   * @return
   */
  public static String castString(Object obj, String defaultValue) {
    return obj != null ? String.valueOf(obj) : defaultValue;
  }

  /**
   * 转换成浮点型（默认值为0.0）
   * @param obj
   * @return
   */
  public static double castDouble(Object obj) {
    return CastHelper.castDouble(obj, 0);
  }

  /**
   * 转换成浮点型（可以指定默认值）
   * @param obj
   * @param defaultValue
   * @return
   */
  public static double castDouble(Object obj, double defaultValue) {
    double doubleValue = defaultValue;
    if (obj != null) {
      String strValue = castString(obj);
      if (StringHelper.isNotEmpty(strValue)) {
        try {
          doubleValue = Double.parseDouble(strValue);
        } catch (NumberFormatException e) {
          doubleValue = defaultValue;
        }
      }
    }
    return doubleValue;
  }

  /**
   * 转换成长整型（默认值为0L）
   * @param obj
   * @return
   */
  public static long castLong(Object obj) {
    return CastHelper.castLong(obj, 0);
  }

  /**
   * 转换成长整型（可以指定默认值）
   * @param obj
   * @param defaultValue
   * @return
   */
  public static long castLong(Object obj, long defaultValue) {
    long longValue = defaultValue;
    if (obj != null) {
      String strValue = castString(obj);
      if (StringHelper.isNotEmpty(strValue)) {
        try {
          longValue = Long.parseLong(strValue);
        } catch (NumberFormatException e) {
          longValue = defaultValue;
        }
      }
    }
    return longValue;
  }

  /**
   * 转换成整型（默认值是0）
   * @param obj
   * @return
   */
  public static int castInt(Object obj) {
    return CastHelper.castInt(obj, 0);
  }

  /**
   * 转换成长整型（可以指定默认值）
   * @param obj
   * @param defaultValue
   * @return
   */
  public static int castInt(Object obj, int defaultValue) {
    int intValue = defaultValue;
    if (obj != null) {
      String strValue = castString(obj);
      if (StringHelper.isNotEmpty(strValue)) {
        try {
          intValue = Integer.parseInt(strValue);
        } catch (NumberFormatException e) {
          intValue = defaultValue;
        }
      }
    }
    return intValue;
  }

  /**
   * 转换成布尔型（默认值为false）
   * @param obj
   * @return
   */
  public static boolean castBoolean(Object obj) {
    return CastHelper.castBoolean(obj, false);
  }

  /**
   * 转换成布尔型（可以指定默认值）
   * @param obj
   * @param defaultValue
   * @return
   */
  public static boolean castBoolean(Object obj, boolean defaultValue) {
    boolean booleanValue = defaultValue;
    if (obj != null) {
      booleanValue = Boolean.parseBoolean(castString(obj));
    }
    return booleanValue;
  }
}
