import lombok.SneakyThrows;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TripletDeque<T> implements Deque<T>, Containerable {
    private final int volume;
    private final int capacity;
    private ValueDeq<T> first = null;
    private ValueDeq<T> last = null;

    @Override
    public Object[] getContainerByIndex(int cIndex) {
        int i = 0;
        ValueDeq<T> current = first;
        while(i < cIndex){
            if(current.right == null){
                return null;
            }
            current = current.right;
            i++;
        }
        return current.val;
    }

    class ValueDeq<T> {
        private final Object[] val;
        private ValueDeq<T> right;
        private ValueDeq<T> left;
        public ValueDeq() {
            this.val = new Object[capacity];
            this.right = null;
            this.left = null;
        }

    }
    public TripletDeque() {
        this.volume = 1000;
        this.capacity = 5;
        this.first = new ValueDeq<T>();
        this.last = first;
    }
    public TripletDeque(int volume) {
        this.volume = volume;
        this.capacity = 5;
        this.first = new ValueDeq<T>();
        this.last = first;
    }
    public TripletDeque(int volume, int capacity) {
        this.volume = volume;
        this.capacity = capacity;
        this.first = new ValueDeq<T>();
        this.last = first;
    }

    @Override
    public void addFirst(T t) {
        if(t == null){
            throw new NullPointerException();
        }
        if(size() <= volume){
            if(this.first.val[0] == null){
                for (int i = 0; i < this.capacity; i++) {
                    if(i == this.capacity-1){
                        this.first.val[i] = t;
                        break;
                    }
                    if(this.first.val[i+1] != null){
                        this.first.val[i] = t;
                        break;
                    }
                }
            }else {
                ValueDeq<T> newFirst = new ValueDeq<>();
                newFirst.right = this.first;
                this.first.left = newFirst;
                this.first = newFirst;
                this.first.val[this.capacity - 1] = t;

            }
        }else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void addLast(T t) {
        if(t == null){
            throw new NullPointerException();
        }
        if(size() <= volume){
            if(this.last.val[capacity-1] == null){

                for (int i = capacity-1; i >= 0; i--) {
                    if(i == 0){
                        this.last.val[i] = t;
                        break;
                    }
                    if(this.last.val[i-1] != null){
                        this.last.val[i] = t;
                        break;
                    }

                }
            }else {
                ValueDeq<T> newLast = new ValueDeq<>();
                newLast.left = this.last;
                this.last.right = newLast;
                this.last = newLast;
                this.last.val[0] = t;

            }
        }else {
            throw new IllegalStateException();
        }

    }

    @Override
    public boolean offerFirst(T t) {
        if(size()<=volume){
            addFirst(t);
            return true;
        }
        return false;
    }

    @Override
    public boolean offerLast(T t) {
        if(size()<=volume){
            addLast(t);
            return true;
        }
        return false;
    }

    @SneakyThrows
    @Override
    public T removeFirst() {
        if(size() == 0){
            throw new NoSuchFieldException();
        } else {
            return pollFirst();
        }
    }

    @SneakyThrows
    @Override
    public T removeLast() {
        if(size() == 0){
            throw new NoSuchFieldException();
        } else {
            return pollLast();
        }
    }

    @Override
    public T pollFirst() {
        T returnedValue;
        for (int i = 0; i < this.first.val.length; i++) {
            if(first.val[i] != null){
                returnedValue = (T)first.val[i];
                this.first.val[i] = null;
                if(i == capacity - 1 && first.right!=null){
                    first = first.right;
                }
                return returnedValue;
            }
        }
        return null;
    }

    @Override
    public T pollLast() {
        T returnedValue;
        for (int i = this.last.val.length-1; i >= 0; i--) {
            if(last.val[i] != null){
                returnedValue = (T)last.val[i];
                this.last.val[i] = null;
                if(i == 0 && last.left!=null){
                    last = last.left;
                }
                return returnedValue;
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public T getFirst() {
        if(size() == 0){
            throw new NoSuchFieldException();
        } else {
            return peekFirst();
        }
    }

    @SneakyThrows
    @Override
    public T getLast() {
        if(size() == 0){
            throw new NoSuchFieldException();
        } else {
            return peekLast();
        }
    }

    @Override
    public T peekFirst() {
        for (int i = 0; i < this.first.val.length; i++) {
            if(first.val[i] != null){
                return (T)first.val[i];
            }
        }
        return null;
    }

    @Override
    public T peekLast() {
        for (int i = this.last.val.length-1; i >= 0; i--) {
            if(last.val[i] != null){
                return  (T)last.val[i];
            }
        }
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object remElem) {
        Iterator<T> iterator = this.iterator();
        int i = 0;
        while(first.val[i]==null && size()!=0){
            i++;
        }
        ValueDeq<T> currentCont= first;
        while(iterator.hasNext()){
            if(iterator.next().equals((T)remElem)){
                while(iterator.hasNext()){
                    currentCont.val[i] = iterator.next();
                    if (i != capacity - 1) {
                        i++;
                    } else {
                        i = 0;
                        currentCont = currentCont.right;
                    }
                }
                if(i ==0){
                    currentCont.left.right=null;
                    last = currentCont.left;
                }else{
                    currentCont.val[i] = null;
                }

                return true;
            }
            if(iterator.hasNext()) {
                if (i != capacity - 1) {
                    i++;
                } else {
                    i = 0;
                    currentCont = currentCont.right;
                }
            }
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object remElem) {
        Iterator<T> iterator = this.descendingIterator();
        int i = capacity-1;
        while(last.val[i]==null && size()!=0){
            i--;
        }
        ValueDeq<T> currentCont = last;
        while(iterator.hasNext()){
            if(iterator.next().equals((T) remElem)){
                while(iterator.hasNext()){
                    currentCont.val[i] = iterator.next();
                    if (i != 0) {
                        i--;
                    } else {
                        i = capacity-1;
                        currentCont = currentCont.left;
                    }
                }
                if(i == capacity - 1){
                    currentCont.right.left=null;
                    first = currentCont.right;
                }else{
                    currentCont.val[i] = null;
                }
                return true;
            }
            if (i != 0) {
                i--;
            } else {
                i = capacity - 1;
                currentCont = currentCont.left;
            }

        }
        return false;
    }

    @Override
    public boolean add(T t) {
        addLast(t);
        return true;
    }

    @Override
    public boolean offer(T t) {
        return offerLast(t);
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if(c.size() == 0){
            return false;
        }
        for (T t : c) {
            add(t);
        }
        return true;
    }


    @Override
    public void push(T t) {
        addFirst(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }



    @Override
    public boolean contains(Object o) {
        for (T t : this) {
            if (t.equals((T) o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        int size = 0;
        ValueDeq<T> current = this.first;
        size+= containerSize(current);
        while (current.right != null){
            current = current.right;
            size += containerSize(current);
        }
        return size;
    }
    private int containerSize(ValueDeq<T> valueDeq){
        int size = 0;
        for (Object o : valueDeq.val) {
            if(o != null){
                size++;
            }
        }
        return size;
    }



    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }
    private class Itr implements Iterator<T>{
        int cursor;       // index of next element to return
        ValueDeq<T> curretCont = first;
        ValueDeq<T> retCont;
        int dequeCursor;
        int lastRet = -1; // index of last element returned; -1 if no such
        @Override
        public boolean hasNext() {
            return dequeCursor < size() && size()!=0;
        }

        @SneakyThrows
        @Override
        public T next() {
            if(hasNext()){
                if(cursor == 0 && curretCont == first){
                    while(curretCont.val[cursor]==null){
                        cursor++;
                    }
                }
                lastRet = cursor;
                retCont = curretCont;
                if(cursor == capacity-1){
                    curretCont = curretCont.right;
                    cursor =0;
                    dequeCursor++;
                } else{
                    cursor++;
                    dequeCursor++;
                }
                return (T)retCont.val[lastRet];
            }
            else{
                throw new NoSuchElementException();
            }

        }
    }
    @Override
    public Iterator<T> descendingIterator() {
        return new descItr();
    }
    private class descItr implements Iterator<T>{
        int cursor = capacity - 1;       // index of next element to return
        ValueDeq<T> curretCont = last;
        ValueDeq<T> retCont;
        int dequeCursor;
        int lastRet = -1; // index of last element returned; -1 if no such
        @Override
        public boolean hasNext() {
            return dequeCursor < size() && size()!=0;
        }

        @SneakyThrows
        @Override
        public T next() {
            if(hasNext()){
                if(cursor == capacity - 1 && curretCont == last){
                    while(curretCont.val[cursor]==null){
                        cursor--;
                    }
                }
                lastRet = cursor;
                retCont = curretCont;
                if(cursor == 0){
                    curretCont = curretCont.left;
                    cursor =capacity-1;
                    dequeCursor++;
                } else{
                    cursor--;
                    dequeCursor++;
                }
                return (T)retCont.val[lastRet];
            }
            else{
                throw new NoSuchElementException();
            }

        }
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        int i = 0;
        for( T elem : this){
            array[i]  = elem;
            i++;
        }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        while(size()!=0){
            remove();
        }

    }
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem : c) {
            if(!contains(elem)){
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
