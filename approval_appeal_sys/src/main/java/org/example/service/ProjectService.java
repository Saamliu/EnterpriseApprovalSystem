package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entity.Project;
import org.example.query.ProjectQuery;
import org.example.response.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ProjectService {

    Result save( Project project) ;
    Result page(ProjectQuery query);
    Result approval(Project project);
    void exportExcel(String ids, String token, HttpServletResponse resp) throws Exception;
    Result batchDel(String ids) throws JsonProcessingException;
    Result selectById(Integer id);
}
