package org.teomant.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@JsonIgnoreProperties("user")
@Getter
@Setter
@ToString(exclude = "user")
@Entity
@Table(name = "authorities")
public class AuthoritiesEntity {

    @Id
    @SequenceGenerator( name = "authorities_sequence", sequenceName = "authorities_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "authorities_sequence" )
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "authority")
    private String authority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
