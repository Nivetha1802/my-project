package com.library.demo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.library.entity.LendDetails;
import com.library.entity.UserEntity;
import com.library.repository.LendDetailsRepository;
import com.library.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LendDetailsRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private LendDetailsRepository lendDetailsRepository;

    @BeforeEach
    void initialSetUp() {
        MockitoAnnotations.openMocks(this);
    }

    private UserEntity user1;
    private UserEntity user2;

    private LendDetails lendDetails1, lendDetails2, lendDetails3;

    @BeforeEach
    public void dataSetUp() {
        user1 = new UserEntity();
        user1.setId(1);
        user1.setName("John Doe");
        user1.setRole("User");
        user1.setPassword("password");

        user2 = new UserEntity();
        user2.setId(2);
        user2.setName("Jane Doe");
        user2.setRole("User");
        user2.setPassword("password");

        userRepository.save(user1);
        userRepository.save(user2);

        lendDetails1 = new LendDetails();
        lendDetails1.setUser(user1);
        lendDetails1.setTitle("Book 1");
        lendDetails1.setAuthors("Author 1");
        lendDetails1.setLendDate(LocalDate.of(2024, 1, 1));
        lendDetails1.setReturnDate(LocalDate.of(2024, 2, 1));
        lendDetails1.setRenewCount(0);
        lendDetails1.setFine(0.0);

        lendDetails2 = new LendDetails();
        lendDetails2.setUser(user2);
        lendDetails2.setTitle("Book 2");
        lendDetails2.setAuthors("Author 2");
        lendDetails2.setLendDate(LocalDate.of(2024, 1, 10));
        lendDetails2.setReturnDate(LocalDate.of(2024, 2, 10));
        lendDetails2.setRenewCount(1);
        lendDetails2.setFine(0.0);

        lendDetails3 = new LendDetails();
        lendDetails3.setUser(user1);
        lendDetails3.setTitle("Book 3");
        lendDetails3.setAuthors("Author 3");
        lendDetails3.setLendDate(LocalDate.of(2024, 1, 15));
        lendDetails3.setReturnDate(LocalDate.of(2024, 2, 15));
        lendDetails3.setRenewCount(0);
        lendDetails3.setFine(0.0);

        lendDetailsRepository.saveAll(List.of(lendDetails1, lendDetails2, lendDetails3));
    }
    @Transactional
    @Test
    public void testFindLendDetailsById_MustReturnResultsSuccessfully() {
        List<LendDetails> lendDetailsList = lendDetailsRepository.findLendDetailsById(1);

        assertThat(lendDetailsList).hasSize(2);
        assertThat(lendDetailsList).extracting("title").containsExactlyInAnyOrder("Book 1", "Book 3");
    }
    @Transactional
    @Test
    public void testFindLendDetailsById_NoResults() {
        List<LendDetails> lendDetailsList = lendDetailsRepository.findLendDetailsById(3);

        assertThat(lendDetailsList).isEmpty();
    }
}
