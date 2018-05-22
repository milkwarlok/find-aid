package md.luciddream.findaid.data.helper.join;


import md.luciddream.findaid.data.dao.OrganDao;
import md.luciddream.findaid.data.dao.join.TraumaOrganDao;
import md.luciddream.findaid.data.model.Organ;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaOrgan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TraumaOrganHelper implements JoinHelper<Trauma, Organ, TraumaOrgan> {

    private ExecutorService executor;
    private TraumaOrganDao traumaOrganDao;

    public TraumaOrganHelper(ExecutorService executor, TraumaOrganDao traumaOrganDao) {
        this.executor = executor;
        this.traumaOrganDao = traumaOrganDao;
    }

    @Override
    public List<Trauma> getFirstBySecondId(int secondId) {
        Future<List<Trauma>> futureList = executor.submit(() -> traumaOrganDao.getTraumasByOrganId(secondId));
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
        Future<List<Trauma>> futureList = executor.submit(() -> traumaOrganDao.getTraumasByOrganName(secondName));
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Organ> getSecondByFirstId(int firstId) {
        Future<List<Organ>> futureList = executor.submit(() -> traumaOrganDao.getOrganByTraumaId(firstId));
        List<Organ> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Organ> getSecondByFirstName(String firstName) {
        Future<List<Organ>> futureList = executor.submit(() -> traumaOrganDao.getOrganByTraumaName(firstName));
        List<Organ> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<TraumaOrgan> findAll() {
        Future<List<TraumaOrgan>> futureList = executor.submit(() -> {
            return traumaOrganDao.findAll();
        });
        List<TraumaOrgan> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public void updateSecondByFirstId(int firstId, int secondId){
        executor.submit(()-> traumaOrganDao.updateSecondByFirstId(firstId, secondId));
    }

    @Override
    public void insert(TraumaOrgan... items) {
        executor.submit(() -> traumaOrganDao.insert(items));
    }

    @Override
    public void delete(TraumaOrgan item) {
        executor.submit(() -> traumaOrganDao.delete(item));
    }

}








