package com.herewego.herewegoapi.model.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Team")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "team_id") @NonNull
    Integer teamId;

    @Column(name = "team_name") @NonNull
    String teamName;

    @Column @NonNull
    String logo;

    @org.hibernate.annotations.Generated(GenerationTime.INSERT) @Column(name = "created_date")
    LocalDateTime createdDate;

    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS) @Column(name = "updated_date")
    LocalDateTime updatedDate;
}
