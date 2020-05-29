package com.assetmanagement.timezone.service;

import java.io.IOException;

import com.assetmanagement.timezone.exception.DeviceNotFoundException;
import com.assetmanagement.timezone.exception.TimeZoneNotFoundException;

public interface IDeviceManagerService {
	/**
	 * @param deviceId
	 * @return
	 * @throws DeviceNotFoundException
	 * @throws TimeZoneNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public abstract String getLocalTime(String deviceId)
			throws DeviceNotFoundException, TimeZoneNotFoundException, IOException, InterruptedException;
}
