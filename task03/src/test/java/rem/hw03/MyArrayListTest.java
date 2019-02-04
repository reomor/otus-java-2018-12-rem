package rem.hw03;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MyArrayListTest {
    @Test
    public void givenEmptyList_whenIsEmpty_thenTrueIsReturned() {
        List<Object> list = new MyArrayList<Object>();
        Assert.assertTrue(list.isEmpty());
    }
}