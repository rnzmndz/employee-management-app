package employee_management_app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import employee_management_app.model.Attendance;

@DataJpaTest
public class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Test
    void findByDateRange_ShouldReturnAttendanceWithinRange() {
    	// Given
    	LocalDate startDate = LocalDate.now().minusDays(2);
    	LocalDate endDate = LocalDate.now().plusDays(2);
    	
        // When
        Page<Attendance> found = attendanceRepository.findByDateRange(startDate, endDate, PageRequest.of(0, 10));

        // Then
        assertThat(found).isNotEmpty();
        assertThat(found.getContent().size()).isEqualTo(3);
        assertThat(found.getContent().get(0).getDate()).isAfter(startDate);
        assertThat(found.getContent().get(0).getDate()).isBefore(endDate);
    }
}