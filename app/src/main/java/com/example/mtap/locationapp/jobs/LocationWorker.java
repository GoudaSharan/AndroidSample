package com.example.mtap.locationapp.jobs;

import java.util.concurrent.TimeUnit;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;

public abstract class LocationWorker extends Worker {

  public static final int ONE_TIME_REQUEST = 0;
  public static final int PERIODIC_REQUEST = 1;

  private WorkManager workManager;
  private Constraints.Builder workConstraints;
  private long interval = 30;
  private TimeUnit periodicTimeUnit = TimeUnit.MINUTES;
  private BackoffPolicy backoffPolicy = BackoffPolicy.EXPONENTIAL;
  private long delay = 5;
  private TimeUnit backOffTimeUnit = TimeUnit.MINUTES;
  private WorkRequest workRequest;
  private Data data;
  private String tag;

  protected LocationWorker() {
    workManager = WorkManager.getInstance();
    workConstraints = new Constraints.Builder();
    workConstraints.setRequiredNetworkType(NetworkType.CONNECTED);
  }

  public void setPeriodicInterval(long interval, TimeUnit timeUnit) {
    this.interval = interval;
    this.periodicTimeUnit = timeUnit;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public void setInputData(Data data) {
    this.data = data;
  }

  public void setPeriodicBackOffCriteria(BackoffPolicy backoffPolicy, long delay,
                                         TimeUnit backOffTimeUnit) {

    this.backoffPolicy = backoffPolicy;
    this.delay = delay;
    this.backOffTimeUnit = backOffTimeUnit;
  }

  public Constraints.Builder getConstraints() {
    return workConstraints;
  }

  public WorkRequest getWorkRequest() {
    return workRequest;
  }

  private void executeWork(WorkRequest workRequest) {
    if (workManager == null) return;
    workManager.enqueue(workRequest);
  }

  public LocationWorker createWorkRequest(int requestType) {
    WorkRequest.Builder workRequestBuilder;
    switch (requestType) {
      case ONE_TIME_REQUEST:
        workRequestBuilder = new OneTimeWorkRequest.Builder(getClass())
            .setConstraints(getConstraints().build());
        break;
      case PERIODIC_REQUEST:
        workRequestBuilder = new PeriodicWorkRequest.Builder(getClass(), interval, periodicTimeUnit)
            .setConstraints(getConstraints().build())
            .setBackoffCriteria(backoffPolicy, delay, backOffTimeUnit);
        break;
      default:
        workRequestBuilder = new OneTimeWorkRequest.Builder(getClass())
            .setConstraints(getConstraints().build());
    }
    if (data != null) {
      workRequestBuilder.setInputData(data);
    }
    if (tag != null && !tag.isEmpty()) {
      workRequestBuilder.addTag(tag);
    }
    workRequest = workRequestBuilder.build();
    return this;
  }

  public void execute() {
    executeWork(workRequest);
  }

}
