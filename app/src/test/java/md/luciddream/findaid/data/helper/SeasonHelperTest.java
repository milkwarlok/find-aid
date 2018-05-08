package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.model.Season;
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
public class SeasonHelperTest {
    private ExecutorService executorService;
    private static FindAidDatabase findAidDatabase;
    private Future<List<Season>> futureList;

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
        SeasonHelper seasonHelper = new SeasonHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get())
                .thenReturn(Arrays.asList(
                        new Season(1, "Winter"),
                        new Season(2, "Spring")));
        seasonHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByIdsTest() throws ExecutionException, InterruptedException {
        SeasonHelper seasonHelper = new SeasonHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Season(1, "Winter"),
                new Season(2, "Spring")));
        seasonHelper.findByIds(new int[]{1, 2});
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByNameTest() throws ExecutionException, InterruptedException {
        SeasonHelper seasonHelper = new SeasonHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Season(1, "Winter"),
                new Season(2, "Spring")));
        seasonHelper.findByName("Winter");
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test(expected = RuntimeException.class)
    public void insertTest(){
        SeasonHelper seasonHelper = new SeasonHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        seasonHelper.insert(new Season(null, "Winter"));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTest(){
        SeasonHelper seasonHelper = new SeasonHelper(executorService, findAidDatabase);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        seasonHelper.delete(new Season(1, "Winter"));
    }
}
