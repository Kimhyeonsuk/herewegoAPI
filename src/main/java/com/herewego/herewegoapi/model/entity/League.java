package com.herewego.herewegoapi.model.entity;

import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "League")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "league_id") @NonNull
    Integer leagueId;

    @Column(name = "league_name") @NonNull
    String leagueName;

    @Column @NonNull
    String logo;

    @org.hibernate.annotations.Generated(GenerationTime.INSERT) @Column(name = "created_date")
    LocalDateTime createdDate;

    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS) @Column(name = "updated_date")
    LocalDateTime updatedDate;
}
