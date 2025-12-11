package com.example.patient.service.dto.request;

import com.example.patient.service.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
//@Builder

public class PatientRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String fatherName;

    @NotNull
    Gender gender;

    @NotNull
    @PastOrPresent(message = "Dogum tarixi bugunki gun ve kecmiste olmalidir")
    LocalDate birthday;

    @Pattern(regexp = "^\\+994(10|50|51|55|60|70|77|99)[0-9]{6}$",
            message = "Telefon nomresi duzgun deyil")
    String phone;



    @NotBlank(message = "FIN bos ola bilmez")
    @Size(min = 7, max = 7, message = "FIN-in uzunlugu 7 olmalidir")
    @Pattern(regexp = "^[A-Z0-9]{7}$", message = "FIN-de ancaq bu simvollar ola biler A-Z and 0-9")
    private String documentNumber;


}
