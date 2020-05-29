package com.assetmanagement.timezone.controller;

import java.io.IOException;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assetmanagement.timezone.exception.DeviceNotFoundException;
import com.assetmanagement.timezone.exception.TimeZoneNotFoundException;
import com.assetmanagement.timezone.service.IDeviceManagerService;

@RestController
@Validated
public class TimeZoneController {

	@Autowired
	IDeviceManagerService deviceManagerServiceImpl;

	/**
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getLocalTime/{deviceId}")
	public String getLocalTime(
			@PathVariable(value = "deviceId") @NonNull @Pattern(regexp = "^[A-Za-z0-9]+$") String deviceId)
			throws Exception {

		String localTime;

		try {
			localTime = deviceManagerServiceImpl.getLocalTime(deviceId);
		} catch (DeviceNotFoundException | TimeZoneNotFoundException | IOException | InterruptedException e) {
			throw e;
		}

		return localTime;

	}
}
