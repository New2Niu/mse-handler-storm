package citycloud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by wojustme on 2017/2/8.
 */
public final class ClassHelper {
  // 关于类操作的工具类的日志打印
  private static final Logger LOGGER = LoggerFactory.getLogger(ClassHelper.class);
  /**
   * 获取类加载器
   *
   * @return
   */
  public static ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  /**
   * 加载类
   * 为了提高加载类的性能，可将isInitialized参数设置为false
   *
   * @param className
   * @param isInitialized
   * @return
   */
  public static Class<?> loadClass(String className, boolean isInitialized) {
    Class<?> clazz;
    try {
      clazz = Class.forName(className, isInitialized, getClassLoader());
    } catch (ClassNotFoundException e) {
      LOGGER.error("load " + className + " class failure", e);
      throw new RuntimeException(e);
    }
    return clazz;
  }

  /**
   * 加载类，提供默认方法：isInitialized->false
   * @param className
   * @return
   */
  public static Class<?> loadClass(String className) {
    return loadClass(className, false);
  }

  /**
   * 获取指定包名下的所有类
   *
   * @param packageName
   * @return
   */
  public static Set<Class<?>> getClassSet(String packageName) {
    Set<Class<?>> classSet = new HashSet<Class<?>>();
    try {
      Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        if (url != null) {
          String protocol = url.getProtocol();
          if (protocol.equals("file")) {
            String packagePath = url.getPath().replaceAll("%20", " ");
            addClass(classSet, packagePath, packageName);
          } else if (protocol.equals("jar")) {
            JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
            if (jarURLConnection != null) {
              JarFile jarFile = jarURLConnection.getJarFile();
              if (jarFile != null) {
                Enumeration<JarEntry> jarEntries = jarFile.entries();
                while (jarEntries.hasMoreElements()) {
                  JarEntry jarEntry = jarEntries.nextElement();
                  String jarEntryName = jarEntry.getName();
                  if (jarEntryName.endsWith(".class")) {
                    String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                    doAddClass(classSet, className);
                  }
                }
              }
            }
          }
        }
      }
    } catch (Exception e) {
      LOGGER.error("get class set of " + packageName + " failure", e);
      throw new RuntimeException(e);
    }
    return classSet;
  }

  /**
   * 将包的路径和包的名称中的class加载到set容器中去
   * @param classSet
   * @param packagePath
   * @param packageName
   */
  private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
    File[] files = new File(packagePath).listFiles(new FileFilter() {
      public boolean accept(File file) {
        return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
      }
    });
    for (File file : files) {
      String fileName = file.getName();
      if (file.isFile()) {
        String className = fileName.substring(0, fileName.lastIndexOf("."));
        if (StringHelper.isNotEmpty(packageName)) {
          className = packageName + "." + className;
        }
        doAddClass(classSet, className);
      } else {
        String subPackagePath = fileName;
        if (StringHelper.isNotEmpty(packagePath)) {
          subPackagePath = packagePath + "/" + subPackagePath;
        }
        String subPackageName = fileName;
        if (StringHelper.isNotEmpty(packageName)) {
          subPackageName = packageName + "." + subPackageName;
        }
        addClass(classSet, subPackagePath, subPackageName);
      }
    }
  }

  /**
   * 将每个class类加载到set容器中去
   * @param classSet
   * @param className
   */
  private static void doAddClass(Set<Class<?>> classSet, String className) {
    Class<?> clazz = loadClass(className, false);
    classSet.add(clazz);
  }
}
