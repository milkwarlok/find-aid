package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.model.Trauma;
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
public class TraumaHelperTest {
    private ExecutorService executorService;
    private static FindAidDatabase findAidDatabase;
    private Future<List<Trauma>> futureList;

    @BeforeClass
    public static void createDb(){
        findAidDatabase = Mockito.mock(FindAidDatabase.class);
    }
    @AfterClass
    public static void closeDb(){
        findAidDatabase.close();
    }
    @Before
    public void setUp(){
        executorService = Mockito.mock(ExecutorService.class);
        futureList = Mockito.mock(Future.class);
    }
    @After
    public void tearDown(){
        executorService.shutdown();
    }
    @Test
    public void findAllTest() throws ExecutionException, InterruptedException {
        TraumaHelper traumaHelper = new TraumaHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Burn", 10),
                        new Trauma(2, "Stroke", 9)));
        traumaHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByIdsTest() throws ExecutionException, InterruptedException {
        TraumaHelper traumaHelper = new TraumaHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Trauma(1, "Burn", 10),
                new Trauma(2, "Stroke", 9)));
        traumaHelper.findByIds(new int[]{1, 2});
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByNameTest() throws ExecutionException, InterruptedException {
        TraumaHelper traumaHelper = new TraumaHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Trauma(1, "Burn", 10),
                new Trauma(2, "Stroke", 9)));
        traumaHelper.findByName("Burn");
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }
    @Test
    public void findFirstMostRelevantTest() throws ExecutionException, InterruptedException {
        TraumaHelper traumaHelper = new TraumaHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Trauma(1, "Burn", 10),
                new Trauma(2, "Stroke", 9)
        ));
        traumaHelper.findFirstMostRelevant(2);
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test(expected = RuntimeException.class)
    public void insertTest(){
        TraumaHelper traumaHelper = new TraumaHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        traumaHelper.insert(new Trauma(null, "Burn", 10));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTest(){
        TraumaHelper traumaHelper = new TraumaHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        traumaHelper.delete(new Trauma(1, "Burn", 10));
    }
}
