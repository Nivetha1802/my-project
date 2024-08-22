package com.library.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.library.entity.LendDetails;
import com.library.entity.UserEntity;
import com.library.repository.LendDetailsRepository;
import com.library.service.LendDetailsServiceImpl;

public class LendDetailsServiceTest {

    @InjectMocks
    private LendDetailsServiceImpl lendDetailsService;

    @Mock
    private LendDetailsRepository lendDetailsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllLendDetails() {

        List<LendDetails> lendDetailsList = Arrays.asList(
                new LendDetails("Book 1", "Author 1", LocalDate.now(), LocalDate.now().plusDays(10)),
                new LendDetails("Book 2", "Author 2", LocalDate.now(), LocalDate.now().plusDays(5)));

        when(lendDetailsRepository.findAll()).thenReturn(lendDetailsList);

        List<LendDetails> result = lendDetailsService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
    }

    @Test
    public void testGetLendDetailsById() {
        LendDetails lendDetails = new LendDetails("Book 1", "Author 1", LocalDate.now(),
                LocalDate.now().plusDays(10));

        when(lendDetailsRepository.findById(1)).thenReturn(Optional.of(lendDetails));

        Optional<LendDetails> result = lendDetailsService.getById(1);

        assertTrue(result.isPresent());
        assertEquals("Book 1", result.get().getTitle());
    }

    @Test
    public void testGetLendDetailsByIdNotFound() {
        when(lendDetailsRepository.findById(1)).thenReturn(Optional.empty());

        Optional<LendDetails> result = lendDetailsService.getById(1);

        assertFalse(result.isPresent());
    }

    @Test
    public void testAddLendDetails() {
        LendDetails lendDetails = new LendDetails("Book 1", "Author 1", LocalDate.now(),
                LocalDate.now().plusDays(10));

        when(lendDetailsRepository.save(lendDetails)).thenReturn(lendDetails);

        LendDetails result = lendDetailsService.create(lendDetails);

        assertNotNull(result);
        assertEquals("Book 1", result.getTitle());
    }

    @Test
    public void testUpdateLendDetails() {
        LendDetails existingLendDetails = new LendDetails("Book 1", "Author 1", LocalDate.now(),
                LocalDate.now().plusDays(10));
        LendDetails updatedLendDetails = new LendDetails("Updated Book", "Updated Author", LocalDate.now(),
                LocalDate.now().plusDays(15));

        when(lendDetailsRepository.findById(1)).thenReturn(Optional.of(existingLendDetails));
        when(lendDetailsRepository.save(updatedLendDetails)).thenReturn(updatedLendDetails);

        LendDetails result = lendDetailsService.update(1, updatedLendDetails);

        assertNotNull(result);
        assertEquals("Updated Book", result.getTitle());
    }

    @Test
    public void testUpdateLendDetailsNotFound() {
        LendDetails updatedLendDetails = new LendDetails("Updated Book", "Updated Author", LocalDate.now(),
                LocalDate.now().plusDays(15));

        when(lendDetailsRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            lendDetailsService.update(1, updatedLendDetails);
        });

        assertEquals("LendDetails not found with id 1", exception.getMessage());
    }

    @Test
    public void testDeleteLendDetails() {
        lendDetailsService.delete(1);

        verify(lendDetailsRepository, times(1)).deleteById(1);
    }

    @Test
    public void testProcessBookLendDetails() {
        // Arrange
        LendDetails book = new LendDetails("Book 1", "Author 1", LocalDate.now(), LocalDate.now().plusDays(10));

        UserEntity user = new UserEntity();
        user.setId(1);

        Optional<UserEntity> optionalUser = Optional.of(user);
        when(lendDetailsRepository.save(any(LendDetails.class))).thenReturn(book);
        // Act
        lendDetailsService.processBookLendDetails(book, optionalUser);
        // Assert
        verify(lendDetailsRepository, times(1)).save(any(LendDetails.class));
    }

    @Test
    public void testGetLendDetailsByUserId() {
        List<LendDetails> lendDetailsList = Arrays.asList(
                new LendDetails("Book 1", "Author 1", LocalDate.now().minusDays(10), LocalDate.now().minusDays(5)),
                new LendDetails("Book 2", "Author 2", LocalDate.now().minusDays(5), LocalDate.now().minusDays(2)));

        when(lendDetailsRepository.findLendDetailsById(1)).thenReturn(lendDetailsList);

        List<LendDetails> result = lendDetailsService.getLendDetailsByUserId(1);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testRenewBook() {
        LendDetails lendDetails = new LendDetails();
        lendDetails.setId(1);
        lendDetails.setRenewCount(0);
        lendDetails.setReturnDate(LocalDate.now().plusDays(10));

        when(lendDetailsRepository.findById(1)).thenReturn(Optional.of(lendDetails));
        when(lendDetailsRepository.save(any(LendDetails.class))).thenReturn(lendDetails);

        lendDetailsService.renewBook(1);

        assertEquals(1, lendDetails.getRenewCount());
        assertEquals(LocalDate.now().plusDays(24), lendDetails.getReturnDate());
        verify(lendDetailsRepository, times(1)).save(lendDetails);
    }
}
