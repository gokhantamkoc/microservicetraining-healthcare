package com.gokhantamkoc.microservicetraining.healthcare.search.adapter.in.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gokhantamkoc.microservicetraining.healthcare.search.application.SearchUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.search.domain.PatientHistoryIndex;

@Controller
class SearchGraphqlController {
    private final SearchUseCase searchUseCase;

    SearchGraphqlController(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    @QueryMapping
    List<PatientHistoryIndex> searchPatientHistory(@Argument Long tenantId, @Argument String term) {
        return searchUseCase.search(tenantId, term);
    }
}

