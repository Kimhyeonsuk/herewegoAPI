package com.herewego.herewegoapi.controller;

import com.herewego.herewegoapi.common.Consts;
import com.herewego.herewegoapi.model.entity.Team;
import com.herewego.herewegoapi.model.entity.UserDetails;
import com.herewego.herewegoapi.repository.TeamRepository;
import com.herewego.herewegoapi.repository.UserDetailsRepository;
import com.herewego.herewegoapi.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class TeamControllerTest {

    private MockMvc mockMvc;

    private RestDocumentationResultHandler document;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider contextProvider) {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(contextProvider))
                .alwaysDo(document)
                .build();

        userDetailsRepository.save(UserDetails.builder()
                .userId(Consts.USERID)
                .favorites(Consts.FAVORITETEAM2)
                .build());

        teamRepository.save(Team.builder()
                .teamId(Consts.TEAMID1)
                .leagueId(Consts.LEAGUEID)
                .season(Consts.SEASON)
                .teamName(Consts.TEAMNAME1)
                .logo(Consts.LOGOURL)
                .build());

        teamRepository.save(Team.builder()
                .teamId(Consts.TEAMID2)
                .leagueId(Consts.LEAGUEID)
                .season(Consts.SEASON)
                .teamName(Consts.TEAMNAME2)
                .logo(Consts.LOGOURL)
                .build());
    }
    @AfterEach
    public void teardown() {
        userDetailsRepository.deleteByUserId(Consts.USERID);
        teamRepository.deleteByTeamId(Consts.TEAMID1);
        teamRepository.deleteByTeamId(Consts.TEAMID2);
    }


    @Test
    void updateFavoriteTeam() throws Exception{
//        given
        int teamId = Consts.TEAMID2;
        String accountToken = Consts.ACCESSTOKEN;
        String userId = Consts.USERID;

        this.mockMvc.perform(put("/v1/teams/favorites?teamId="+Integer.toString(teamId))
                .header("Authorization",accountToken)
                .header("UserId",userId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("teamId").description("Team Id")
                        ),
                        responseFields(
                                fieldWithPath("resultCode").description("Result Code"),
                                fieldWithPath("resultMessage").description("Result Message")
                )));
    }
}