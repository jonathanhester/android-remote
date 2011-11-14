package com.myandroidremote.commands;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

class LocationManagerThread {
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	private static LocationManagerThread locationManagerThread = null;
	private Context context;
	private Location currentBestLocation = null;
	private Thread t;
	final LocationManager locationManager;
	private MyLocationListener locationListener;

	public LocationManagerThread(Context context) {
		this.context = context;
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
	}

	public static LocationManagerThread getInstance(Context context) {
		if (locationManagerThread == null) {
			locationManagerThread = new LocationManagerThread(context);
		}
		return locationManagerThread;
	}

	public void start(final LocationCommand locationCommand) {
		if (t != null)
			return;

		locationListener = new MyLocationListener(locationCommand);

		t = new Thread() {
			public void run() {
				Looper.prepare();
				try {
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, 0, 0,
							locationListener);
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 0, 0,
							locationListener);
					String locationProvider = LocationManager.NETWORK_PROVIDER;
					Location lastKnownLocation = locationManager
							.getLastKnownLocation(locationProvider);
					locationCommand.sendLocation(lastKnownLocation, context);
					// message = mHandler.obtainMessage(1, o);
				} catch (Exception e) {
					// message = mHandler.obtainMessage(1, e);
					Log.d("exception", e.getMessage());
				}
				Looper.loop();
			}
		};
		t.start();
	}

	public void stop() {
		locationManager.removeUpdates(locationListener);
		t = null;
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	class MyLocationListener implements LocationListener {

		private LocationCommand locationCommand;

		public MyLocationListener(LocationCommand locationCommand) {
			this.locationCommand = locationCommand;
		}

		public void onLocationChanged(Location location) {
			if (isBetterLocation(location, currentBestLocation)) {
				currentBestLocation = location;
				locationCommand.sendLocation(location, context);
			}

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	}
}
