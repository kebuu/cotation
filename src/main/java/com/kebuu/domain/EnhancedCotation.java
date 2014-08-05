package com.kebuu.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="enhanced_cotation", uniqueConstraints = @UniqueConstraint(columnNames = "date"))
public class EnhancedCotation extends Cotation {

}
