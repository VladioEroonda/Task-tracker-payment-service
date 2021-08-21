package com.github.vladioeroonda.payment.tasktrackerpayment.service.impl;

import com.github.vladioeroonda.payment.tasktrackerpayment.dto.response.ClientResponseDto;
import com.github.vladioeroonda.payment.tasktrackerpayment.entity.Client;
import com.github.vladioeroonda.payment.tasktrackerpayment.exception.ClientBadDataException;
import com.github.vladioeroonda.payment.tasktrackerpayment.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void getAllClients_ShouldReturnExpectedListSize() {
        List<Client> expected = List.of(new Client(), new Client(), new Client());
        Mockito.when(clientRepository.findAll())
                .thenReturn(expected);

        List<ClientResponseDto> actual = clientService.getAllClients();

        assertEquals(expected.size(), actual.size());
    }

    @Test
    void addClient_ShouldThrowException() {
        ClientResponseDto expectedWithNullName = new ClientResponseDto();
        ClientResponseDto expectedWithBlankName =
                new ClientResponseDto("", "testId", new BigDecimal("1.00"));

        assertThrows(ClientBadDataException.class, () -> {
            clientService.addClient(expectedWithNullName);

        });
        assertThrows(ClientBadDataException.class, () -> {
            clientService.addClient(expectedWithBlankName);
        });
    }
}
