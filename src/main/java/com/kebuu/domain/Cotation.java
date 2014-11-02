package com.kebuu.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table( name = "cotation",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"date", "code"}),
            @UniqueConstraint(columnNames = {"position"})
        }
)
@EqualsAndHashCode(of={"id"})
public class Cotation {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull private Date date;
    @NotNull private String code;
    @NotNull private int position;
    @NotNull private double start;
    @NotNull private double min;
    @NotNull private double max;
    @NotNull private double end;
    private Long volume;

    public boolean hasVolume() {
        return volume != null;
    }
}
