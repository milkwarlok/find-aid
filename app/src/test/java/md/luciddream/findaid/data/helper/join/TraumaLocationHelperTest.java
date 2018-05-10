package md.luciddream.findaid.data.helper.join;

import md.luciddream.findaid.data.dao.join.TraumaLocationDao;
import md.luciddream.findaid.data.helper.LocationHelper;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaLocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
public class TraumaLocationHelperTest {

    private ExecutorService executorService;
    private TraumaLocationDao traumaLocationDao;
    private Future<List<Trauma>> futureFirstList;
    private Future<List<Location>> futureSecondList;
    private Future<List<TraumaLocation>> futureJoinList;

    @Before
    public void setUp() {
        executorService = Mockito.mock(ExecutorService.class);
        traumaLocationDao = Mockito.mock(TraumaLocationDao.class);
        futureFirstList = Mockito.mock(Future.class);
        futureSecondList = Mockito.mock(Future.class);
        futureJoinList = Mockito.mock(Future.class);
    }

    @After
    public void TearDown() {
        executorService.shutdown();
    }


    @Test
    public void getFirstBySecondIdTest() throws ExecutionException, InterruptedException {
        TraumaLocationHelper traumaLocationHelper = new TraumaLocationHelper(executorService, traumaLocationDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureFirstList);
        when(futureFirstList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Red skin", 10),
                        new Trauma(2, "Headache", 4)));
        traumaLocationHelper.getFirstBySecondId(1);
        verify(executorService).submit(any(Callable.class));
        verify(futureFirstList).get();
    }

    @Test
    public void getFirstBySecondNameTest() throws ExecutionException, InterruptedException {
        TraumaLocationHelper traumaLocationHelper = new TraumaLocationHelper(executorService, traumaLocationDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureFirstList);
        when(futureFirstList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Red skin", 10),
                        new Trauma(2, "Headache", 4)));
        traumaLocationHelper.getFirstBySecondName("Sea");
        verify(executorService).submit(any(Callable.class));
        verify(futureFirstList).get();
    }

    @Test
    public void getSecondByFirstIdTest() throws ExecutionException, InterruptedException {
        TraumaLocationHelper traumaLocationHelper = new TraumaLocationHelper(executorService, traumaLocationDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Location(1, "Sea"),
                        new Location(2, "Plain")));
        traumaLocationHelper.getSecondByFirstId(1);
        verify(executorService).submit(any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void getSecondByFirstIdName() throws ExecutionException, InterruptedException {
        TraumaLocationHelper traumaLocationHelper = new TraumaLocationHelper(executorService, traumaLocationDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Location(1, "Sea"),
                        new Location(2, "Plain")));
        traumaLocationHelper.getSecondByFirstName("Sunburn");
        verify(executorService).submit(any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void findAllTest() throws ExecutionException, InterruptedException {
        TraumaLocationHelper traumaLocationHelper = new TraumaLocationHelper(executorService, traumaLocationDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureJoinList);
        when(futureJoinList.get())
                .thenReturn(Arrays.asList(
                        new TraumaLocation(1, 1),
                        new TraumaLocation(2, 2)));
        traumaLocationHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureJoinList).get();
    }

    @Test(expected = RuntimeException.class)
    public void insertTest() {
        TraumaLocationHelper helper = new TraumaLocationHelper(executorService, traumaLocationDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        helper.insert(new TraumaLocation(1, 1));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTest() {
        TraumaLocationHelper helper = new TraumaLocationHelper(executorService, traumaLocationDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        helper.delete(new TraumaLocation(1, 1));
    }
}
