package deque;

public class ArrayDeque<Item> implements Deque<Item>{
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    @Override
    public void addFirst(Item i) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = i;
        nextFirst = ((nextFirst - 1) + items.length) % items.length;
        size++;
    }

    @Override
    public void addLast(Item i) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = i;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }

    @Override
    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }

        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 2);
        }
        nextFirst = (nextFirst + 1) % items.length;
        Item first = items[nextFirst];
        items[nextFirst] = null;

        size--;
        return first;
    }

    @Override
    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }

        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 2);
        }
        nextLast = ((nextLast - 1) + items.length) % items.length;
        Item last = items[nextLast];
        items[nextLast] = null;

        size--;
        return last;
    }

    //Potential complications if downsizing by greater than a factor of 2.
    //Ugly code right now to separate upsize and downsize
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        int firstIndex = (nextFirst + 1) % items.length;
        if (capacity > items.length) {
            if (nextLast > nextFirst) {
                int firstCopy = items.length - firstIndex;
                System.arraycopy(items, firstIndex, temp, size / 2, firstCopy);
                System.arraycopy(items, 0, temp, size / 2 + firstCopy, size - firstCopy);
            } else {
                System.arraycopy(items, firstIndex, temp, size / 2, size);
            }
        } else {
            if (nextLast < nextFirst) {
                int firstCopy = items.length - firstIndex;
                System.arraycopy(items, firstIndex, temp, size / 2, firstCopy);
                System.arraycopy(items, 0, temp, size / 2 + firstCopy, size - firstCopy);
            } else {
                System.arraycopy(items, firstIndex, temp, size / 2, size);
            }
        }
        items = temp;
        nextFirst = size / 2 - 1;
        nextLast = size + (size / 2);
    }

    @Override
    public int size() {return size;}

    @Override
    public Item get(int index) {return items[((nextFirst + 1) % items.length + index) % items.length];}

    @Override
    public void printDeque() {
        for (int counter = (nextFirst + 1) % items.length; counter < nextLast; counter++) {
            System.out.print(items[counter] + " ");
        }
        System.out.println();
    }
}
