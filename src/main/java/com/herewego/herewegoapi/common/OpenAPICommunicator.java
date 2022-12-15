package com.herewego.herewegoapi.common;

import com.herewego.herewegoapi.exceptions.ErrorCode;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.response.openapi.error.FixtureByTeamIdErrorResponseVO;
import com.herewego.herewegoapi.response.openapi.fixture.FixtureByIdResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class OpenAPICommunicator {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenAPICommunicator.class);

    private static final String HEADER_RAPIDAPI_KEY = "c5f7cfe6abmsh29132c5f54915f6p16b75djsnd3a4891c19cb"; //TODO: 파라미터 스토어 저장
    private static final String HEADER_RAPIDAPI_HOST = "api-football-v1.p.rapidapi.com"; // TODO: 파라미터 스토어 저장

    @Autowired
    WebClientBuilderManager webClientBuilderManager;

    public FixtureByIdResponseVO getFixtureByTeamId(String season, String teamId) throws ForwardException {
        FixtureByIdResponseVO fixtureByTeamIdResponseVO = webClientBuilderManager
                .makeCommonWebclientBuilder()
                .build()
                .get()
                .uri("https://" + HEADER_RAPIDAPI_HOST + "/v3/fixtures?season=" + season + "&team=" + teamId)
                .headers(httpHeaders -> {
                    httpHeaders.set(Consts.HEADER_KEY, HEADER_RAPIDAPI_KEY);
                    httpHeaders.set(Consts.HEADER_HOST, HEADER_RAPIDAPI_HOST);
                })
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus != HttpStatus.OK,
                        clientResponse -> clientResponse.bodyToMono(FixtureByTeamIdErrorResponseVO.class)
                )
                .bodyToMono(FixtureByIdResponseVO.class)
                .onErrorResume(throwable -> {
                            if(throwable instanceof FixtureByTeamIdErrorResponseVO) {
                                FixtureByTeamIdErrorResponseVO error = (FixtureByTeamIdErrorResponseVO) throwable;
                                LOGGER.error("Communicator Error : ", error.getMessage());
                            }
                            return Mono.error(new ForwardException(ErrorCode.RC500000));
                })
                .block(Duration.ofSeconds(60L));

        return fixtureByTeamIdResponseVO;
    }

    public FixtureByIdResponseVO getFixtureByFixtureId(String fixtureId) throws ForwardException {
        FixtureByIdResponseVO fixtureByTeamIdResponseVO = webClientBuilderManager
                .makeCommonWebclientBuilder()
                .build()
                .get()
                .uri("https://" + HEADER_RAPIDAPI_HOST + "/v3/fixtures?id=" + fixtureId)
                .headers(httpHeaders -> {
                    httpHeaders.set(Consts.HEADER_KEY, HEADER_RAPIDAPI_KEY);
                    httpHeaders.set(Consts.HEADER_HOST, HEADER_RAPIDAPI_HOST);
                })
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus != HttpStatus.OK,
                        clientResponse -> clientResponse.bodyToMono(FixtureByTeamIdErrorResponseVO.class)
                )
                .bodyToMono(FixtureByIdResponseVO.class)
                .onErrorResume(throwable -> {
                    if(throwable instanceof FixtureByTeamIdErrorResponseVO) {
                        FixtureByTeamIdErrorResponseVO error = (FixtureByTeamIdErrorResponseVO) throwable;
                        LOGGER.error("Communicator Error : ", error.getMessage());
                    }
                    return Mono.error(new ForwardException(ErrorCode.RC500000));
                })
                .block(Duration.ofSeconds(60L));

        return fixtureByTeamIdResponseVO;
    }
}
