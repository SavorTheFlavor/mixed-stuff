import java.io.*;

/**
 * Created by Administrator on 2018/10/13.
 */
public class MyClassLoader extends ClassLoader {

    private String name;



    /**
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(new File("d:/Test.class"));
            int c = 0;
            while (-1 != (c = is.read())) {
                baos.write(c);
            }
            data = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.defineClass(name, data, 0, data.length);
    }

    // 重写loadClass方法可破坏双亲委托模式
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = null;
        ClassLoader system = getSystemClassLoader();
        try {
            clazz = system.loadClass(name);
        } catch (Exception e) {
            // ignore
        }
        if (clazz != null)
            return clazz;
        clazz = findClass(name);
        return clazz;
    }

    //原loadClass方法
//    protected Class<?> loadClass(String name, boolean resolve)
//            throws ClassNotFoundException
//    {
//        synchronized (getClassLoadingLock(name)) {
//            // First, check if the class has already been loaded
//            Class<?> c = findLoadedClass(name);
//            if (c == null) {
//                long t0 = System.nanoTime();
//                try {
//                    if (parent != null) {
//                        c = parent.loadClass(name, false);
//                    } else {
//                        c = findBootstrapClassOrNull(name);
//                    }
//                } catch (ClassNotFoundException e) {
//                    // ClassNotFoundException thrown if class not found
//                    // from the non-null parent class loader
//                }
//
//                if (c == null) {
//                    // If still not found, then invoke findClass in order
//                    // to find the class.
//                    long t1 = System.nanoTime();
//                    c = findClass(name);
//
//                    // this is the defining class loader; record the stats
//                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
//                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
//                    sun.misc.PerfCounter.getFindClasses().increment();
//                }
//            }
//            if (resolve) {
//                resolveClass(c);
//            }
//            return c;
//        }
//    }
}
