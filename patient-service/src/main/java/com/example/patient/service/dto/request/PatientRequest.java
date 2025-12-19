package com.example.patient.service.dto.request;

import com.lims.common.enums.Gender;
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


    @Pattern(
            regexp = "^\\+994(10|50|51|55|60|70|77|99)[0-9]{7}$",
            message = "Telefon nomresi duzgun deyil. Meselen: +994501234567"
    )
    String phone;



    @NotBlank(message = "FIN bos ola bilmez")
    @Size(min = 7, max = 7, message = "FIN-in uzunlugu 7 simvol olmalidir")
    @Pattern(
            regexp = "^[A-Z0-9]{7}$",
            message = "FIN yalniz boyuk herfler (A-Z) ve reqemlerden (0-9) ibaret ola biler"
    )
    String fin;


}
