package AST;

import java.lang.reflect.Array;

public class ArrayTypeName extends Type {

    private Type componentType;

    private int arraySize;

    public ArrayTypeName(Type componentType, int arraySize) {
        this.componentType = componentType;
        this.arraySize = arraySize;
    }

    public Type componentType() {
        return componentType;
    }

    public String toDescriptor() {

        String descriptor = "";
        for(int i = 0; i < arraySize; i++){
            descriptor += "[";
        }

        descriptor += componentType.toDescriptor();

        return descriptor;
    }

    public String toString() {
        String descriptor = componentType.toDescriptor();
        for(int i = 0; i < arraySize; i++){
            descriptor += "[]";
        }
        return descriptor;
    }

    public Type resolve(Context context) {
        componentType = componentType.resolve(context);

        Class classRep = Array.newInstance(componentType().classRep(), 0)
                .getClass();

        setClassRep(classRep);

        return this;
    }

}
