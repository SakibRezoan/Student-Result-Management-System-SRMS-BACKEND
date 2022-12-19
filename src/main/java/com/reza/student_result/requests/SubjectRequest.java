package com.reza.student_result.requests;
import com.reza.student_result.entities.Subject;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SubjectRequest {
    private Long id;
    @NotBlank
    @NotNull
    private String SubjectName;
    @NotBlank
    @NotNull
    private String subjectNameBn;
    private String subjectCode;
    private Long subjectTypeId;

    public Subject to(SubjectRequest request) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(request, subject);
        return subject;
    }
}