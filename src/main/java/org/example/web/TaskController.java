package org.example.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constants.TaskStatus;
import org.example.model.Task;
import org.example.service.TaskService;
import org.example.web.vo.TaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    /**
     * 새로운 할 일 추가
     * @param req 추가하고자 하는 할 일
     * @return 추가된 할 일
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest req) {
        var result = this.taskService.add(req.getTitle()
                , req.getDescription(), req.getDueDate());

        return ResponseEntity.ok(result);
    }

    /**
     * 특정 마감일에 해당하는 할 일 목록을 반환
     * @param dueDate 할 일의 마감일
     * @return 마감일에 해당하는 할 일 목록
     */
    @GetMapping
    public ResponseEntity<List<Task>> getTask(Optional<String> dueDate) {
        List<Task> result;
        if (dueDate.isPresent()) {
            result=this.taskService.getByDueDate(dueDate.get());
        } else {
            result=this.taskService.getAll();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 특정 ID에 해당하는 할 일을 조회
     * @param id 할일 ID
     * @return id 에 해당하는 Task 객체
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> fetchOneTask(@PathVariable Long id) {
        var result=this.taskService.getOne(id);

        return ResponseEntity.ok(result);

    }

    /**
     * 특정 상태에 해당하는 할 일 목록을 반환
     * @param status 할 일의 상태
     * @return 상태에 해당하는 할 일 목록
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getByStatus(@PathVariable TaskStatus status) {
        var result = this.taskService.getByStatus(status);
        return ResponseEntity.ok(result);
    }

}

//Slf4j 로그 사용을 위한 어노테이션
//Controller 어노테이션
//RequestMapping -> 통신시 받을 주소
//Optional -> 함수 인자 값을 선택적으로 받아들이고 싶을 때 (있어도 되고 없어도 된다)
//@PathVariable 로 경로 변수를 바인딩 해주었다.