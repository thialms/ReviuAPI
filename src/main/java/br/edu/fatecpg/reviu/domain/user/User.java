package br.edu.fatecpg.reviu.domain.user;

import br.edu.fatecpg.reviu.domain.deck.Deck;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true,  nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    // Vericação do email
    private Boolean verified = false; // true se o usuário confirmou o e-mail

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deck> decks = new ArrayList<>();
}
