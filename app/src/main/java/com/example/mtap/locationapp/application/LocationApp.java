package com.example.mtap.locationapp.application;

import android.app.Application;

import com.example.mtap.locationapp.jobs.LocationSyncWorker;
import com.example.mtap.locationapp.jobs.LocationWorker;


public class LocationApp extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
  }

  public static LocationWorker getWorker() {
    return new LocationSyncWorker();
  }
}