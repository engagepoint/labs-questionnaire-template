package com.engagepoint.controller.question;

import com.engagepoint.controller.page.TemplateEditController;
import com.engagepoint.model.question.Question;
import com.engagepoint.model.question.TextQuestionBean;
import com.engagepoint.controller.page.QuestionEditController;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("textQuestion")
@ConversationScoped
public class TextQuestionController extends QuestionEditController {

    private TextQuestionBean currentQuestion;

    @Inject
    TemplateEditController templateEditController;

    @PostConstruct
    public void postConstruct() {
        beginConversation();
        Question question = getTemplateTreeController().getCurrentQuestion();
        if (question == null) {
            setNew(true);
            currentQuestion = new TextQuestionBean(getTemplateTreeController().getCurrentGroup());
            currentQuestion.setQuestionType(templateEditController.getSelectedQuestionType());
        } else {currentQuestion = (TextQuestionBean) question;
        }
        currentQuestionEventNew.fire(currentQuestion);
    }

    public TextQuestionBean getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(TextQuestionBean currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    @Override
    public String actionSave() {
        getTemplateTreeController().setCurrentQuestion(currentQuestion);
        return super.actionSave();
    }
}