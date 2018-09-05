package com.example.mtap.locationapp.jobs;

import android.support.annotation.NonNull;

import com.example.mtap.locationapp.dao.LocationDAO;
import com.example.mtap.locationapp.database.LocationDB;
import com.example.mtap.locationapp.model.Location;

public class LocationSyncWorker extends LocationWorker {
  @NonNull @Override public Result doWork() {

    LocationDAO locationDAO = LocationDB.get(getApplicationContext()).locationDAO();
    Location location = new Location();
    location.setLat("12.444555");
    location.setLng("77.88999");
    locationDAO.insert(location);
    return Result.SUCCESS;
  }
}
