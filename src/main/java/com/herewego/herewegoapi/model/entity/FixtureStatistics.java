package com.herewego.herewegoapi.model.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FixtureStatistics")
@Data
@TypeDef(name = "jsonb" , typeClass = JsonStringType.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FixtureStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "fixture_id") @NonNull
    Integer fixtureId;

    @Column @NonNull
    Long timestamp;

    @Column(name = "league_id") @NonNull
    Integer leagueId;

    @Column @NonNull
    Integer season;

    @Column @NonNull
    Integer round;

    @Column(name = "team_id") @NonNull
    Integer teamId;

    @Column @NonNull
    Integer home;

    @Column @NonNull
    Integer win;

    @Column(name = "half_score") @NonNull
    Integer halfScore;

    @Column(name = "full_score") @NonNull
    Integer fullScore;

    @Column(name = "half_lose") @NonNull
    Integer halfLose;

    @Column(name = "full_lose") @NonNull
    Integer fullLose;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb") @NonNull
    String statistics;

    @org.hibernate.annotations.Generated(GenerationTime.INSERT) @Column(name = "created_date")
    LocalDateTime createdDate;

    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS) @Column(name = "updated_date")
    LocalDateTime updatedDate;
}
