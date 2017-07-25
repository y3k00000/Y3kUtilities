package y3k.utils;

import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class ClassUtil {
    public static <T> ArrayList<Class<T>> findSubClassOf(Class<T> target) {
        long decompileDexFileStartTime = new Date().getTime();
        ArrayList<Class<T>> resultSubClasses = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> classUrls = classLoader.getResources("classes.dex");
            for (URL classUrl; classUrls.hasMoreElements(); ) {
                classUrl = classUrls.nextElement();
                Log.d(ClassUtil.class.getSimpleName(), "classUrl = " + classUrl);
                DexFile dexFile = new DexFile(classUrl.toString().replace("!/classes.dex", "").replace("jar:file:", ""));
                Enumeration<String> dexEntries = dexFile.entries();
                for (; dexEntries.hasMoreElements(); ) {
                    String dexEntry = dexEntries.nextElement();
                    try {
                        Class loadedClass = Class.forName(dexEntry,false,classLoader);
                        if (target.isAssignableFrom(loadedClass)&&target!=loadedClass) {
                            resultSubClasses.add(loadedClass);
                        }
                    } catch (Exception e) {
                        Log.w(ClassUtil.class.getSimpleName(), dexEntry + " Exception", e);
                    }
                }
            }
        } catch (IOException e) {
            Log.e(ClassUtil.class.getSimpleName(), "classes.dex enumerating Exception", e);
        }
        Log.d(ClassUtil.class.getSimpleName(), "decompile class.dex elapsed " + (new Date().getTime() - decompileDexFileStartTime));
        return resultSubClasses;
    }
}
