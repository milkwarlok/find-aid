package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.TraumaDao;
import md.luciddream.findaid.data.model.Trauma;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TraumaHelper implements Helper<Trauma>{
    private ExecutorService executor;
    private TraumaDao traumaDao;

    public TraumaHelper(ExecutorService executor, TraumaDao traumaDao) {
        this.executor = executor;
        this.traumaDao = traumaDao;
    }

    @Override
    public List<Trauma> findAll() {
        Future<List<Trauma>> futureList = executor.submit(() -> traumaDao.findAll());
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Trauma> findByIds(int[] id) {
        Future<List<Trauma>> futureList = executor.submit(() -> traumaDao.findByIds(id));
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public Trauma findById(int id) {
        Future<Trauma> futureList = executor.submit(() -> traumaDao.findById(id));
        Trauma toReturn = new Trauma();
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
    public List<Trauma> findByName(String name) {
        Future<List<Trauma>> futureList = executor.submit(() -> traumaDao.findByName(name));
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public List<Trauma> findFirstMostRelevant(Integer howMany){
        Future<List<Trauma>> futureList = executor.submit(() -> traumaDao.findFirstMostRelevant(howMany));
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public Boolean exists(String name){
        Future<Boolean> exists = executor.submit(() -> traumaDao.exists(name));
        Boolean toReturn = false;
        try {
            toReturn = exists.get();
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public void insert(Trauma... items) {
        executor.submit(() -> traumaDao.insert(items));
    }

    @Override
    public void delete(Trauma item) {
        executor.submit(() -> traumaDao.delete(item));
    }
}
