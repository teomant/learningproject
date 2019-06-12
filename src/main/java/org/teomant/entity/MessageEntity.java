package org.teomant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
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
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @SequenceGenerator( name = "messages_sequence", sequenceName = "messages_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "messages_sequence" )
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Column(name = "text")
    private String text;

    @JsonIgnoreProperties({"password", "authorities", "posts", "files", "messageFrom", "messageTo"})
    @ManyToOne
    @JoinColumn(name = "from_user")
    private UserEntity from;

    @JsonIgnoreProperties({"password", "authorities", "posts", "files", "messageFrom", "messageTo"})
    @ManyToOne
    @JoinColumn(name = "to_user")
    private UserEntity to;

    @Column(name = "date")
    private LocalDateTime date;
}
