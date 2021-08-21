package com.github.vladioeroonda.payment.tasktrackerpayment.service.impl;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.TransactionResponseDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Transaction;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.ClientNotFoundException;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.ClientRepository;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class ClientHistoryServiceImplTest {
    @InjectMocks
    private ClientHistoryServiceImpl clientHistoryService;

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void getAllTransactionsByClientId_ShouldThrowException() {
        Long expectedId = 123L;
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenThrow(new ClientNotFoundException());

        assertThrows(ClientNotFoundException.class, () -> {
            clientHistoryService.getAllTransactionsByClientId(expectedId);
        });
    }

    @Test
    void getAllTransactionsByClientId_ShouldReturnExpectedListSize() {
        List<Transaction> expected = List.of(new Transaction(), new Transaction());
        Mockito.when(clientRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new Client()));
        Mockito.when(transactionRepository.getAllTransactionsByClientId(Mockito.anyLong()))
                .thenReturn(expected);

        List<TransactionResponseDto> actual =
                clientHistoryService.getAllTransactionsByClientId(Mockito.anyLong());

        assertEquals(expected.size(), actual.size());
    }
}
