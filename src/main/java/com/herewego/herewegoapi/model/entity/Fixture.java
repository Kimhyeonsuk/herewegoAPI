package com.herewego.herewegoapi.model.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Fixture")
@Data
@TypeDef(name = "jsonb" , typeClass = JsonStringType.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Fixture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "fixture_id") @NonNull
    Integer fixtureId;

    @Column @NonNull
    String timezone;

    @Column @NonNull
    LocalDateTime date;

    @Column @NonNull
    Long timestamp;

    @Column(name = "venue_id") @NonNull
    Integer venueId;

    @Column(name = "league_id") @NonNull
    Integer leagueId;

    @Column @NonNull
    Integer season;

    @Column @NonNull
    Integer round;

    @Column(name = "home_id") @NonNull
    Integer hometeamId;

    @Column(name = "away_id") @NonNull
    Integer awayteamId;

    @Column(name = "home_score") @NonNull
    Integer homeScore;

    @Column(name = "away_score") @NonNull
    Integer awayScore;

    @org.hibernate.annotations.Generated(GenerationTime.INSERT) @Column(name = "created_date")
    LocalDateTime createdDate;

    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS) @Column(name = "updated_date")
    LocalDateTime updatedDate;

}
