package com.assetmanagement.timezone.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TimeZoneAPIServiceImpl implements ITimeZoneAPIService {

	/**
	 * Makes an external API call passing the corresponding device timestamp and
	 * coordinates
	 */
	@Override
	public Optional<String> getTimeZone(List<String> list) throws IOException, InterruptedException {

		HttpResponse<String> response = httpCallHandler(buildUrl(list));

		Optional<String> timeZone = Optional.empty();

		JsonNode parent = new ObjectMapper().readTree(response.body()).at("/resourceSets").get(0).at("/resources")
				.get(0).at("/timeZone");

		if (parent.has("convertedTime")) {
			String localTime = parent.requiredAt("/convertedTime/localTime").asText();
			LocalDateTime dateTime = LocalDateTime.parse(localTime);
			ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of(parent.path("ianaTimeZoneId").asText()));

			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss z Z");

			timeZone = Optional.of(zonedDateTime.format(formatter2));
		}

		return timeZone;

	}

	/**
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private HttpResponse<String> httpCallHandler(String url) throws IOException, InterruptedException {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_2).build();

		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

		return response;
	}

	/**
	 * Build URL for API call
	 * 
	 * @param list
	 * @return
	 */
	private String buildUrl(List<String> list) {

		String delimiter = ",";

		Instant ins = Instant.ofEpochSecond(Long.parseLong(list.get(0).split(delimiter, 3)[0]));

		String url = Constants.TIMEZONE_API_BASE_URL + list.get(0).split(delimiter, 3)[2] + ","
				+ list.get(0).split(delimiter, 3)[1] + "?datetime=" + ins + "&key=" + Constants.TIMEZONE_API_KEY;

		return url;
	}

}
