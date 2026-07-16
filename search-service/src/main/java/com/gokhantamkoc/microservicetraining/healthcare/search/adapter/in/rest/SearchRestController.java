package com.gokhantamkoc.microservicetraining.healthcare.search.adapter.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gokhantamkoc.microservicetraining.healthcare.search.application.SearchUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.search.domain.PatientHistoryIndex;

@RestController
class SearchRestController {
    private final SearchUseCase searchUseCase;

    SearchRestController(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    @GetMapping("/search/patient-history")
    List<PatientHistoryIndex> search(@RequestParam Long tenantId, @RequestParam String term) {
        return searchUseCase.search(tenantId, term);
    }

    @PostMapping("/search/index")
    ResponseEntity<PatientHistoryIndex> index(@RequestBody IndexRequest request) {
        return ResponseEntity.accepted().body(searchUseCase.index(request.tenantId(), request.patientId(), request.summary()));
    }

    record IndexRequest(Long tenantId, Long patientId, String summary) {
    }
}

