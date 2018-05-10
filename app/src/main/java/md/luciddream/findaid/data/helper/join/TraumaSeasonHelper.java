package md.luciddream.findaid.data.helper.join;


import md.luciddream.findaid.data.dao.SeasonDao;
import md.luciddream.findaid.data.dao.join.TraumaSeasonDao;
import md.luciddream.findaid.data.model.Season;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaSeason;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TraumaSeasonHelper implements JoinHelper<Trauma, Season, TraumaSeason> {

    private ExecutorService executor;
    private TraumaSeasonDao traumaSeasonDao;

    public TraumaSeasonHelper(ExecutorService executor, TraumaSeasonDao traumaSeasonDao) {
        this.executor = executor;
        this.traumaSeasonDao = traumaSeasonDao;
    }

    @Override
    public List<Trauma> getFirstBySecondId(int secondId) {
        Future<List<Trauma>> futureList = executor.submit(() -> traumaSeasonDao.getTraumasBySeasonId(secondId));
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
        Future<List<Trauma>> futureList = executor.submit(() -> traumaSeasonDao.getTraumasBySeasonName(secondName));
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Season> getSecondByFirstId(int firstId) {
        Future<List<Season>> futureList = executor.submit(() -> traumaSeasonDao.getSeasonByTraumaId(firstId));
        List<Season> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Season> getSecondByFirstName(String firstName) {
        Future<List<Season>> futureList = executor.submit(() -> traumaSeasonDao.getSeasonByTraumaName(firstName));
        List<Season> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<TraumaSeason> findAll() {
        Future<List<TraumaSeason>> futureList = executor.submit(() -> {
            return traumaSeasonDao.findAll();
        });
        List<TraumaSeason> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    @Override
    public void insert(TraumaSeason... items) {
        executor.submit(() -> traumaSeasonDao.insert(items));
    }

    @Override
    public void delete(TraumaSeason item) {
        executor.submit(() -> traumaSeasonDao.delete(item));
    }

}








