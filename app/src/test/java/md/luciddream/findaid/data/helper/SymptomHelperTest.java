package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.dao.SymptomDao;
import md.luciddream.findaid.data.model.Symptom;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SymptomHelperTest {
    private ExecutorService executorService;
    private static SymptomDao symptomDao;
    private Future<List<Symptom>> futureList;
    private Future<Symptom> futureItem;

    @BeforeClass
    public static void createDb(){
        symptomDao = Mockito.mock(SymptomDao.class);
    }

    @Before
    public void setUp(){
        executorService = Mockito.mock(ExecutorService.class);
        futureList = Mockito.mock(Future.class);
        futureItem = Mockito.mock(Future.class);
    }
    @After
    public void tearDown(){
        executorService.shutdown();
    }
    @Test
    public void findAllTest() throws ExecutionException, InterruptedException {
        SymptomHelper symptomHelper = new SymptomHelper(executorService, symptomDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get())
                .thenReturn(Arrays.asList(
                        new Symptom(1, "Red skin"),
                        new Symptom(2, "Headache")));
        symptomHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByIdsTest() throws ExecutionException, InterruptedException {
        SymptomHelper symptomHelper = new SymptomHelper(executorService, symptomDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Symptom(1, "Red skin"),
                new Symptom(2, "Headache")));
        symptomHelper.findByIds(new int[]{1, 2});
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByIdTest() throws ExecutionException, InterruptedException {
        SymptomHelper symptomHelper = new SymptomHelper(executorService, symptomDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureItem);
        when(futureItem.get()).thenReturn(new Symptom(1, "Headache"));
        symptomHelper.findById(1);
        verify(executorService).submit(any(Callable.class));
        verify(futureItem).get();
    }
    @Test
    public void findByNameTest() throws ExecutionException, InterruptedException {
        SymptomHelper symptomHelper = new SymptomHelper(executorService, symptomDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Symptom(1, "Red skin"),
                new Symptom(2, "Headache")));
        symptomHelper.findByName("Red skin");
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByNamesTest() throws ExecutionException, InterruptedException {
        SymptomHelper symptomHelper = new SymptomHelper(executorService, symptomDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Symptom(1, "Red skin"),
                new Symptom(2, "Headache")));
        symptomHelper.findByNames(new String[]{"Red skin", "Headache"});
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }
    @Test(expected = RuntimeException.class)
    public void insertTest(){
        SymptomHelper symptomHelper = new SymptomHelper(executorService, symptomDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        symptomHelper.insert(new Symptom(null, "Red skin"));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTest(){
        SymptomHelper symptomHelper = new SymptomHelper(executorService, symptomDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        symptomHelper.delete(new Symptom(1, "Red skin"));
    }
}
