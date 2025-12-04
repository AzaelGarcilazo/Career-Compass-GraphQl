package career.compass.apigrapgql.controller;

import career.compass.apigrapgql.dto.TestResponse;
import career.compass.apigrapgql.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class EvaluationGraphQLController {

    private final EvaluationService evaluationService;

    @QueryMapping
    public TestResponse getPersonalityTest() {
        return evaluationService.getPersonalityTest();
    }

    @QueryMapping
    public TestResponse getVocationalInterestsTest() {
        return evaluationService.getVocationalInterestsTest();
    }

    @QueryMapping
    public TestResponse getCognitiveSkillsTest() {
        return evaluationService.getCognitiveSkillsTest();
    }

}