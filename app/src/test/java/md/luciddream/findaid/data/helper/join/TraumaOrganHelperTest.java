package md.luciddream.findaid.data.helper.join;

import md.luciddream.findaid.data.dao.join.TraumaOrganDao;
import md.luciddream.findaid.data.model.Organ;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaOrgan;
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
public class TraumaOrganHelperTest {

    private ExecutorService executorService;
    private TraumaOrganDao traumaOrganDao;
    private Future<List<Trauma>> futureFirstList;
    private Future<List<Organ>> futureSecondList;
    private Future<List<TraumaOrgan>> futureJoinList;

    @Before
    public void setUp(){
        executorService = Mockito.mock(ExecutorService.class);
        traumaOrganDao = Mockito.mock(TraumaOrganDao.class);
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
        TraumaOrganHelper traumaOrganHelper = new TraumaOrganHelper(executorService, traumaOrganDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureFirstList);
        when(futureFirstList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Red skin", 10),
                        new Trauma(2,  "Headache", 4)));
        traumaOrganHelper.getFirstBySecondId(1);
        verify(executorService).submit( any(Callable.class));
        verify(futureFirstList).get();
    }

    @Test
    public void getFirstBySecondNameTest() throws ExecutionException, InterruptedException {
        TraumaOrganHelper traumaOrganHelper = new TraumaOrganHelper(executorService, traumaOrganDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureFirstList);
        when(futureFirstList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Red skin", 10),
                        new Trauma(2,  "Headache", 4)));
        traumaOrganHelper.getFirstBySecondName("Sea");
        verify(executorService).submit( any(Callable.class));
        verify(futureFirstList).get();
    }

    @Test
    public void getSecondByFirstIdTest() throws ExecutionException, InterruptedException {
        TraumaOrganHelper traumaOrganHelper = new TraumaOrganHelper(executorService, traumaOrganDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Organ(1, "Sea"),
                        new Organ(2,  "Plain")));
        traumaOrganHelper.getSecondByFirstId(1);
        verify(executorService).submit( any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void getSecondByFirstIdName() throws ExecutionException, InterruptedException {
        TraumaOrganHelper traumaOrganHelper = new TraumaOrganHelper(executorService, traumaOrganDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Organ(1, "Sea"),
                        new Organ(2,  "Plain")));
        traumaOrganHelper.getSecondByFirstName("Sunburn");
        verify(executorService).submit( any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void findAllTest() throws ExecutionException, InterruptedException {
        TraumaOrganHelper traumaOrganHelper = new TraumaOrganHelper(executorService, traumaOrganDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureJoinList);
        when(futureJoinList.get())
                .thenReturn(Arrays.asList(
                        new TraumaOrgan(1, 1),
                        new TraumaOrgan(2, 2)));
        traumaOrganHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureJoinList).get();
    }

}
