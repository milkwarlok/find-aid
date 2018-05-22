package md.luciddream.findaid.data.helper.join;


import md.luciddream.findaid.data.dao.SymptomDao;
import md.luciddream.findaid.data.dao.join.TraumaSymptomDao;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaSymptom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TraumaSymptomHelper implements JoinHelper<Trauma, Symptom, TraumaSymptom> {

    private ExecutorService executor;
    private TraumaSymptomDao traumaSymptomDao;

    public TraumaSymptomHelper(ExecutorService executor, TraumaSymptomDao traumaSymptomDao) {
        this.executor = executor;
        this.traumaSymptomDao = traumaSymptomDao;
    }

    @Override
    public List<Trauma> getFirstBySecondId(int secondId) {
        Future<List<Trauma>> futureList = executor.submit(() -> traumaSymptomDao.getTraumasBySymptomId(secondId));
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Trauma> getFirstBySecondName(String secondName) {
        Future<List<Trauma>> futureList = executor.submit(() -> traumaSymptomDao.getTraumasBySymptomName(secondName));
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Symptom> getSecondByFirstId(int firstId) {
        Future<List<Symptom>> futureList = executor.submit(() -> traumaSymptomDao.getSymptomByTraumaId(firstId));
        List<Symptom> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Symptom> getSecondByFirstName(String firstName) {
        Future<List<Symptom>> futureList = executor.submit(() -> traumaSymptomDao.getSymptomByTraumaName(firstName));
        List<Symptom> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<TraumaSymptom> findAll() {
        Future<List<TraumaSymptom>> futureList = executor.submit(() -> {
            return traumaSymptomDao.findAll();
        });
        List<TraumaSymptom> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    @Override
    public void insert(TraumaSymptom... items) {
        executor.submit(() -> traumaSymptomDao.insert(items));
    }

    @Override
    public void delete(TraumaSymptom item) {
        executor.submit(() -> traumaSymptomDao.delete(item));
    }

    public void delete(int t_id, int sm_id){
        executor.submit(() -> traumaSymptomDao.delete(t_id, sm_id));
    }

}








