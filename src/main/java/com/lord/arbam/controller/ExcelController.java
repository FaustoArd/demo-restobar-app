package com.lord.arbam.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.lord.arbam.model.WorkingDay;
import com.lord.arbam.service.WorkingDayService;
import com.lord.arbam.util.ExcelExporter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/arbam/excel")
@RequiredArgsConstructor
public class ExcelController {
	
	@Autowired
	private final WorkingDayService workingDayService;
	
	@GetMapping("/{workingDayId}")
	void exportToExcel(HttpServletResponse response,@PathVariable("workingDayId")Long workingDayId)throws IOException{
		
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		WorkingDay workingDay = workingDayService.findWorkingDayById(workingDayId);
		ExcelExporter excelExporter = new  ExcelExporter(workingDay);
		 excelExporter.export(response);
		
		
		}
	}
	
	


