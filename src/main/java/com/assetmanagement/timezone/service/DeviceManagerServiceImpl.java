package com.assetmanagement.timezone.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assetmanagement.timezone.exception.DeviceNotFoundException;
import com.assetmanagement.timezone.exception.TimeZoneNotFoundException;

@Service
public class DeviceManagerServiceImpl implements IDeviceManagerService {

	@Autowired
	TimeZoneAPIServiceImpl timeZoneAPIServiceImpl;

	/**
	 * Fetches timestamp of the reading in the local time zone for a given device id
	 */
	@Override
	public String getLocalTime(String deviceId)
			throws DeviceNotFoundException, TimeZoneNotFoundException, IOException, InterruptedException {

		Map<String, List<String>> csv = getCSVContents();

		return getTimeZone(csv, deviceId);

	}

	/**
	 * Retrieves contents of CSV file to a map
	 * 
	 * @return
	 * @throws IOException
	 */
	private Map<String, List<String>> getCSVContents() throws IOException {

		String delimiter = ",";

		Map<String, List<String>> map = new HashMap<>();

		try (Stream<String> lines = Files.lines(Paths.get(Constants.CSV_FILE_PATH)).skip(1)) {
			lines.filter(line -> line.contains(delimiter)).forEach(

					line -> map.computeIfAbsent(line.split(delimiter, 2)[0], k -> new ArrayList<String>())
							.add(line.split(delimiter, 2)[1]));
		}

		catch (IOException e) {
			throw e;
		}
		return map;
	}

	/**
	 * Given a device id and CSV records, this method fetches the timestamp in local
	 * time
	 * 
	 * @param csv
	 * @param deviceId
	 * @return
	 * @throws DeviceNotFoundException
	 * @throws TimeZoneNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private String getTimeZone(Map<String, List<String>> csv, String deviceId)
			throws DeviceNotFoundException, TimeZoneNotFoundException, IOException, InterruptedException {

		List<String> list = csv.get(deviceId);

		if (list == null) {
			throw new DeviceNotFoundException();
		}

		Optional<String> timeZone = timeZoneAPIServiceImpl.getTimeZone(list);
		return timeZone.orElseThrow(TimeZoneNotFoundException::new);

	}

}
