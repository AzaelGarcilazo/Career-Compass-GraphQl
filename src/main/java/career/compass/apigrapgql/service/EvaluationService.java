package career.compass.apigrapgql.service;

import career.compass.apigrapgql.dto.TestResponse;

public interface EvaluationService {
    TestResponse getPersonalityTest();
    TestResponse getVocationalInterestsTest();
    TestResponse getCognitiveSkillsTest();
}