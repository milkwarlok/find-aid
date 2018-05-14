package md.luciddream.findaid.data.specific;

import md.luciddream.findaid.data.FindAidDatabase;
import org.junit.*;
import org.mockito.Mockito;

import java.util.concurrent.ExecutorService;

public class SpecificSaverTest {
    private static ExecutorService executorService;
    private SpecificTrauma specificTrauma;
    private static FindAidDatabase findAidDatabase;
    private SpecificSaver specificSaver;

    @BeforeClass
    public static void majorSetUp(){
        executorService = Mockito.mock(ExecutorService.class);
        findAidDatabase = Mockito.mock(FindAidDatabase.class);
    }
    @AfterClass
    public static void majorTearDown(){
        if(executorService != null) executorService.shutdown();
        if(findAidDatabase != null) findAidDatabase.close();
    }

    @Before
    public void minorSetUp(){
        specificTrauma = Mockito.mock(SpecificTrauma.class);
        specificSaver = new SpecificSaver(executorService, specificTrauma, findAidDatabase);
    }

    @Test
    public void saveTest(){

    }

}
