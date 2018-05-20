package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.SymptomDao;
import md.luciddream.findaid.data.model.Symptom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SymptomHelper implements Helper<Symptom>{
    private ExecutorService executor;
    private SymptomDao symptomDao;

    public SymptomHelper(ExecutorService executor, SymptomDao symptomDao) {
        this.executor = executor;
        this.symptomDao = symptomDao;
    }

    @Override
    public List<Symptom> findAll() {
        Future<List<Symptom>> futureList = executor.submit(() -> symptomDao.findAll());
        List<Symptom> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Symptom> findByIds(int[] id) {
        Future<List<Symptom>> futureList = executor.submit(() -> symptomDao.findByIds(id));
        List<Symptom> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public Symptom findById(int id) {
        Future<Symptom> futureList = executor.submit(() -> symptomDao.findById(id));
        Symptom toReturn = new Symptom();
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
    public List<Symptom> findByName(String name) {
        Future<List<Symptom>> futureList = executor.submit(() -> symptomDao.findByName(name));
        List<Symptom> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public List<Symptom> findByNames(String[] names){
        Future<List<Symptom>> futureList = executor.submit(() -> symptomDao.findByNames(names));
        List<Symptom> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public Boolean exists(String name){
        Future<Boolean> exists = executor.submit(() -> symptomDao.exists(name));
        Boolean toReturn = false;
        try {
            toReturn = exists.get();
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    @Override
    public void insert(Symptom... items) {
        executor.submit(() -> symptomDao.insert(items));
    }

    @Override
    public void delete(Symptom item) {
        executor.submit(() -> symptomDao.delete(item));
    }
}
