package com.herewego.herewegoapi.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herewego.herewegoapi.repository.FixtureStatRepository;
import com.herewego.herewegoapi.repository.FixtureRepository;
import com.herewego.herewegoapi.repository.LeagueRepository;
import com.herewego.herewegoapi.repository.TeamRepository;
import com.herewego.herewegoapi.response.openapi.fixture.Score;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OpenAPICommunicatorTest {

    @Autowired
    OpenAPICommunicator openAPICommunicator;

    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    FixtureStatRepository fixtureStatRepository;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    TeamRepository teamRepository;

    int cnt=0;

//    @Test
//    public void getFixtureByTeamIdTest() throws ForwardException {
//        FixtureByIdResponseVO fixtureByTeamIdResponseVO = openAPICommunicator.getFixtureByTeamId("2020", "40");
//
//        System.out.println(fixtureByTeamIdResponseVO.getResponse().size());
//
//
//        fixtureByTeamIdResponseVO.getResponse().forEach(fixtureResponseVO -> {
//            if (fixtureResponseVO.getLeague().getName().equals("Premier League")) {
//                System.out.println(fixtureResponseVO.getFixture());
//            }
//        });
//
//        System.out.println(cnt);
//    }
//
//    @Test
//    public void getFixtureByFixtureIdTest() throws ForwardException {
//        FixtureByIdResponseVO fixtureByFixtureIdResponseVO = openAPICommunicator.getFixtureByFixtureId("592871");
//
//
//        fixtureByFixtureIdResponseVO.getResponse().forEach(fixtureResponseVO -> {
//            ObjectMapper mapper = new ObjectMapper();
//            try {
//                String jsonStr = mapper.writeValueAsString(fixtureResponseVO.getStatistics().get(0));
//                System.out.println(jsonStr);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//
//            Fixture fixture = fixtureRepository.findByFixtureId(fixtureResponseVO.getFixture().getId());
//            if (ObjectUtils.isEmpty(fixture)) {
//                fixtureRepository.save(Fixture.builder()
//                        .fixtureId(fixtureResponseVO.getFixture().getId())
//                        .timezone(fixtureResponseVO.getFixture().getTimezone())
//                        .date(new java.sql.Timestamp(DateUtil.parse(fixtureResponseVO.getFixture().getDate()).getTime()).toLocalDateTime())
//                        //.date(LocalDateTime.parse(fixtureResponseVO.getFixture().getDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
//                        .timestamp(fixtureResponseVO.getFixture().getTimestamp())
//                        .venueId(fixtureResponseVO.getFixture().getVenue().getId())
//                        .leagueId(fixtureResponseVO.getLeague().getId())
//                        .season(fixtureResponseVO.getLeague().getSeason())
//                        .round(parseRound(fixtureResponseVO.getLeague().getRound()))
//                        .hometeamId(fixtureResponseVO.getTeams().getHome().getId())
//                        .awayteamId(fixtureResponseVO.getTeams().getAway().getId())
//                        .homeScore(fixtureResponseVO.getScore().getFulltime().getHome())
//                        .awayScore(fixtureResponseVO.getScore().getFulltime().getAway())
//                        .build());
//
//            }
//
//
//            //홈팀 데이터 삽입
//            try {
//                fixtureStatRepository.save(FixtureStatistics.builder()
//                        .fixtureId(fixtureResponseVO.getFixture().getId())
//                        .timestamp(fixtureResponseVO.getFixture().getTimestamp())
//                        .leagueId(fixtureResponseVO.getLeague().getId())
//                        .season(fixtureResponseVO.getLeague().getSeason())
//                        .round(parseRound(fixtureResponseVO.getLeague().getRound()))
//                        .teamId(fixtureResponseVO.getTeams().getHome().getId())
//                        .home(1)
//                        .win(parseWinner(fixtureResponseVO.getScore().getFulltime()))
//                        .halfScore(fixtureResponseVO.getScore().getHalftime().getHome())
//                        .fullScore(fixtureResponseVO.getScore().getFulltime().getHome())
//                        .halfLose(fixtureResponseVO.getScore().getHalftime().getAway())
//                        .fullLose(fixtureResponseVO.getScore().getFulltime().getAway())
//                        .statistics(parseStatistics(fixtureResponseVO.getStatistics().get(0)))
//                        .build());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//            //어웨이팀 삽입
//            try {
//                fixtureStatRepository.save(FixtureStatistics.builder()
//                        .fixtureId(fixtureResponseVO.getFixture().getId())
//                        .timestamp(fixtureResponseVO.getFixture().getTimestamp())
//                        .leagueId(fixtureResponseVO.getLeague().getId())
//                        .season(fixtureResponseVO.getLeague().getSeason())
//                        .round(parseRound(fixtureResponseVO.getLeague().getRound()))
//                        .teamId(fixtureResponseVO.getTeams().getAway().getId())
//                        .home(0)
//                        .win(parseWinner(fixtureResponseVO.getScore().getFulltime()))
//                        .halfScore(fixtureResponseVO.getScore().getHalftime().getAway())
//                        .fullScore(fixtureResponseVO.getScore().getFulltime().getAway())
//                        .halfLose(fixtureResponseVO.getScore().getHalftime().getHome())
//                        .fullLose(fixtureResponseVO.getScore().getFulltime().getHome())
//                        .statistics(parseStatistics(fixtureResponseVO.getStatistics().get(0)))
//                        .build());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//            Team homeTeam = teamRepository.findByTeamId(fixtureResponseVO.getTeams().getHome().getId());
//            if (ObjectUtils.isEmpty(homeTeam)) {
//                teamRepository.save(Team.builder()
//                        .teamId(fixtureResponseVO.getTeams().getHome().getId())
//                        .teamName(fixtureResponseVO.getTeams().getHome().getName())
//                        .logo(fixtureResponseVO.getTeams().getHome().getLogo())
//                        .build());
//            }
//
//            Team awayTeam = teamRepository.findByTeamId(fixtureResponseVO.getTeams().getAway().getId());
//            if (ObjectUtils.isEmpty(awayTeam)) {
//                teamRepository.save(Team.builder()
//                        .teamId(fixtureResponseVO.getTeams().getAway().getId())
//                        .teamName(fixtureResponseVO.getTeams().getAway().getName())
//                        .logo(fixtureResponseVO.getTeams().getAway().getLogo())
//                        .build());
//            }
//
//
//            League league = leagueRepository.findByLeagueId(fixtureResponseVO.getLeague().getId());
//            if (ObjectUtils.isEmpty(league)) {
//                leagueRepository.save(League.builder()
//                        .leagueId(fixtureResponseVO.getLeague().getId())
//                        .leagueName(fixtureResponseVO.getLeague().getName())
//                        .logo(fixtureResponseVO.getLeague().getLogo())
//                        .build());
//            }
//
//        });
//    }
//
//    private Integer parseRound(String roundString) {
//        String[] strArr = roundString.split(" ");
//        return Integer.parseInt(strArr[strArr.length-1]);
//    }

    private Integer parseWinner(Score fullTimeScore) {
        if (fullTimeScore.getHome() < fullTimeScore.getAway()) {
            return 0;
        }
        else if (fullTimeScore.getHome() > fullTimeScore.getAway()) {
            return 1;
        }
        else
            return 2;
    }

    private String parseStatistics(Object jsonValue) throws JSONException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(jsonValue);
            JSONObject json = new JSONObject(jsonStr);
            String result = json.getString("statistics");
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "fd";
    }
}
