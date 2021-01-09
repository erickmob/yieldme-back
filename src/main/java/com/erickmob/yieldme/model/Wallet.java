package com.erickmob.yieldme.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    @NotNull(message = "User may not be null")
    private User user;

    @OneToMany(mappedBy="wallet", fetch = FetchType.EAGER)
    private List<Contribution> contributions = new ArrayList<>();

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", user=" + null +
                ", contributions=" + null +
                '}';
    }
}
