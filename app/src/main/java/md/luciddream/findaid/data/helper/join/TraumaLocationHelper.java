package md.luciddream.findaid.data.helper.join;


import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.dao.join.TraumaLocationDao;
import md.luciddream.findaid.data.model.Location;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.model.join.TraumaLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TraumaLocationHelper implements JoinHelper<Trauma, Location, TraumaLocation> {

    private ExecutorService executor;
    private TraumaLocationDao traumaLocationDao;

    public TraumaLocationHelper(ExecutorService executor, TraumaLocationDao traumaLocationDao) {
        this.executor = executor;
        this.traumaLocationDao = traumaLocationDao;
    }

    @Override
    public List<Trauma> getFirstBySecondId(int secondId) {
        Future<List<Trauma>> futureList = executor.submit(() -> traumaLocationDao.getTraumasByLocationId(secondId));
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
        Future<List<Trauma>> futureList = executor.submit(() -> traumaLocationDao.getTraumasByLocationName(secondName));
        List<Trauma> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Location> getSecondByFirstId(int firstId) {
        Future<List<Location>> futureList = executor.submit(() -> traumaLocationDao.getLocationByTraumaId(firstId));
        List<Location> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<Location> getSecondByFirstName(String firstName) {
        Future<List<Location>> futureList = executor.submit(() -> traumaLocationDao.getLocationByTraumaName(firstName));
        List<Location> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    @Override
    public List<TraumaLocation> findAll() {
        Future<List<TraumaLocation>> futureList = executor.submit(() -> {
            return traumaLocationDao.findAll();
        });
        List<TraumaLocation> toReturn = new ArrayList<>();
        try {
            toReturn = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    @Override
    public void insert(TraumaLocation... items) {
        executor.submit(() -> traumaLocationDao.insert(items));
    }

    @Override
    public void delete(TraumaLocation item) {
        executor.submit(() -> traumaLocationDao.delete(item));
    }

}








