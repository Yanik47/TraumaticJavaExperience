import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
public class SwMikolaj implements Inwentaryzator{
    @Override
    public Map<String, Integer> inwentaryzacja(List<String> listaKlas) {
        HashMap<String, Integer> invent = new HashMap<>();
        try {
            for(String niceClass : listaKlas) {
                Class<?> myClass = Class.forName(niceClass);
                Object myObject = myClass.newInstance();
                //Class<?> mySuperClass = myClass.getSuperclass();
                Field[] myFields = myClass.getDeclaredFields();
                for(Field field : myFields) {

                    //przypadek kiedy deklaracja dziedziczenie wyklucza wartosc z sumy
                    //if (mySuperClass != null ){
                        //try {
                        //    Field fieldSuperKlass = mySuperClass.getDeclaredField(field.getName());
                        //}catch(NoSuchFieldException e) {
                            try {
                                Field fieldKlass = myClass.getDeclaredField(field.getName());
                            } catch (NoSuchFieldException er) {
                                continue;
                            }
                            int modifiers = field.getModifiers();
                            Class<?> fieldType = field.getType();
                            if ((Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) &&
                                    (fieldType == int.class)) {
                                String fieldName = field.getName();
                                Set<String> interes = new HashSet<>();
                                interes.add("bombki");
                                interes.add("lancuchy");
                                interes.add("prezenty");
                                interes.add("cukierki");
                                interes.add("szpice");
                                interes.add("lampki");
                                if (interes.contains(fieldName)) {
                                    invent.put(fieldName, invent.getOrDefault(fieldName, 0) + field.getInt(myObject));
                                }
                            }
                        //}
                    //}
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        invent.values().removeIf((value -> value == 0));
        return invent;
    }
}
