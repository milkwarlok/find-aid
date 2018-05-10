package md.luciddream.findaid.data.helper.join;


import android.arch.persistence.room.Query;
import md.luciddream.findaid.data.dao.StepDao;
import md.luciddream.findaid.data.dao.join.TraumaStepDao;
import md.luciddream.findaid.data.model.Step;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaStep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TraumaStepHelper implements JoinHelper<Trauma, Step, TraumaStep> {

    private ExecutorService executor;
    private TraumaStepDao traumaStepDao;

    public TraumaStepHelper(ExecutorService executor, TraumaStepDao traumaStepDao) {
        this.executor = executor;
        this.traumaStepDao = traumaStepDao;
    }

    @Override
    public List<Trauma> getFirstBySecondId(int secondId) {
        Future<List<Trauma>> futureList = executor.submit(() -> traumaStepDao.getTraumasByStepId(secondId));
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
        Future<List<Trauma>> futureList = executor.submit(() -> traumaStepDao.getTraumasByStepName(secondName));
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Step> getSecondByFirstId(int firstId) {
        Future<List<Step>> futureList = executor.submit(() -> traumaStepDao.getStepByTraumaId(firstId));
        List<Step> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Step> getSecondByFirstName(String firstName) {
        Future<List<Step>> futureList = executor.submit(() -> traumaStepDao.getStepByTraumaName(firstName));
        List<Step> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    List<Step> getOrderedStepByTraumaId(int traumaId){
        Future<List<Step>> futureList = executor.submit(() -> traumaStepDao.getOrderedStepByTraumaId(traumaId));
        List<Step> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    List<Step> getOrderedStepByTraumaName(String traumaName){
        Future<List<Step>> futureList = executor.submit(() -> traumaStepDao.getOrderedStepByTraumaName(traumaName));
        List<Step> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    @Override
    public List<TraumaStep> findAll() {
        Future<List<TraumaStep>> futureList = executor.submit(() -> {
            return traumaStepDao.findAll();
        });
        List<TraumaStep> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    @Override
    public void insert(TraumaStep... items) {
        executor.submit(() -> traumaStepDao.insert(items));
    }

    @Override
    public void delete(TraumaStep item) {
        executor.submit(() -> traumaStepDao.delete(item));
    }

}








