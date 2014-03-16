package com.engagepoint.model.question;

import com.engagepoint.model.questionnaire.GroupBean;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "textQuestionBean", propOrder = {
        "questionId",
        "requiredAnswer",
        "questionText",
        "questionType",
        "rules",
        "helpText",
        "defaultAnswers"
})
public class TextQuestionBean extends Question {

    public TextQuestionBean() {
        super();
        getDefaultAnswers().add(0, "");
    }

    public TextQuestionBean(GroupBean currentGroup) {
        super(currentGroup);
        getDefaultAnswers().add(0, "");
    }

    @XmlTransient
	public String getDefaultAnswer() {
		return getDefaultAnswers().get(0);
	}

	public void setDefaultAnswer(String defaultAnswer) {
        getDefaultAnswers().set(0, defaultAnswer);
	}
}
