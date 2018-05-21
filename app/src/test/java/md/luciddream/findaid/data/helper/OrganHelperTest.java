package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.dao.OrganDao;
import md.luciddream.findaid.data.model.Organ;
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
public class OrganHelperTest {
    private ExecutorService executorService;
    private static OrganDao organDao;
    private Future<List<Organ>> futureList;
    private Future<Organ> futureItem;

    @BeforeClass
    public static void createDb(){
        organDao = Mockito.mock(OrganDao.class);
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
        OrganHelper organHelper = new OrganHelper(executorService, organDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get())
                .thenReturn(Arrays.asList(
                        new Organ(1, "Skin"),
                        new Organ(2, "Head")));
        organHelper.findAll();
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByIdsTest() throws ExecutionException, InterruptedException {
        OrganHelper organHelper = new OrganHelper(executorService, organDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Organ(1, "Skin"),
                new Organ(2, "Head")));
        organHelper.findByIds(new int[]{1, 2});
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test
    public void findByIdTest() throws ExecutionException, InterruptedException {
        OrganHelper organHelper = new OrganHelper(executorService, organDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureItem);
        when(futureItem.get()).thenReturn(new Organ(1, "Skin"));
        organHelper.findById(1);
        verify(executorService).submit(any(Callable.class));
        verify(futureItem).get();
    }

    @Test
    public void findByNameTest() throws ExecutionException, InterruptedException {
        OrganHelper organHelper = new OrganHelper(executorService, organDao);
        when(executorService.submit(any(Callable.class))).thenReturn(futureList);
        when(futureList.get()).thenReturn(Arrays.asList(
                new Organ(1, "Skin"),
                new Organ(2, "Head")));
        organHelper.findByName("Skin");
        verify(executorService).submit(any(Callable.class));
        verify(futureList).get();
    }

    @Test(expected = RuntimeException.class)
    public void insertTest(){
        OrganHelper organHelper = new OrganHelper(executorService, organDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        organHelper.insert(new Organ(null, "Skin"));
    }

    @Test(expected = RuntimeException.class)
    public void deleteTest(){
        OrganHelper organHelper = new OrganHelper(executorService, organDao);
        when(executorService.submit(any(Runnable.class))).thenThrow(new RuntimeException());
        organHelper.delete(new Organ(1, "Skin"));
    }
}
