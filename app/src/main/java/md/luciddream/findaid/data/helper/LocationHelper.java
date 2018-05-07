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
    private FindAidDatabase mDb;

    public LocationHelper(ExecutorService executor, FindAidDatabase mDb) {
        this.executor = executor;
        this.mDb = mDb;
    }

    @Override
    public List<Location> findAll() {
        Future<List<Location>> futureList = executor.submit(() -> {
            LocationDao locationDao = mDb.locationDao();
            return locationDao.findAll();
        });
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
        Future<List<Location>> futureList = executor.submit(() -> {
            LocationDao locationDao = mDb.locationDao();
            return locationDao.findByIds(id);
        });
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
        Future<List<Location>> futureList = executor.submit(() -> {
            LocationDao locationDao = mDb.locationDao();
            return locationDao.findByName(name);
        });
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
        executor.submit(() -> {
            LocationDao locationDao = mDb.locationDao();
            locationDao.insert(items);
        });
    }

    @Override
    public void delete(Location item) {
        executor.submit(() -> {
            LocationDao locationDao = mDb.locationDao();
            locationDao.delete(item);
        });
    }
}
