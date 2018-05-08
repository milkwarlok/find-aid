package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.OrganDao;
import md.luciddream.findaid.data.model.Organ;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class OrganHelper implements Helper<Organ>{
    private ExecutorService executor;
    private FindAidDatabase mDb;

    public OrganHelper(ExecutorService executor, FindAidDatabase mDb) {
        this.executor = executor;
        this.mDb = mDb;
    }

    @Override
    public List<Organ> findAll() {
        Future<List<Organ>> futureList = executor.submit(() -> {
            OrganDao organDao = mDb.organDao();
            return organDao.findAll();
        });
        List<Organ> toReturn = new ArrayList<>();
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
    public List<Organ> findByIds(int[] id) {
        Future<List<Organ>> futureList = executor.submit(() -> {
            OrganDao organDao = mDb.organDao();
            return organDao.findByIds(id);
        });
        List<Organ> toReturn = new ArrayList<>();
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
    public List<Organ> findByName(String name) {
        Future<List<Organ>> futureList = executor.submit(() -> {
            OrganDao organDao = mDb.organDao();
            return organDao.findByName(name);
        });
        List<Organ> toReturn = new ArrayList<>();
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
    public void insert(Organ... items) {
        executor.submit(() -> {
            OrganDao organDao = mDb.organDao();
            organDao.insert(items);
        });
    }

    @Override
    public void delete(Organ item) {
        executor.submit(() -> {
            OrganDao organDao = mDb.organDao();
            organDao.delete(item);
        });
    }
}
