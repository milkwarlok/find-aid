package md.luciddream.findaid.data.helper.join;

import md.luciddream.findaid.data.dao.join.TraumaSymptomDao;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaSeason;
import md.luciddream.findaid.data.model.join.TraumaSymptom;
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
public class TraumaSymptomHelperTest {

    private ExecutorService executorService;
    private TraumaSymptomDao traumaSymptomDao;
    private Future<List<Trauma>> futureFirstList;
    private Future<List<Symptom>> futureSecondList;
    private Future<List<TraumaSymptom>> futureJoinList;

    @Before
    public void setUp(){
        executorService = Mockito.mock(ExecutorService.class);
        traumaSymptomDao = Mockito.mock(TraumaSymptomDao.class);
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
        TraumaSymptomHelper traumaSymptomHelper = new TraumaSymptomHelper(executorService, traumaSymptomDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureFirstList);
        when(futureFirstList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Red skin", 10),
                        new Trauma(2,  "Headache", 4)));
        traumaSymptomHelper.getFirstBySecondId(1);
        verify(executorService).submit( any(Callable.class));
        verify(futureFirstList).get();
    }

    @Test
    public void getFirstBySecondNameTest() throws ExecutionException, InterruptedException {
        TraumaSymptomHelper traumaSymptomHelper = new TraumaSymptomHelper(executorService, traumaSymptomDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureFirstList);
        when(futureFirstList.get())
                .thenReturn(Arrays.asList(
                        new Trauma(1, "Red skin", 10),
                        new Trauma(2,  "Headache", 4)));
        traumaSymptomHelper.getFirstBySecondName("Headache");
        verify(executorService).submit( any(Callable.class));
        verify(futureFirstList).get();
    }

    @Test
    public void getSecondByFirstIdTest() throws ExecutionException, InterruptedException {
        TraumaSymptomHelper traumaSymptomHelper = new TraumaSymptomHelper(executorService, traumaSymptomDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Symptom(1, "Headache"),
                        new Symptom(2,  "Red skin")));
        traumaSymptomHelper.getSecondByFirstId(1);
        verify(executorService).submit( any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void getSecondByFirstIdName() throws ExecutionException, InterruptedException {
        TraumaSymptomHelper traumaSymptomHelper = new TraumaSymptomHelper(executorService, traumaSymptomDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureSecondList);
        when(futureSecondList.get())
                .thenReturn(Arrays.asList(
                        new Symptom(1, "Headache"),
                        new Symptom(2,  "Red skin")));
        traumaSymptomHelper.getSecondByFirstName("Sunburn");
        verify(executorService).submit( any(Callable.class));
        verify(futureSecondList).get();
    }

    @Test
    public void findAllTest() throws ExecutionException, InterruptedException {
        TraumaSymptomHelper traumaSymptomHelper = new TraumaSymptomHelper(executorService, traumaSymptomDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureJoinList);
        when(futureJoinList.get())
                .thenReturn(Arrays.asList(
                        new TraumaSymptom(1, 1),
                        new TraumaSymptom(2, 2)));
        traumaSymptomHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureJoinList).get();
    }
    @Test(expected = RuntimeException.class)
    public void insertTest() {
        TraumaSymptomHelper helper = new TraumaSymptomHelper(executorService, traumaSymptomDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        helper.insert(new TraumaSymptom(1, 1));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTest() {
        TraumaSymptomHelper helper = new TraumaSymptomHelper(executorService, traumaSymptomDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        helper.delete(new TraumaSymptom(1, 1));
    }

}
