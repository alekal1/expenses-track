package ee.alekal.constructionexpenses.tag.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.alekal.constructionexpenses.common.exception.CEValidationException;
import ee.alekal.constructionexpenses.common.service.model.ServiceResult;
import ee.alekal.constructionexpenses.tag.model.dto.TagDto;
import ee.alekal.constructionexpenses.tag.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TagController.class)
@ContextConfiguration(classes = TagControllerTest.TagControllerTestConfiguration.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TagService tagService;


    @Test
    void givenRequestBody_whenSaveTag_shouldReturn200CeResponse() throws Exception {
        var requestBody = TagDto.builder()
                .name("test")
                .build();

        doReturn(new ServiceResult<>())
                .when(tagService)
                .saveNewTag(requestBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/tag")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    void givenRequestBody_whenSaveTagAndValidationFails_shouldThrowValidationException() throws Exception {
        var requestBody = TagDto.builder()
                .name("test")
                .build();

        doThrow(CEValidationException.class)
                .when(tagService)
                .saveNewTag(requestBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(result -> assertInstanceOf(CEValidationException.class, result.getResolvedException()));

    }

    @Test
    void givenNothing_whenGetAllTags_shouldReturn200CeResponse() throws Exception {

        doReturn(new ServiceResult<>())
                .when(tagService)
                .getAllTags();

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/tag")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @TestConfiguration
    protected static class TagControllerTestConfiguration {

        @Bean
        public TagService tagService() {
            return mock(TagService.class);
        }
    }
}
