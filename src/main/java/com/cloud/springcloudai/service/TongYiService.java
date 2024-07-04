package com.cloud.springcloudai.service;

import com.cloud.springcloudai.model.ActorsFilms;
import com.cloud.springcloudai.model.Completion;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.image.ImageResponse;

import java.util.List;
import java.util.Map;

public interface TongYiService {

    /**
     * Hello World example.
     *
     * @param message conversation content question.
     * @return AI answer.
     */
    String completion(String message);

    /**
     * Stream call.
     *
     * @param message conversation content question.
     * @return AI answer.
     */
    Map<String, String> streamCompletion(String message);

    /**
     * Output parse object.
     *
     * @param actor actor name.
     * @return Object.
     */
    ActorsFilms genOutputParse(String actor);

    /**
     * Prompt template.
     *
     * @param adjective params1.
     * @param topic params2.
     * @return AI answer.
     */
    AssistantMessage genPromptTemplates(String adjective, String topic);

    /**
     * AI role example.
     *
     * @param message question content,
     * @param name params1.
     * @param voice params2.
     * @return AI answer.
     */
    AssistantMessage genRole(String message, String name, String voice);

    /**
     * Stuff and answer.
     *
     * @param message question.
     * @param stuffit is stuff.
     * @return Completion object.
     */
    Completion stuffCompletion(String message, boolean stuffit);

    /**
     * Gen images.
     * @param imgPrompt prompt info.
     * @return {@link ImageResponse}
     */
    ImageResponse genImg(String imgPrompt);

    /**
     * Gen audio.
     * @param text prompt info.
     * @return ByteBuffer object.
     */
    String genAudio(String text);

    /**
     * Audio Transcription.
     * @param audioUrls url of the audio file to be transcribed.
     * @return the result file Path.
     */
    String audioTranscription(String audioUrls);

    /**
     * TongYI LLM Text embedding.
     * @param text input text.
     * @return {@link EmbeddingResponse}
     */
    List<Double> textEmbedding(String text);

}
