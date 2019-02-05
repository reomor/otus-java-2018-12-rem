package rem.hw03;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class MyArrayListTest {
    @Test
    public void givenEmptyList_whenIsEmpty_thenTrueIsReturned() {
        List<Object> list = new MyArrayList<Object>();
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void givenFilledList_whenAdd_thenAddedToTail() {
        List<String> list = new MyArrayList<String>();
        list.add("First");
        list.add("Second");
        String string = "String to add";
        boolean added = list.add(string);
        assertTrue(added);
        assertEquals(string, list.get(list.size() - 1));
    }

    @Test
    public void givenFilledList_whenContains_thenTrue() {
        List<String> list = new MyArrayList<String>();
        list.add("First");
        String containsString = "Second";
        list.add(containsString);
        assertTrue(list.contains(containsString));
    }

    @Test
    public void givenFilledList_whenRemove_thenRemovedSizeLessElementsShifted() {
        List<Long> list = new MyArrayList<Long>();
        final int size = 10;
        Long start = new Random().nextLong();
        for (int i = 0; i < size; i++) {
            list.add(start + i);
        }
        final int indexDel = new Random().nextInt(size);
        Long elementDel = list.get(indexDel);
        boolean removed = list.remove(elementDel);
        assertTrue(removed);
        for (int i = 0; i < list.size(); i++) {
            assertFalse(list.get(i).equals(elementDel));
        }
        assertTrue(size == list.size() + 1);
    }

    @Test
    public void givenFilledList_whenGetByIndex_thenReturnedContained() {
        List<String> list = new MyArrayList<String>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(); i++) {
            list.add("");
        }
        final String elementGet = "1234567890";
        list.add(elementGet);
        final int index = list.size() - 1;
        for (int i = 0; i < random.nextInt(); i++) {
            list.add(elementGet);
        }
        assertEquals(elementGet, list.get(index));
    }

    @Test
    public void givenFilledList_whenSublist_thenFilledSublist() {
        List<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        List<Integer> subList = list.subList(1, 3);
        assertArrayEquals(subList.toArray(), new Integer[]{2,3});
    }
}