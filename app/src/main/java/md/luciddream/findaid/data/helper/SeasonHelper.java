package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.SeasonDao;
import md.luciddream.findaid.data.model.Season;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SeasonHelper implements Helper<Season>{
    private ExecutorService executor;
    private SeasonDao seasonDao;

    public SeasonHelper(ExecutorService executor, SeasonDao seasonDao) {
        this.executor = executor;
        this.seasonDao = seasonDao;
    }

    @Override
    public List<Season> findAll() {
        Future<List<Season>> futureList = executor.submit(() ->seasonDao.findAll());
        List<Season> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Season> findByIds(int[] id) {
        Future<List<Season>> futureList = executor.submit(() -> seasonDao.findByIds(id));
        List<Season> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public Season findById(int id) {
        Future<Season> futureList = executor.submit(() -> seasonDao.findById(id));
        Season toReturn = new Season();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    @Override
    public List<Season> findByName(String name) {
        Future<List<Season>> futureList = executor.submit(() -> seasonDao.findByName(name));
        List<Season> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public void insert(Season... items) {
        executor.submit(() -> seasonDao.insert(items));
    }

    @Override
    public void delete(Season item) {
        executor.submit(() -> seasonDao.delete(item));
    }
}
