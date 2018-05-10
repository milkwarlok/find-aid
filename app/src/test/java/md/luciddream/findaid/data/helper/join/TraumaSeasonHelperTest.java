package md.luciddream.findaid.data.helper.join;

import md.luciddream.findaid.data.dao.join.TraumaSeasonDao;
import md.luciddream.findaid.data.model.Season;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaOrgan;
import md.luciddream.findaid.data.model.join.TraumaSeason;
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
public class TraumaSeasonHelperTest {

    private ExecutorService executorService;
    private TraumaSeasonDao traumaSeasonDao;
    private Future<List<Trauma>> futureFirstList;
    private Future<List<Season>> futureSecondList;
    private Future<List<TraumaSeason>> futureJoinList;

    @Before
    public void setUp(){
        executorService = Mockito.mock(ExecutorService.class);
        traumaSeasonDao = Mockito.mock(TraumaSeasonDao.class);
        futureFirstList = Mockito.mock(Future.class);
        futureSecondList = Mockito.mock(Future.class);
        futureJoinList = Mockito.mock(Future.class);
    }

    @After
    public void TearDown(){
        executorService.shutdown();
    }


    @Test
    public void getFirstBySecondIdTest() throws ExecutionException, InterruptedException {
        TraumaSeasonHelper traumaSeasonHelper = new TraumaSeasonHelper(executorService, traumaSeasonDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureFirstList);
        when(futureFirstList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Red skin", 10),
                        new Trauma(2,  "Headache", 4)));
        traumaSeasonHelper.getFirstBySecondId(1);
        verify(executorService).submit( any(Callable.class));
        verify(futureFirstList).get();
    }

    @Test
    public void getFirstBySecondNameTest() throws ExecutionException, InterruptedException {
        TraumaSeasonHelper traumaSeasonHelper = new TraumaSeasonHelper(executorService, traumaSeasonDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureFirstList);
        when(futureFirstList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Red skin", 10),
                        new Trauma(2,  "Headache", 4)));
        traumaSeasonHelper.getFirstBySecondName("Winter");
        verify(executorService).submit( any(Callable.class));
        verify(futureFirstList).get();
    }

    @Test
    public void getSecondByFirstIdTest() throws ExecutionException, InterruptedException {
        TraumaSeasonHelper traumaSeasonHelper = new TraumaSeasonHelper(executorService, traumaSeasonDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Season(1, "Winter"),
                        new Season(2,  "Spring")));
        traumaSeasonHelper.getSecondByFirstId(1);
        verify(executorService).submit( any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void getSecondByFirstIdName() throws ExecutionException, InterruptedException {
        TraumaSeasonHelper traumaSeasonHelper = new TraumaSeasonHelper(executorService, traumaSeasonDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Season(1, "Winter"),
                        new Season(2,  "Spring")));
        traumaSeasonHelper.getSecondByFirstName("Sunburn");
        verify(executorService).submit( any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void findAllTest() throws ExecutionException, InterruptedException {
        TraumaSeasonHelper traumaSeasonHelper = new TraumaSeasonHelper(executorService, traumaSeasonDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureJoinList);
        when(futureJoinList.get())
                .thenReturn(Arrays.asList(
                        new TraumaSeason(1, 1),
                        new TraumaSeason(2, 2)));
        traumaSeasonHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureJoinList).get();
    }
    @Test(expected = RuntimeException.class)
    public void insertTest() {
        TraumaSeasonHelper helper = new TraumaSeasonHelper(executorService, traumaSeasonDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        helper.insert(new TraumaSeason(1, 1));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTest() {
        TraumaSeasonHelper helper = new TraumaSeasonHelper(executorService, traumaSeasonDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        helper.delete(new TraumaSeason(1, 1));
    }
}
