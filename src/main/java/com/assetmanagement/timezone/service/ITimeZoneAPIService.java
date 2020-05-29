package com.assetmanagement.timezone.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ITimeZoneAPIService {
	/**
	 * @param list
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public abstract Optional<String> getTimeZone(List<String> list) throws IOException, InterruptedException;
}
