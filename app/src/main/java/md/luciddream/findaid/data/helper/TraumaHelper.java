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
    private FindAidDatabase mDb;

    public TraumaHelper(ExecutorService executor, FindAidDatabase mDb) {
        this.executor = executor;
        this.mDb = mDb;
    }

    @Override
    public List<Trauma> findAll() {
        Future<List<Trauma>> futureList = executor.submit(() -> {
            TraumaDao traumaDao = mDb.traumaDao();
            return traumaDao.findAll();
        });
        List<Trauma> toReturn = new ArrayList<>();
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
    public List<Trauma> findByIds(int[] id) {
        Future<List<Trauma>> futureList = executor.submit(() -> {
            TraumaDao traumaDao = mDb.traumaDao();
            return traumaDao.findByIds(id);
        });
        List<Trauma> toReturn = new ArrayList<>();
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
        Future<List<Trauma>> futureList = executor.submit(() -> {
            TraumaDao traumaDao = mDb.traumaDao();
            return traumaDao.findByName(name);
        });
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public List<Trauma> findFirstMostRelevant(Integer howMany){
        Future<List<Trauma>> futureList = executor.submit(() -> {
            TraumaDao traumaDao = mDb.traumaDao();
            return traumaDao.findFirstMostRelevant(howMany);
        });
        List<Trauma> toReturn = new ArrayList<>();
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
    public void insert(Trauma... items) {
        executor.submit(() -> {
            TraumaDao traumaDao = mDb.traumaDao();
            traumaDao.insert(items);
        });
    }

    @Override
    public void delete(Trauma item) {
        executor.submit(() -> {
            TraumaDao traumaDao = mDb.traumaDao();
            traumaDao.delete(item);
        });
    }
}