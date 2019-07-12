package com.stanula.usermanager.service;

import com.stanula.usermanager.domain.Employee;
import com.stanula.usermanager.repository.EmployeeRepository;
import com.stanula.usermanager.service.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    private static Employee redEmployee;
    private static final Integer ID = 1;
    private static final String EMPLOYEE_NOT_FOUND_EXCEPTION_TEXT = "Employee with id: " + ID + " not found";

    @BeforeAll
    static void setUp() {
        redEmployee = new Employee(1, "John", "Smith", 1, 2300);
    }

    @Test
    void whenCreateMethodIsCalled_thenRepositorySaveMethodIsInvocatedOnce() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(redEmployee);

        employeeService.create(redEmployee);

        verify(employeeRepository, times(1)).save(redEmployee);
    }

    @Test
    void whenUpdateMethodIsCalled_thenRepositoryFindByIdAndSaveMethodsAreInvocatedOnce() {
        when(employeeRepository.findById(ID)).thenReturn(Optional.of(new Employee()));

        employeeService.update(redEmployee, ID);

        verify(employeeRepository, times(1)).findById(ID);
        verify(employeeRepository, times(1)).save(redEmployee);
    }

    @Test
    void whenDeleteMethodIsCalled_thenRepositoryFindByIdAndDeleteByIdMethodsAreInvocatedOnce() {
        when(employeeRepository.findById(ID)).thenReturn(Optional.of(new Employee()));

        employeeService.delete(ID);

        verify(employeeRepository, times(1)).findById(ID);
        verify(employeeRepository, times(1)).deleteById(ID);
    }

    @Test
    void whenGetByIdMethodIsCalled_thenRepositoryFindByIdAMethodsIsInvocatedOnce() {
        when(employeeRepository.findById(ID)).thenReturn(Optional.of(new Employee()));

        employeeService.getById(ID);

        verify(employeeRepository, times(1)).findById(ID);
    }

    @Test
    void whenGetByIdMethodIsCalled_thenEmployeeNotFoundExceptionIsThrown() {
        when(employeeRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(EmployeeNotFoundException.class).isThrownBy(
                () -> employeeService.getById(ID)).withMessage(EMPLOYEE_NOT_FOUND_EXCEPTION_TEXT);
    }

    @Test
    void whenGetAllByAnyParametersMethodIsCalled_thenRepositoryFindByIdAMethodsIsInvocatedOnce() {
        employeeService.getAllByAnyParameter("John", "Smith", 1, 2333);

        verify(employeeRepository, times(1)).getAllByAnyParameter("John", "Smith", 1, 2333);
    }
}