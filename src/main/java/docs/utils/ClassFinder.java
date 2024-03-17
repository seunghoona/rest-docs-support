package docs.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassFinder {
    public static List<Class<?>> findClassesImplementingInterface(String packageName, Class<?> interfaceClass) {
        List<Class<?>> implementingClasses = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                java.net.URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    File[] files = new File(resource.getFile()).listFiles();
                    for (File file : files) {
                        if (file.isDirectory()) {
                            implementingClasses.addAll(findClassesImplementingInterface(packageName + "." + file.getName(), interfaceClass));
                        } else if (file.getName().endsWith(".class")) {
                            String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                            try {
                                Class<?> clazz = Class.forName(className);
                                if (interfaceClass.isAssignableFrom(clazz) && !clazz.equals(interfaceClass)) {
                                    implementingClasses.add(clazz);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return implementingClasses;
    }
}
