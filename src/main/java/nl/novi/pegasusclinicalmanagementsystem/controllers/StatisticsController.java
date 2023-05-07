package nl.novi.pegasusclinicalmanagementsystem.controllers;

import nl.novi.pegasusclinicalmanagementsystem.services.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/appointments")
    public Long getAppointmentsCount() {
        return statisticsService.calculateAppointments();
    }

    @GetMapping("/cancellations")
    public Long getCancellationsCount() {
        return statisticsService.calculateCancellations();
    }

    @GetMapping("/hours-per-doctor")
    public Map<String, Double> getHoursPerDoctor() {
        return statisticsService.calculateHoursPerDoctor();
    }
}
