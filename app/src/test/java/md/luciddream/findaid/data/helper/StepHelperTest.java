package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.dao.StepDao;
import md.luciddream.findaid.data.model.Step;
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
public class StepHelperTest {
    private ExecutorService executorService;
    private static StepDao stepDao;
    private Future<List<Step>> futureList;

    @BeforeClass
    public static void createDb(){
        stepDao = Mockito.mock(StepDao.class);
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
        StepHelper stepHelper = new StepHelper(executorService, stepDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get())
                .thenReturn(Arrays.asList(
                        new Step(1, "Call ambulance"),
                        new Step(2, "Find water")));
        stepHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByIdsTest() throws ExecutionException, InterruptedException {
        StepHelper stepHelper = new StepHelper(executorService, stepDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Step(1, "Call ambulance"),
                new Step(2, "Find water")));
        stepHelper.findByIds(new int[]{1, 2});
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByNameTest() throws ExecutionException, InterruptedException {
        StepHelper stepHelper = new StepHelper(executorService, stepDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Step(1, "Call ambulance"),
                new Step(2, "Find water")));
        stepHelper.findByName("Call ambulance.");
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test(expected = RuntimeException.class)
    public void insertTest(){
        StepHelper stepHelper = new StepHelper(executorService, stepDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        stepHelper.insert(new Step(null, "Call ambulance."));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTest(){
        StepHelper stepHelper = new StepHelper(executorService, stepDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        stepHelper.delete(new Step(1, "Call ambulance."));
    }
}
