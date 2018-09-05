package com.example.mtap.locationapp.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mtap.locationapp.dao.LocationDAO;
import com.example.mtap.locationapp.database.LocationDB;

import java.util.List;

public class LocationViewModel extends AndroidViewModel{
  LocationDAO locationDAO;
  public LocationViewModel(@NonNull Application application) {
    super(application);
    locationDAO = LocationDB.get(application).locationDAO();
  }

  public LiveData<List<Location>> getLocations() {

    return locationDAO.getAllLocations();
  }

}