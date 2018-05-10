package md.luciddream.findaid.data.helper;

import md.luciddream.findaid.data.FindAidDatabase;
import md.luciddream.findaid.data.dao.LocationDao;
import md.luciddream.findaid.data.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class LocationHelper implements Helper<Location>{
    private ExecutorService executor;
    private LocationDao locationDao;

    public LocationHelper(ExecutorService executor, LocationDao locationDao) {
        this.executor = executor;
        this.locationDao = locationDao;
    }

    @Override
    public List<Location> findAll() {
        Future<List<Location>> futureList = executor.submit(() -> locationDao.findAll());
        List<Location> toReturn = new ArrayList<>();
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
    public List<Location> findByIds(int[] id) {
        Future<List<Location>> futureList = executor.submit(() -> locationDao.findByIds(id));
        List<Location> toReturn = new ArrayList<>();
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
    public List<Location> findByName(String name) {
        Future<List<Location>> futureList = executor.submit(() -> locationDao.findByName(name));
        List<Location> toReturn = new ArrayList<>();
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
    public void insert(Location... items) {
        executor.submit(() -> locationDao.insert(items));
    }

    @Override
    public void delete(Location item) {
        executor.submit(() -> locationDao.delete(item));
    }
}
