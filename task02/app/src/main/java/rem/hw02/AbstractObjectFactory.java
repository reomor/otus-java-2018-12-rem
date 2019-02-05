package rem.hw02;

abstract class AbstractObjectFactory<T> {
    int size = 20_000_000;
    public static AbstractObjectFactory getFactory(FactoryObject factoryObject) {
        AbstractObjectFactory abstractObjectFactory = null;
        switch (factoryObject) {
            case OBJECT:
                abstractObjectFactory = new ObjectFactory();
                break;
            case STRING:
                abstractObjectFactory = new StringFactory();
                break;
            case INTEGER:
                abstractObjectFactory = new IntegerFactory();
                break;
        }
        return abstractObjectFactory;
    }

    public abstract T getEmptyOne();
    public abstract T getFilledOne();
    public abstract T[] getReferenceArray();
    public abstract T[] getFilledArray();
}

class ObjectFactory extends AbstractObjectFactory<Object> {

    @Override
    public Object getEmptyOne() {
        return new Object();
    }

    @Override
    public Object getFilledOne() {
        return new Object();
    }

    @Override
    public Object[] getReferenceArray() {
        return new Object[size];
    }

    @Override
    public Object[] getFilledArray() {
        Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Object();
        }
        return array;
    }
}

class StringFactory extends AbstractObjectFactory<String> {

    @Override
    public String getEmptyOne() {
        return "";
    }

    @Override
    public String getFilledOne() {
        return "qwerty";
    }

    @Override
    public String[] getReferenceArray() {
        return new String[size];
    }

    @Override
    public String[] getFilledArray() {
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = "qwerty";
        }
        return array;
    }
}

class IntegerFactory extends AbstractObjectFactory<Integer> {

    @Override
    public Integer getEmptyOne() {
        return 0;
    }

    @Override
    public Integer getFilledOne() {
        return Integer.valueOf(123456789);
    }

    @Override
    public Integer[] getReferenceArray() {
        return new Integer[size];
    }

    @Override
    public Integer[] getFilledArray() {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = Integer.valueOf(123456789);
        }
        return array;
    }
}