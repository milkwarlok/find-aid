package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.StepDao;
import md.luciddream.findaid.data.model.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class StepHelper implements Helper<Step>{
    private ExecutorService executor;
    private FindAidDatabase mDb;

    public StepHelper(ExecutorService executor, FindAidDatabase mDb) {
        this.executor = executor;
        this.mDb = mDb;
    }

    @Override
    public List<Step> findAll() {
        Future<List<Step>> futureList = executor.submit(() -> {
            StepDao stepDao = mDb.stepDao();
            return stepDao.findAll();
        });
        List<Step> toReturn = new ArrayList<>();
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
    public List<Step> findByIds(int[] id) {
        Future<List<Step>> futureList = executor.submit(() -> {
            StepDao stepDao = mDb.stepDao();
            return stepDao.findByIds(id);
        });
        List<Step> toReturn = new ArrayList<>();
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
    public List<Step> findByName(String name) {
        Future<List<Step>> futureList = executor.submit(() -> {
            StepDao stepDao = mDb.stepDao();
            return stepDao.findByName(name);
        });
        List<Step> toReturn = new ArrayList<>();
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
    public void insert(Step... items) {
        executor.submit(() -> {
            StepDao stepDao = mDb.stepDao();
            stepDao.insert(items);
        });
    }

    @Override
    public void delete(Step item) {
        executor.submit(() -> {
            StepDao stepDao = mDb.stepDao();
            stepDao.delete(item);
        });
    }
}
