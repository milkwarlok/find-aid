package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.StepDao;
import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Symptom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class StepHelper implements Helper<Step>{
    private ExecutorService executor;
    private StepDao stepDao;

    public StepHelper(ExecutorService executor, StepDao stepDao) {
        this.executor = executor;
        this.stepDao = stepDao;
    }

    @Override
    public List<Step> findAll() {
        Future<List<Step>> futureList = executor.submit(() -> stepDao.findAll());
        List<Step> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Step> findByIds(int[] id) {
        Future<List<Step>> futureList = executor.submit(() -> stepDao.findByIds(id));
        List<Step> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Step> findByName(String name) {
        Future<List<Step>> futureList = executor.submit(() ->stepDao.findByName(name));
        List<Step> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public List<Step> findByNames(String[] names){
        Future<List<Step>> futureList = executor.submit(() -> stepDao.findByNames(names));
        List<Step> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public Boolean exists(String name){
        Future<Boolean> exists = executor.submit(() -> stepDao.exists(name));
        Boolean toReturn = false;
        try {
            toReturn = exists.get();
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public void insert(Step... items) {
        executor.submit(() -> stepDao.insert(items));
    }

    @Override
    public void delete(Step item) {
        executor.submit(() -> stepDao.delete(item));
    }
}
