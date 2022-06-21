package com.demo.grpc.utils;

import java.lang.reflect.Field;

public class Java2Proto {

//    public static void main(String[] args) {
//        java2proto(LiNlu.class);
//    }

    public static void java2proto(Class aClass){
        StringBuilder b = new StringBuilder();
            Field[] fields = aClass.getDeclaredFields();
            b.append("message ").append("").append(aClass.getSimpleName()).append("{").append("\n");
            for (int i = 1; i <= fields.length; i++) {
                Field field = fields[i-1];
                String name = field.getName();
                String type = field.getType().getName();
                b.append("  ");
                switch (type){
                    case "java.lang.String":
                        b.append("string");
                        break;
                    case "java.lang.Double":
                    case "double":
                        b.append("double");
                        break;
                    case "java.lang.Float":
                    case "float":
                        b.append("float");
                        break;
                    case "java.lang.Long":
                    case "long":
                        b.append("int64");
                        break;
                    case "java.lang.Integer":
                    case "int":
                        b.append("int32");
                        break;
                    case "java.lang.Boolean":
                    case "boolean":
                        b.append("bool");
                        break;
                    default:
                        b.append(type);
                }
                b.append(" ").append(name).append(" ").append("=").append(" ").append(i).append(";");
                b.append("\n");
            }
            b.append("}");
            b.append("\n");

        System.out.println(b);
    }
}