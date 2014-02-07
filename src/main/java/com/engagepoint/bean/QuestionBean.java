package com.engagepoint.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Random;

/**
 * Class represents question tag.
 */
@Named("question")
@RequestScoped
public class QuestionBean implements Cloneable {
    private Long id;					//id of the question
    private String questionText;		//questiontext
    private boolean requiredAnswer;		//is answer required or not
    private QuestionType questionType;	//questiontype from ENUM of questiontypes
    private String helpText;			//Help texts for questions
    
    //Default values for questions
    //Dependent questions
    

    @XmlElement(name = "question-title")
    public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	@XmlAttribute(name = "question-id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute(required = true)
    public boolean isRequiredAnswer() {
        return requiredAnswer;
    }

    public void setRequiredAnswer(boolean requiredAnswer) {
        this.requiredAnswer = requiredAnswer;
    }

    @XmlElement(name = "question-type")
    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public QuestionBean() {
    }

    public QuestionBean(String questionText, boolean requiredAnswer, QuestionType questionType) {
        this.questionText = questionText;
        this.requiredAnswer = requiredAnswer;
        this.questionType = questionType;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //ÐŸÐ¾ÐºÐ° Ð½Ðµ Ð¾Ð¿Ñ€ÐµÐ´ÐµÐ»Ð¸Ð»Ð¸Ñ�ÑŒ ÐºÐ°Ðº Ð´ÐµÐ»Ð°ÐµÐ¼ Ð�Ð¹Ð´Ð¸ Ð¿Ñ€Ð¸ ÐºÐ»Ð¾Ð½Ð¸Ñ€Ð¾Ð²Ð°Ð½Ð¸Ð¸, Ð´ÐµÐ»Ð°ÑŽ Ñ€Ð°Ð½Ð´Ð¾Ð¼
        Random rand = new Random(60000);
        QuestionBean copy = (QuestionBean) super.clone();
        copy.setId(rand.nextLong());
        copy.setQuestionType(this.questionType);
        copy.setQuestionText(this.questionText);
        copy.setRequiredAnswer(this.requiredAnswer);

        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionBean that = (QuestionBean) o;

        if (requiredAnswer != that.requiredAnswer) return false;

        if (questionText != null ? !questionText.equals(that.questionText) : that.questionText != null)
            return false;
        if (questionType != that.questionType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (questionText != null ? questionText.hashCode() : 0);
        result = 31 * result + (requiredAnswer ? 1 : 0);
        result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
        return result;
    }
}
