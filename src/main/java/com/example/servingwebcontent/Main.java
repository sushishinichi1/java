package com.example.servingwebcontent;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/details/{id}")
    public String showRecordDetails(@PathVariable("id") int id, Model model) {
        String sql = "SELECT due_day, number_of_fetuses FROM nipt_reservation_interview_sheets WHERE id = ?";
        Map<String, Object> record = jdbcTemplate.queryForMap(sql, id);

        model.addAttribute("record", record);

        return "result";
    }

    }

    @PostMapping("/add")
    public String addRecord(
            @RequestParam("due_day") String dueDay,
            @RequestParam("number_of_fetuses") int fetuses,
            @RequestParam("reservation_id") int reservationId,
            @RequestParam("current_height") int currentHeight,
            @RequestParam("current_weight") int currentWeight,
            @RequestParam(value = "is_blood_transfusion", defaultValue = "false") boolean isBloodTransfusion,
            @RequestParam(value = "is_stem_cell_or_kidney_transplantation", defaultValue = "false") boolean isStemCellOrKidneyTransplantation,
            @RequestParam(value = "is_everyday_medicine_transfusion", defaultValue = "false") boolean isEverydayMedicineTransfusion) {
        String sql = "INSERT INTO nipt_reservation_interview_sheets (due_day, number_of_fetuses, reservation_id, current_height, current_weight, is_blood_transfusion, is_stem_cell_or_kidney_transplantation, is_everyday_medicine_transfusion) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, dueDay, fetuses, reservationId, currentHeight, currentWeight, isBloodTransfusion,
                isStemCellOrKidneyTransplantation, isEverydayMedicineTransfusion);

        return "result";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable("id") int id) {
        String sql = "DELETE FROM nipt_reservation_interview_sheets WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return "result";
    }
}
