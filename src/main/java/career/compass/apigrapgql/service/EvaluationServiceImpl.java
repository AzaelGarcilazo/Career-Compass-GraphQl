package career.compass.apigrapgql.service;

import career.compass.apigrapgql.dto.*;
import career.compass.apigrapgql.model.*;
import career.compass.apigrapgql.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public TestResponse getPersonalityTest() {
        Test test = testRepository.findByTestTypeNameAndActiveTrue("personality")
                .orElseThrow(() -> new EntityNotFoundException("Personality test not found"));

        List<Question> randomQuestions = questionRepository
                .findRandomActiveQuestionsByTestId(test.getId(), test.getQuestionsToShow());

        return buildTestResponse(test, randomQuestions);
    }

    @Override
    @Transactional(readOnly = true)
    public TestResponse getVocationalInterestsTest() {
        Test test = testRepository.findByTestTypeNameAndActiveTrue("vocational_interests")
                .orElseThrow(() -> new EntityNotFoundException("Vocational interests test not found"));

        List<Question> randomQuestions = questionRepository
                .findRandomActiveQuestionsByTestId(test.getId(), test.getQuestionsToShow());

        return buildTestResponse(test, randomQuestions);
    }

    @Override
    @Transactional(readOnly = true)
    public TestResponse getCognitiveSkillsTest() {
        Test test = testRepository.findByTestTypeNameAndActiveTrue("cognitive_skills")
                .orElseThrow(() -> new EntityNotFoundException("Cognitive skills test not found"));

        List<Question> randomQuestions = questionRepository
                .findRandomActiveQuestionsByTestId(test.getId(), test.getQuestionsToShow());

        return buildTestResponse(test, randomQuestions);
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private TestResponse buildTestResponse(Test test, List<Question> questions) {
        List<QuestionResponse> questionResponses = questions.stream()
                .map(this::mapQuestionToResponse)
                .collect(Collectors.toList());

        return TestResponse.builder()
                .id(test.getId())
                .name(test.getName())
                .description(test.getDescription())
                .testType(test.getTestType() != null ? test.getTestType().getName() : null)
                .questionsToShow(test.getQuestionsToShow())
                .questions(questionResponses)
                .build();
    }

    private QuestionResponse mapQuestionToResponse(Question question) {
        // Cargar opciones directamente desde el repository (evita lazy loading issues)
        List<AnswerOption> options = answerOptionRepository.findByQuestionId(question.getId());

        // Mapear opciones de respuesta
        List<AnswerOptionResponse> optionResponses = options.stream()
                .map(option -> AnswerOptionResponse.builder()
                        .id(option.getId())
                        .optionText(option.getOptionText())
                        .build())
                .collect(Collectors.toList());

        // Construir y retornar QuestionResponse
        return QuestionResponse.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .orderNumber(question.getOrderNumber())
                .options(optionResponses)
                .build();
    }

}