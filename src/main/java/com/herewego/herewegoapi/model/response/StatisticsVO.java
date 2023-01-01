package com.herewego.herewegoapi.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herewego.herewegoapi.model.entity.FixtureStatistics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsVO {
    List<Integer> goal;
    List<Integer> loss;
    List<Integer> shotsOnGoal;
    List<Integer> shotsOffGoal;
    List<Integer> totalShots;
    List<Integer> blockedShots;
    List<Integer> shotsInsideBox;
    List<Integer> shotsOutsideBox;
    List<Integer> fouls;
    List<Integer> cornerKicks;
    List<Integer> offSide;
    List<Integer> ballPossession;
    List<Integer> yellow;
    List<Integer> red;
    List<Integer> save;
    List<Integer> totalPasses;
    List<Integer> passesAccurate;

    public StatisticsVO(List<FixtureStatistics> fixtureStatistics){
        List<Integer> goal = new ArrayList<>();
        List<Integer> loss = new ArrayList<>();
        List<Integer> shotsOnGoal = new ArrayList<>();
        List<Integer> shotsOffGoal = new ArrayList<>();
        List<Integer> totalShots = new ArrayList<>();
        List<Integer> blockedShots = new ArrayList<>();
        List<Integer> shotsInsideBox = new ArrayList<>();
        List<Integer> shotsOutsideBox = new ArrayList<>();
        List<Integer> fouls = new ArrayList<>();
        List<Integer> cornerKicks = new ArrayList<>();
        List<Integer> offSide = new ArrayList<>();
        List<Integer> ballPossession = new ArrayList<>();
        List<Integer> yellow = new ArrayList<>();
        List<Integer> red = new ArrayList<>();
        List<Integer> save = new ArrayList<>();
        List<Integer> totalPasses = new ArrayList<>();
        List<Integer> passesAccurate = new ArrayList<>();

        for (int i = fixtureStatistics.size()-1; i >=0; i--) {
            String jsonStatistics = fixtureStatistics.get(i).getStatistics();
            JSONArray statArray = new JSONArray(jsonStatistics);

            goal.add(fixtureStatistics.get(i).getFullScore());
            loss.add(fixtureStatistics.get(i).getFullLose());
            for (int j = 0; j < statArray.length(); ++j) {
                JSONObject stat = statArray.getJSONObject(j);
                switch ((String)stat.get("type")) {
                    case "Shots on Goal":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            shotsOnGoal.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            shotsOnGoal.add(0);
                        break;
                    case "Shots off Goal":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            shotsOffGoal.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            shotsOffGoal.add(0);
                        break;
                    case "Total Shots":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            totalShots.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            totalShots.add(0);
                        break;
                    case "Blocked Shots":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            blockedShots.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            blockedShots.add(0);
                        break;
                    case "Shots insidebox":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            shotsInsideBox.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            shotsInsideBox.add(0);
                        break;
                    case "Shots outsidebox":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            shotsOutsideBox.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            shotsOutsideBox.add(0);
                        break;
                    case "Fouls":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            fouls.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            fouls.add(0);
                        break;
                    case "Corner Kicks":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            cornerKicks.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            cornerKicks.add(0);
                        break;
                    case "Offsides":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            offSide.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            offSide.add(0);
                        break;
                    case "Ball Possession":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            String possession = stat.get("value").toString();
                            ballPossession.add(Integer.parseInt(possession.substring(0, possession.length()-1)));
                        }
                        else
                            ballPossession.add(0);
                        break;
                    case "Yellow Cards":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            yellow.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            yellow.add(0);
                        break;
                    case "Red Cards":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            red.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            red.add(0);
                        break;
                    case "Goalkeeper Saves":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            save.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            save.add(0);
                        break;
                    case "Total passes":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            totalPasses.add(Integer.parseInt(stat.get("value").toString()));
                        }
                        else
                            totalPasses.add(0);
                        break;
                    case "Passes %":
                        if (!ObjectUtils.isEmpty(stat.get("value")) && !stat.get("value").toString().equals("null")) {
                            String accuracy = stat.get("value").toString();
                            passesAccurate.add(Integer.parseInt(accuracy.substring(0, accuracy.length()-1)));
                        }
                        else
                            passesAccurate.add(0);
                        break;
                }
            }
        }

        this.goal = goal;
        this.loss  = loss;
        this.shotsOnGoal = shotsOnGoal;
        this.shotsOffGoal = shotsOffGoal;
        this.totalShots = totalShots;
        this.blockedShots = blockedShots;
        this.shotsInsideBox = shotsInsideBox;
        this.shotsOutsideBox = shotsOutsideBox;
        this.fouls = fouls;
        this.cornerKicks = cornerKicks;
        this.offSide = offSide;
        this.ballPossession = ballPossession;
        this.yellow = yellow;
        this.red = red;
        this.save = save;
        this.totalPasses = totalPasses;
        this.passesAccurate = passesAccurate;
    }
}
