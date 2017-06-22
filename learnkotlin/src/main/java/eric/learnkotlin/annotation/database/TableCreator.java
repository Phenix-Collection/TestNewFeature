package eric.learnkotlin.annotation.database;

import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by wenjun.zhong on 2017/6/13.
 */

public class TableCreator {

    private TableCreator(){}

    public static void createTable(){
        String className = Member.class.getName();

        try {
            Class<?> cls = Class.forName(className);
            DBTable dbTable = cls.getAnnotation(DBTable.class);
            if(dbTable == null){
                System.out.println("createTable DBTable annotation is null");
                return ;
            }

            String tableName = dbTable.name();

            if(tableName.length() < 1){
                tableName = cls.getName().toUpperCase();
            }

            List<String> columnDefs = new ArrayList<>();

            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields){
                Annotation[] annotations = field.getDeclaredAnnotations();
                if(annotations == null || annotations.length < 1){
                    continue;
                }
                String columnName = null;
                if(annotations[0] instanceof SQLInteger){
                    SQLInteger sqlInteger = (SQLInteger) annotations[0];
                    if(sqlInteger.name().length() < 1){
                        columnName = field.getName().toUpperCase();
                    }else{
                        columnName = sqlInteger.name();
                    }
                    columnDefs.add(columnName + " INT" + getConstraints(sqlInteger.constraints()));
                }

                if(annotations[0] instanceof SQLString){
                    SQLString sqlString = (SQLString) annotations[0];
                    if(sqlString.name().length() < 1){
                        columnName = field.getName().toUpperCase();
                    }else{
                        columnName = sqlString.name();
                    }
                    columnDefs.add(columnName + " VARCHAR(" + sqlString.value() + ")" + getConstraints(sqlString.constraints()));

                }
            }

            StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
            for (String columnDef : columnDefs){
                createCommand.append("\n    ").append(columnDef).append(",");
            }
            String tableCreate = createCommand.substring(0, createCommand.length() -1) + ");";
            System.out.print("Table Creation SQL for " + className + " is :\n" + tableCreate);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String getConstraints(Constraints cons){
        String constraints = "";

        if(!cons.allowNull()){
            constraints += " NOT NULL";
        }

        if(cons.primaryKey()){
            constraints += " PRIMARY KEY";
        }

        if(cons.unique()){
            constraints += " UNIQUE";
        }
        return constraints;
    }
}
