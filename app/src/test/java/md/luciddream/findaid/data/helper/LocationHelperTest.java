package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.model.Location;
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
public class LocationHelperTest {
    private ExecutorService executorService;
    private static LocationDao locationDao;
    private Future<List<Location>> futureList;
    private Future<Location> futureItem;

    @BeforeClass
    public static void createDb(){
        locationDao = Mockito.mock(LocationDao.class);
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
        LocationHelper locationHelper = new LocationHelper(executorService, locationDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get())
                .thenReturn(Arrays.asList(
                        new Location(1, "Sea"),
                        new Location(2, "Mountains")));
        locationHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByIdsTest() throws ExecutionException, InterruptedException {
        LocationHelper locationHelper = new LocationHelper(executorService, locationDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Location(1, "Sea"),
                new Location(2, "Mountains")));
        locationHelper.findByIds(new int[]{1, 2});
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByIdTest() throws ExecutionException, InterruptedException {
        LocationHelper locationHelper = new LocationHelper(executorService, locationDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureItem);
        when(futureItem.get()).thenReturn(new Location(1, "Sea"));
        locationHelper.findById(1);
        verify(executorService).submit(any(Callable.class));
        verify(futureItem).get();
    }

    @Test
    public void findByNameTest() throws ExecutionException, InterruptedException {
        LocationHelper locationHelper = new LocationHelper(executorService, locationDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Location(1, "Sea"),
                new Location(2, "Mountains")));
        locationHelper.findByName("Sea");
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test(expected = RuntimeException.class)
    public void insertTest(){
        LocationHelper locationHelper = new LocationHelper(executorService, locationDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        locationHelper.insert(new Location(null, "Sea"));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTest(){
        LocationHelper locationHelper = new LocationHelper(executorService, locationDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        locationHelper.delete(new Location(1, "Sea"));
    }
}
