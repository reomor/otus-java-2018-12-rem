package rem.hw03;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    //Size of list
    private int size = 0;

    //Default capacity of list is 10
    private static final int DEFAULT_CAPACITY = 10;

    //Default empty
    private static final Object[] EMPTY_ELEMENT = {};

    //This array will store all elements added to list
    private Object elements[];

    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            elements = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            elements = EMPTY_ELEMENT;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(T t) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = t;
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        T item = (T) o;
        for (int i = 0; i < size - 1; i++) {
            if (elements[i] == item) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        T item = (T) o;
        for (int i = 0; i < size; i++) {
            if (elements[i] == item) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndexRange(index);
        return (T) elements[index];
    }

    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        checkIndexRange(index);
        T itemOld = (T) elements[index];
        elements[index] = element;
        return itemOld;
    }

    public void add(int index, T element) {
        checkIndexRange(index);
        if (size == elements.length) {
            ensureCapacity();
        }
        int tailLength = elements.length - index - 1;
        System.arraycopy(elements, index, elements, index + 1, tailLength);
        size++;
    }

    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndexRange(index);
        Object item = elements[index];
        int tailLength = elements.length - index - 1;
        System.arraycopy(elements, index + 1, elements, index, tailLength);
        size--;
        return (T) item;
    }

    @SuppressWarnings("unchecked")
    public int indexOf(Object o) {
        T item = (T) o;
        for (int i = 0; i < size; i++) {
            if (elements[i] == item) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public int lastIndexOf(Object o) {
        T item = (T) o;
        int lastIndexOf = -1;
        for (int i = 0; i < size; i++) {
            if (elements[i] == item) {
                lastIndexOf = i;
            }
        }
        return lastIndexOf;
    }

    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        int cursor = 0;
        int lastRet = -1;

        Itr() {
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elements = MyArrayList.this.elements;
            if (i >= elements.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elements[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    public <TT> TT[] toArray(TT[] a) {
        if (a.length < size) {
            return (TT[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        Object[] a = c.toArray();
        int aSize = a.length;
        if (aSize == 0)
            return false;
        final int s;
        if (aSize > elements.length - (s = size)) {
            elements = grow(s + aSize);
        }
        System.arraycopy(a, 0, elements, s, aSize);
        size = size + aSize;
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndexRange(index);
        Object[] a = c.toArray();
        int aSize = a.length;
        if (aSize == 0)
            return false;
        final int s;
        if (aSize > elements.length - (s = size)) {
            elements = grow(s + aSize);
        }

        int numMoved = s - index;
        if (numMoved > 0) {
            System.arraycopy(elements, index, elements, index + aSize, numMoved);
        }
        System.arraycopy(a, 0, elements, index, aSize);
        return true;
    }

    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection.
     * @see ArrayList#removeAll(Collection)
     **/
    public boolean removeAll(Collection<?> c) {
        return batchRemove(c, false, 0, size);
    }
    /**
     * Retains only the elements in this list that are contained in the
     * specified collection.  In other words, removes from this list all
     * of its elements that are not contained in the specified collection.
     * @see ArrayList#retainAll(Collection)
     **/
    public boolean retainAll(Collection<?> c) {
        return batchRemove(c, true, 0, size);
    }

    /**
     * Removes collection {@code c} from current list if {@param complement} is false
     * Removes from current list all elements, that are not contained in {@code c} if {@param complement} is true
     * taken from ArrayList
     * @param c collection with elements to remove from list, or retain in list
     * @param complement {@code true} for retain, {@code false} for remove
     * @param from start index
     * @param end end index
     * @return {@code true} if modified, else {@code false}
     */
    private boolean batchRemove(Collection<?> c, boolean complement,
                        final int from, final int end) {
        Objects.requireNonNull(c);
        final Object[] es = elements;
        int r;
        // Optimize for initial run of survivors
        for (r = from;; r++) {
            if (r == end)
                return false;
            if (c.contains(es[r]) != complement)
                break;
        }
        int w = r++;
        try {
            for (Object e; r < end; r++)
                if (c.contains(e = es[r]) == complement)
                    es[w++] = e;
        } catch (Throwable ex) {
            // Preserve behavioral compatibility with AbstractCollection,
            // even if c.contains() throws.
            System.arraycopy(es, r, es, w, end - r);
            w += end - r;
            throw ex;
        } finally {
            shiftTailOverGap(es, w, end);
        }
        return true;
    }

    private class ListItr extends Itr implements ListIterator<T> {

        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public T previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = elements;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (T) elementData[lastRet = i];
        }

        public void set(T e) {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(T e) {

            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    public ListIterator<T> listIterator(int index) {
        checkIndexRange(index);
        return new ListItr(index);
    }

    /**
     * Returns subList of current list
     * @param fromIndex start from
     * @param toIndex end, NOT included
     * @return subList if possible or empty collection
     */
    public List<T> subList(int fromIndex, int toIndex) {
        checkIndexRange(fromIndex);
        checkIndexRange(toIndex);
        List<T> subList = new MyArrayList<T>();
        for (int index = fromIndex; index < toIndex; index++) {
            subList.add((T) elements[index]);
        }
        return subList;
    }

    // additional functions
    private void checkIndexRange(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound:[" + 0 + ", " + (size - 1) + "]");
        }
    }

    private void ensureCapacity() {
        final int newCapacity = elements.length * 2;
        elements = Arrays.copyOf(elements, newCapacity);
    }

    private Object[] grow(int minCapacity) {
        return elements = Arrays.copyOf(elements, newCapacity(minCapacity));
    }

    private int newCapacity(int minCapacity) {
        if (minCapacity < 0) throw new OutOfMemoryError();
        return minCapacity + minCapacity;
    }

    private void shiftTailOverGap(Object[] es, int lo, int hi) {
        System.arraycopy(es, hi, es, lo, size - hi);
        for (int to = size, i = (size -= hi - lo); i < to; i++)
            es[i] = null;
    }
}
