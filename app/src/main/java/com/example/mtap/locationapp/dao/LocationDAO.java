package com.example.mtap.locationapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.mtap.locationapp.model.Location;

import java.util.List;

@Dao
public interface LocationDAO {
  int BUCKET_SIZE = 100;

  @Query("SELECT * FROM location")
  List<Location> getAll();

  @Query("SELECT * FROM location ORDER BY locationId DESC LIMIT 50;")
  LiveData<List<Location>> getAllLocations();


  @Query("DELETE FROM location")
  void deleteAll();

  @Insert
  void insert(Location location);

  @Delete
  void delete(Location... location);

  @Query("SELECT COUNT(*) from location")
  int count();
}
