package com.example.mtap.locationapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mtap.locationapp.dao.LocationDAO;
import com.example.mtap.locationapp.model.Location;

@Database(entities = {Location.class},
    version = 1, exportSchema = false)
public abstract class LocationDB extends RoomDatabase {
  private static LocationDB instance;

  public abstract LocationDAO locationDAO();

  public static LocationDB get(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(),
          LocationDB.class, "location.db")
          .build();
    }
    return instance;
  }

  public static void destroyInstance() {
    instance = null;
  }
}