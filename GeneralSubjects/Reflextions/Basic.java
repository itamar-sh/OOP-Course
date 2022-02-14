package GeneralSubjects.Reflextions;

import java.lang.reflect.*;

public class Basic {
    public static Field getFields(String objectName) throws NoSuchFieldException{
        Field f = Basic.class.getDeclaredField(objectName);
        int modifiers = f.getModifiers();
        if(Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)){
            if(f.getType().equals(Basic.class))  {
                return f;
            }
        }
        return null;
    }
    public static void checkObject(Object obj){
        Class cls = obj.getClass();
        System.out.println(cls.getName());
        System.out.println(cls.getClass());
        System.out.println(cls.getSimpleName());
        System.out.println(cls.getSuperclass());
        System.out.println(cls.getConstructors());
        System.out.println(cls.getDeclaredConstructors());
        System.out.println(cls.getDeclaredFields());
        System.out.println(cls.getDeclaredMethods());
        System.out.println(cls.getInterfaces());
        System.out.println(cls.getTypeName());
        System.out.println(cls.isAnonymousClass());
        System.out.println(cls.isAnnotation());
        System.out.println(cls.isInterface());
        System.out.println(cls.isEnum());
        System.out.println(cls.isMemberClass());
    }
    public static void checkClass(String className) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class cls = Class.forName(className);
        Constructor[] allCons = cls.getDeclaredConstructors();
        Object obj = allCons[0].newInstance();
        Field[] publicFields = cls.getFields();  // public ones
        Field[] Allfields = cls.getDeclaredFields();  // all
        for(Field f: Allfields){
            f.set(obj, "car");
        }
        Method[] allMet = cls.getDeclaredMethods();
        for(Method m: allMet){
            m.invoke(obj);
        }
    }
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //checkObject(new Object(){});
        checkClass("GeneralSubjects.Reflextions.Car");
    }

}

class Car{
    private String a = "a";
    public String b = "b";

    public void pri(){
        System.out.println("pri");
    }

    public void a(){
        System.out.println(a + b);
    }
}

