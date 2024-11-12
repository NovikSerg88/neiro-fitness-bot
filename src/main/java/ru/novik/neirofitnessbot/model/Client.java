package ru.novik.neirofitnessbot.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@Builder
@AllArgsConstructor
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "message_id")
    private Integer messageId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription")
    private SubscriptionOption subscription;

    @Column(name = "is_subscribed")
    private boolean isSubscribed;
}
