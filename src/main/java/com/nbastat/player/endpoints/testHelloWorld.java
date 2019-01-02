package com.nbastat.player.endpoints;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nbastat.player.responses.LeagueDashPlayerStatsResponse;

@RestController
public class testHelloWorld {

	@RequestMapping("/helloWorld")
	public String helloWorld() {
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HttpHeaderInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
		interceptors.add(new HttpHeaderInterceptor("Accept-Encoding", "utf-8"));
		interceptors.add(new HttpHeaderInterceptor("Accept-Language", "en-US,en;q=0.5"));
		interceptors.add(new HttpHeaderInterceptor("Connection", "keep-alive"));
		interceptors.add(new HttpHeaderInterceptor("Host", "stats.nba.com"));
		interceptors.add(new HttpHeaderInterceptor("Referer",
				"http://stats.nba.com/players/t…17&SeasonType=Regular%20Season"));
		interceptors.add(new HttpHeaderInterceptor("User-Agent",
				"Mozilla/5.0 (X11; Fedora; Linu…) Gecko/20100101 Firefox/59.0"));
		interceptors.add(new HttpHeaderInterceptor("x-nba-stats-origin", "stats"));
		interceptors.add(new HttpHeaderInterceptor("x-nba-stats-token", "true"));
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(interceptors);

		try {
			URIBuilder builder = new URIBuilder(
					"https://stats.nba.com/stats/leaguedashplayerstats?College=&Conference=a&Country=&DateFrom=&DateTo=&Division=&DraftPick=&DraftYear=&GameScope=&GameSegment=&Height=&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PORound=0&PaceAdjust=N&PerMode=PerGame&Period=0&PlayerExperience=&PlayerPosition=&PlusMinus=N&Rank=N&Season=2015-16&SeasonSegment=&SeasonType=Regular+Season&ShotClockRange=&StarterBench=&TeamID=0&VsConference=&VsDivision=&Weight=");
			builder.getQueryParams().forEach(parm -> {

				if (parm.getValue() != null && !parm.getValue().isEmpty())
					System.out.println(parm.toString());
			});
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}

		System.out.println("Starting get json.");
		LeagueDashPlayerStatsResponse leagueDashPlayerStatsResponse = restTemplate
				.getForObject(	"https://stats.nba.com/stats/leaguedashplayerstats?College=&Conference=East&Country=&DateFrom=&DateTo=&Division=&DraftPick=&DraftYear=&GameScope=&GameSegment=&Height=&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PORound=0&PaceAdjust=N&PerMode=PerGame&Period=0&PlayerExperience=&PlayerPosition=&PlusMinus=N&Rank=N&Season=2015-16&SeasonSegment=&SeasonType=Regular+Season&ShotClockRange=&StarterBench=&TeamID=0&VsConference=&VsDivision=&Weight=",
								LeagueDashPlayerStatsResponse.class);
		System.out.println("End get json.");
		return leagueDashPlayerStatsResponse.getResultSets()[0].findResultsByPlayerId(201166).toString();
	}

}
