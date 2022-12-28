package com.reza.student_result.controllers;

import com.reza.student_result.entities.IIT_Student;
import com.reza.student_result.enums.RecordStatus;
import com.reza.student_result.exceptions.ResourceNotFoundException;
import com.reza.student_result.requests.IIT_StudentRequest;
import com.reza.student_result.response.IIT_StudentResponse;
import com.reza.student_result.services.impl.IIT_StudentServiceImpl;
import com.reza.student_result.utils.CommonDataHelper;
import com.reza.student_result.validators.IIT_StudentValidator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.reza.student_result.constant.MessageConstants.*;
import static com.reza.student_result.exceptions.ApiError.fieldError;
import static com.reza.student_result.utils.ResponseBuilder.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/iit-student-management")
public class IIT_StudentManagementController {
    private final IIT_StudentServiceImpl iitStudentServiceImpl;
    private final IIT_StudentValidator validator;
    private final CommonDataHelper helper;

    @GetMapping("/")
    @Operation(summary = "Welcome Message", description = "Welcome Message")
    private String welcome() {
        return "Welcome to IIT Student Management System";
    }

    //Register IIT_Student
    @PostMapping("/teacher/add-new-iit-student")
    @Operation(summary = "Add new IIT_Student", description = "Add new IIT_Student")
    public ResponseEntity<JSONObject> addNewIitStudent(@Valid @RequestBody IIT_StudentRequest iit_studentRequest, BindingResult bindingResult) {

        ValidationUtils.invokeValidator(validator, iit_studentRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        IIT_Student iitStudent = iitStudentServiceImpl.save(iit_studentRequest);
        return ok(success(IIT_StudentResponse.from(iitStudent), IIT_STUDENT_SAVE).getJson());
    }

    //Find IIT Student by id
    @GetMapping("/teacher/find-student/{id}")
    @Operation(summary = "Find a student", description = "Find a student by id")
    public ResponseEntity<JSONObject> findStudentById(@PathVariable Long id) {
        Optional<IIT_StudentResponse> response = Optional.ofNullable(iitStudentServiceImpl.findStudentById(id)
                .map(IIT_StudentResponse::from)
                .orElseThrow(ResourceNotFoundException::new));

        return ok(success(response).getJson());
    }
    //Update IIT Student
    @PutMapping("/teacher/update-iit-student")
    @Operation(summary = "Update IIT Student", description = "Update existing iit student")

    public ResponseEntity<JSONObject> updateStudent(@Valid @RequestBody IIT_StudentRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return badRequest().body(error(fieldError(bindingResult)).getJson());
        }

        IIT_Student iitStudent = iitStudentServiceImpl.update(request);
        return ok(success(IIT_StudentResponse.from(iitStudent), IIT_STUDENT_UPDATE).getJson());
    }

    //Update Record Status of IIT Student
    @PutMapping("/change-record-status/{id}/{status}")
    @Operation(summary = "Change record status of a iit student", description =
            "Change record status of a iit student with parameter id and status")
    public ResponseEntity<JSONObject> changeRecordStatus(@PathVariable Long id, @PathVariable RecordStatus status) {

        IIT_Student iitStudent = iitStudentServiceImpl.update(id, status);
        return ok(success(IIT_StudentResponse.from(iitStudent), RECORD_STATUS_UPDATE).getJson());
    }

    //Get Paginated List of Courses
//    @GetMapping("/teacher/course-list")
//    @Operation(summary = "Fetch all courses", description =
//            "Fetch all courses with page, size, sortBy, courseCode, courseTitle")
//    public ResponseEntity<JSONObject> getList(
//            @RequestParam(value = "page", defaultValue = "1") Integer page,
//            @RequestParam(value = "size", defaultValue = "10") Integer size,
//            @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
//            @RequestParam(value = "courseCode", defaultValue = "") String courseCode,
//            @RequestParam(value = "courseTitle", defaultValue = "") String courseTitle
//    ) {
//        helper.setPageSize(page,size);
//
//        PaginatedResponse paginatedResponse = new PaginatedResponse();
//
//        Map<String,Object> courseMappedSearchResult = courseServiceImpl.searchCourse(courseCode, courseTitle,
//                page, size, sortBy);
//        List<Course> responses =(List<Course>) courseMappedSearchResult.get("lists");
//
//        List<CourseResponse> customResponse = responses.stream().map(CourseResponse::from).
//                collect(Collectors.toList());
//        helper.getCommonData(page,size, courseMappedSearchResult, paginatedResponse, customResponse);
//
//        return ok(paginatedSuccess(paginatedResponse).getJson());
//    }

}