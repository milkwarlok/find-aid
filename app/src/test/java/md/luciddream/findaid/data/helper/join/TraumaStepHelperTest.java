package md.luciddream.findaid.data.helper.join;

import md.luciddream.findaid.data.dao.join.TraumaStepDao;
import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaSeason;
import md.luciddream.findaid.data.model.join.TraumaStep;
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
public class TraumaStepHelperTest {

    private ExecutorService executorService;
    private TraumaStepDao traumaStepDao;
    private Future<List<Trauma>> futureFirstList;
    private Future<List<Step>> futureSecondList;
    private Future<List<TraumaStep>> futureJoinList;

    @Before
    public void setUp() {
        executorService = Mockito.mock(ExecutorService.class);
        traumaStepDao = Mockito.mock(TraumaStepDao.class);
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
        TraumaStepHelper traumaStepHelper = new TraumaStepHelper(executorService, traumaStepDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureFirstList);
        when(futureFirstList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Red skin", 10),
                        new Trauma(2, "Headache", 4)));
        traumaStepHelper.getFirstBySecondId(1);
        verify(executorService).submit(any(Callable.class));
        verify(futureFirstList).get();
    }

    @Test
    public void getFirstBySecondNameTest() throws ExecutionException, InterruptedException {
        TraumaStepHelper traumaStepHelper = new TraumaStepHelper(executorService, traumaStepDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureFirstList);
        when(futureFirstList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Red skin", 10),
                        new Trauma(2, "Headache", 4)));
        traumaStepHelper.getFirstBySecondName("Call ambulance.");
        verify(executorService).submit(any(Callable.class));
        verify(futureFirstList).get();
    }

    @Test
    public void getSecondByFirstIdTest() throws ExecutionException, InterruptedException {
        TraumaStepHelper traumaStepHelper = new TraumaStepHelper(executorService, traumaStepDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Step(1, "Call ambulance"),
                        new Step(2, "Find water")));
        traumaStepHelper.getSecondByFirstId(1);
        verify(executorService).submit(any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void getSecondByFirstIdName() throws ExecutionException, InterruptedException {
        TraumaStepHelper traumaStepHelper = new TraumaStepHelper(executorService, traumaStepDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Step(1, "Call ambulance"),
                        new Step(2, "Find water")));
        traumaStepHelper.getSecondByFirstName("Sunburn");
        verify(executorService).submit(any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void getOrderedStepByTraumaId() throws ExecutionException, InterruptedException {
        TraumaStepHelper traumaStepHelper = new TraumaStepHelper(executorService, traumaStepDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Step(1, "Call ambulance"),
                        new Step(2, "Find water")));
        traumaStepHelper.getOrderedStepByTraumaId(1);
        verify(executorService).submit(any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void getOrderedStepByTraumaName() throws ExecutionException, InterruptedException {
        TraumaStepHelper traumaStepHelper = new TraumaStepHelper(executorService, traumaStepDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Step(1, "Call ambulance"),
                        new Step(2, "Find water")));
        traumaStepHelper.getOrderedStepByTraumaName("Burn");
        verify(executorService).submit(any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void findAllTest() throws ExecutionException, InterruptedException {
        TraumaStepHelper traumaStepHelper = new TraumaStepHelper(executorService, traumaStepDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureJoinList);
        when(futureJoinList.get())
                .thenReturn(Arrays.asList(
                        new TraumaStep(1, 1),
                        new TraumaStep(2, 2)));
        traumaStepHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureJoinList).get();
    }

    @Test(expected = RuntimeException.class)
    public void insertTest() {
        TraumaStepHelper helper = new TraumaStepHelper(executorService, traumaStepDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        helper.insert(new TraumaStep(1, 1));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTest() {
        TraumaStepHelper helper = new TraumaStepHelper(executorService, traumaStepDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        helper.delete(new TraumaStep(1, 1));
    }

}
